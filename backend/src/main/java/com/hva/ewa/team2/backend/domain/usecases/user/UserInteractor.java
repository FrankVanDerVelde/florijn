package com.hva.ewa.team2.backend.domain.usecases.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hva.ewa.team2.backend.common.services.asset.AssetService;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.*;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import com.hva.ewa.team2.backend.rest.user.AddClientRequestBody;
import com.hva.ewa.team2.backend.rest.user.AddSpecialistRequestBody;
import com.hva.ewa.team2.backend.rest.user.json.JsonUserData;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserInteractor implements UserBusinessLogic {

    private final UserRepository userRepo;

    private final AssetService assetService;

    @Autowired
    public UserInteractor(UserRepository userRepo, AssetService assetService) {
        this.userRepo = userRepo;
        this.assetService = assetService;
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Override
    public <U extends User> List<U> getUsersByRole(User.Role role, Class<U> clazz) {
        return this.userRepo.findUsersByRole(role, clazz);
    }

    @Override
    public User updateUser(int id, JsonUserData body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Optional<User> found = this.userRepo.findById(id);

        if (found.isEmpty()) throw new IllegalStateException("There is no user found with that id!");

        User user = found.get();

        if (user instanceof Admin admin) {
            Admin newAdminData = mapper.readValue(body.getUser(), Admin.class);

            admin.setEmail(newAdminData.getEmail());

            if (body.getAvatarFile() != null) {
                String extension = FilenameUtils.getExtension(body.getAvatarFile().getOriginalFilename());
                assetService.uploadAsset(body.getAvatarFile(), "users/avatars/" + user.getId() + "." + extension, true);

                admin.setAvatarUrl("users/avatars/" + user.getId() + "." + extension);
            }

            admin.setFirstName(newAdminData.getFirstName());
            admin.setLastName(newAdminData.getLastName());
        } else if (user instanceof Specialist specialist) {
            Specialist newSpecialistData = mapper.readValue(body.getUser(), Specialist.class);

            specialist.setEmail(newSpecialistData.getEmail());

            if (body.getAvatarFile() != null) {
                String extension = FilenameUtils.getExtension(body.getAvatarFile().getOriginalFilename());
                System.out.println("users/avatars/" + user.getId() + "." + extension);
                assetService.uploadAsset(body.getAvatarFile(), "users/avatars/" + user.getId() + "." + extension, true);

                specialist.setAvatarUrl("users/avatars/" + user.getId() + "." + extension);
            }

            specialist.setFirstName(newSpecialistData.getFirstName());
            specialist.setLastName(newSpecialistData.getLastName());

            Address newAddress = mapper.readValue(body.getAddress(), Address.class);

            specialist.setAddress(newAddress);
        } else if (user instanceof Client client) {
            Client newClientData = mapper.readValue(body.getUser(), Client.class);

            client.setEmail(newClientData.getEmail());

            if (body.getAvatarFile() != null) {
                String extension = FilenameUtils.getExtension(body.getAvatarFile().getOriginalFilename());
                assetService.uploadAsset(body.getAvatarFile(), "users/avatars/" + user.getId() + "." + extension, true);

                client.setAvatarUrl("users/avatars/" + user.getId() + "." + extension);
            }

            if (body.getBannerFile() != null) {
                String extension = FilenameUtils.getExtension(body.getBannerFile().getOriginalFilename());
                assetService.uploadAsset(body.getBannerFile(), "projects/" + user.getId() + "." + extension, true);

                client.setBannerSrc("projects/" + user.getId() + "." + extension);
            }

            client.setName(newClientData.getName());

        }

        System.out.println(user.getRole());

        return this.userRepo.save(user);
    }

    @Override
    public User addAdmin(JsonNode body) {
        if (body.get("email") == null || body.get("password") == null || body.get("avatarUrl") == null)
            throw new IllegalStateException("The fields email and/or password and/or avatarUrl isn't found!");

        String email = body.get("email").asText();
        String password = body.get("password").asText();
        String avatarUrl = body.get("avatarUrl").asText();

        JsonNode firstName = body.get("firstName");
        JsonNode lastName = body.get("lastName");

        if (firstName == null || lastName == null)
            throw new IllegalStateException("The fields firstName and/or lastName isn't found!");

        User admin = new Admin(-1, email, password, avatarUrl, firstName.asText(), lastName.asText());

        return this.userRepo.save(admin);
    }

    @Override
    public User addClient(AddClientRequestBody body) throws IOException {
        User user = new Client(
                -1,
                body.getEmail(),
                body.getPassword(),
                body.getAvatarUrl().getOriginalFilename(),
                body.getName(),
                null);

        final MultipartFile avatar = body.getAvatarUrl();
        if (avatar != null) {
            // uploading the logo to the assets.
            String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());
            String fileName = String.format("users/avatars/%s.%s", UUID.randomUUID(), extension);
            final FileResult fileResult = assetService.uploadAsset(avatar, fileName);

            user.setAvatarUrl(fileName);
            // returning the updated project with the generated logo upload src.
        }
        return this.userRepo.save(user);
    }

    @Override
    public User addSpecialist(AddSpecialistRequestBody body) throws IOException {
        User user = new Specialist(
                -1,
                body.getEmail(),
                body.getPassword(),
                null,
                body.getFirstname(),
                body.getLastname());

        if (body.getAvatarUrl() != null) {
            // uploading the logo to the assets.
            String extension = FilenameUtils.getExtension(body.getAvatarUrl().getOriginalFilename());
            String fileName = String.format("users/avatars/%s.%s", UUID.randomUUID(), extension);
            final FileResult fileResult = assetService.uploadAsset(body.getAvatarUrl(), fileName);

            user.setAvatarUrl(fileName);
            // returning the updated project with the generated logo upload src.
        }

        return this.userRepo.save(user);
    }

    @Override
    public User deleteUserById(int id) {
        final Optional<User> found = this.userRepo.findById(id);

        if (found.isEmpty()) throw new IllegalStateException("There is no user found with that id!");

        this.userRepo.deleteById(id);
        return found.get();
    }

    @Override
    public Address getUsersAddressById(int id) {
        User user = this.userRepo.findById(id).orElse(null);
        if (!(user instanceof Specialist specialist)) return null;

        return specialist.getAddress();
    }

    @Override
    public String getResume(int id) {
        Optional<User> found = this.userRepo.findById(id);

        if (found.isEmpty()) throw new IllegalStateException("There is no user found with that id!");

        User user = found.get();

        String resumeURL = null;

        if (user instanceof Specialist specialist) {
            System.out.println("test1");
            System.out.println(specialist.getResumeURL());
            resumeURL = specialist.getResumeURL();
        }

        return resumeURL;
    }

    @Override
    public String updateResume(int id, JsonUserData body) throws IOException {
        Optional<User> found = this.userRepo.findById(id);

        if (found.isEmpty()) throw new IllegalStateException("There is no user found with that id!");

        User user = found.get();

        String resumeURL = null;
        if (user instanceof Specialist specialist) {
            if (body.getResumeFile() != null) {
                String extension = FilenameUtils.getExtension(body.getResumeFile().getOriginalFilename());
                assetService.uploadAsset(body.getResumeFile(), "users/resumes/" + user.getId() + "." + extension, true);

                specialist.setResumeURL("users/resumes/" + user.getId() + "." + extension);
                this.userRepo.save(user);


                resumeURL = specialist.getResumeURL();
            }
        }

        return resumeURL;
    }

    @Override
    public List<UserRepository.UserCount> getUserCounts() {
        return this.userRepo.getUserCounts();
    }
}
