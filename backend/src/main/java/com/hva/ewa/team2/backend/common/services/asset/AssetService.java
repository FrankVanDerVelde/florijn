package com.hva.ewa.team2.backend.common.services.asset;

import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class AssetService implements AssetServiceLogic {

    private final Path rootLocation;

    public AssetService() {
        this.rootLocation = Path.of("backend/assets");
    }

    @Override
    public Resource getAsset(String assetName) {
        try {
            Path file = rootLocation.resolve(assetName);
            Resource resource = new org.springframework.core.io.UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + assetName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + assetName, e);
        }
    }

    @Override
    public FileResult uploadAsset(MultipartFile file) throws IOException {
        Assert.notNull(file, "File cannot be null");

        return this.uploadAsset(file, file.getOriginalFilename());
    }

    @Override
    public FileResult uploadAsset(MultipartFile file, String fileName) throws IOException {
        Assert.notNull(file, "File cannot be null");

        return this.uploadAsset(file, fileName, false);
    }

    @Override
    public FileResult uploadAsset(MultipartFile file, String fileName, boolean replaceSameNames) throws IOException {
        Assert.notNull(file, "File cannot be null");

        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }

        try {
            if (file.isEmpty()) throw new IllegalArgumentException("Failed to store empty file.");

            Path destination = this.rootLocation.resolve(
                    Path.of(fileName)
            ).normalize().toAbsolutePath();

            System.out.println(destination);

            // checking if they didn't somehow try to escape the root location.
            final Path absoluteRoot = this.rootLocation.toAbsolutePath();
            if (!destination.startsWith(absoluteRoot)) {
                throw new IllegalArgumentException("Cannot store file outside current directory.");
            }

            final File parent = destination.getParent().toFile();
            if (!parent.exists()) parent.mkdirs();

            if (replaceSameNames) {
                // delete all files with the same name but different extension in the folder of the file to upload
                final String fileNameWithoutExtension = FilenameUtils.removeExtension(destination.getFileName().toString());

                try (Stream<Path> stream = Files.list(destination.getParent())) {
                    stream.filter(path -> isSameName(path, fileNameWithoutExtension))
                            .map(Path::toFile)
                            .forEach(File::delete);
                }
            }

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            return new FileResult(destination,
                    destination.toString()
                            .replace(absoluteRoot.toString() + '\\', "")
                            .replaceAll("\\\\", "/"));
        } catch (Exception e) {
            throw new IOException("Failed to store file.", e);
        }
    }

    private boolean isSameName(Path path, String fileName) {
        final String s = FilenameUtils.removeExtension(path.getFileName().toString());

        System.out.println("Comparing " + s + " to " + fileName);

        return s
                .equals(fileName);
    }

    @Override
    public String deleteAsset(String fileName) {
        Path destination = this.rootLocation.resolve(
                Path.of(fileName)
        ).normalize().toAbsolutePath();

        if (!destination.toFile().exists()) {
            throw new IllegalArgumentException("File does not exist.");
        }

        return destination.toString();
    }
}
