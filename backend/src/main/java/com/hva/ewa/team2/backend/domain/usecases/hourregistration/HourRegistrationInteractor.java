package com.hva.ewa.team2.backend.domain.usecases.hourregistration;

import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.hourregistration.UpdateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.hourregistration.UpdateHourRegistrationRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Primary
public class HourRegistrationInteractor implements HourRegistrationBusinessLogic {

    private final HourRegistrationRepository hourRegistrationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public HourRegistrationInteractor(
            HourRegistrationRepository hourRegistrationRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.hourRegistrationRepository = hourRegistrationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<HourRegistration> handleFetchHourRegistrationsByUser(int userId) {
        return hourRegistrationRepository.fetchAllHourRegistrationsByUser(userId);
    }

    @Override
    public List<HourRegistration> handleFetchHourRegistrationsForProject(int projectId) {
        return hourRegistrationRepository.fetchAllHourRegistrationByProject(projectId);
    }

    @Override
    public List<HourRegistration> handleFetchHourRegistrationsForProjectUser(int projectId, int userId) {
        final User user = userRepository.findById(userId);
        if (user instanceof Specialist specialist) {
            return hourRegistrationRepository.fetchAllHourRegistrationByProjectUser(projectId, userId);
        }

        // todo check if user is admin
        // todo check if user is client and member of the project.

        return handleFetchHourRegistrationsForProject(projectId);
    }

    @Override
    public Optional<HourRegistration> handleFetchHourRegistrationById(int id) {
        return hourRegistrationRepository.fetchHourRegistrationById(id);
    }

    @Override
    public Optional<HourRegistration> handleDeleteHourRegistrationById(int id) {
        try {
            return hourRegistrationRepository.deleteHourRegistration(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public HourRegistration handleCreateHourRegistration(CreateHourRegistrationRequest request) throws Exception {
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(request.getProjectId()));
        if (project.isEmpty())
            throw new Exception("Project not found for id: " + request.getProjectId());
        return hourRegistrationRepository.createHourRegistration(request, project.get());
    }

    @Override
    public HourRegistration handleUpdateHourRegistration(int id, UpdateHourRegistrationRequest request) throws Exception {
        Optional<HourRegistration> hr = hourRegistrationRepository.fetchHourRegistrationById(request.getId());

        if (hr.isEmpty())
            throw new Exception("HourRegistration not found for id: " + request.getId());

        HourRegistration updatedHR = hr.get();
        updatedHR.setFrom(request.getFrom());
        updatedHR.setTo(request.getTo());
        updatedHR.setDescription(request.getDescription());

        return hourRegistrationRepository.updateHourRegistration(id, updatedHR);
    }

    @Override
    public Optional<HourRegistration> handleAcceptHourRegistration(int id) {
        // TODO: Check user permission, only admins or clients can accept.
        Optional<HourRegistration> hr = hourRegistrationRepository.fetchHourRegistrationById(id);
        if (hr.isPresent()) {
            try {
                return Optional.of(updateStatusForHourRegistration(hr.get(), true));
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<HourRegistration> handleRejectHourRegistration(int id) {
        // TODO: Check user permission, only admins or clients can reject.
        Optional<HourRegistration> hr = hourRegistrationRepository.fetchHourRegistrationById(id);
        if (hr.isPresent()) {
            try {
                return Optional.of(updateStatusForHourRegistration(hr.get(), false));
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private HourRegistration updateStatusForHourRegistration(HourRegistration hr, boolean isAccepted) throws Exception {
        hr.setStatus(isAccepted ? HourRegistration.Status.ACCEPTED : HourRegistration.Status.REJECTED);
        return hourRegistrationRepository.updateHourRegistration(hr.getId(), hr);
    }
}
