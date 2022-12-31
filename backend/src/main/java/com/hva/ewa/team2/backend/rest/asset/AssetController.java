package com.hva.ewa.team2.backend.rest.asset;


import com.hva.ewa.team2.backend.domain.usecases.asset.AssetBusinessLogic;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetBusinessLogic assetBusinessLogic;

    @Autowired
    public AssetController(AssetBusinessLogic assetBusinessLogic) {
        this.assetBusinessLogic = assetBusinessLogic;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResult> uploadAsset(@RequestParam("file") MultipartFile file, @RequestParam("name") Optional<String> name) throws IOException {
        final FileResult body = name.isEmpty() ? assetBusinessLogic.uploadFile(file) : assetBusinessLogic.uploadFile(file, name.get());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/**")
    public void getAsset(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getRequestURI().split(request.getContextPath() + "/assets/")[1];

        try {
            Resource resource = assetBusinessLogic.getAsset(fileName);
            try (InputStream in = new FileInputStream(resource.getFile())) {
                response.setContentType(getTypeFromFileExtension(FilenameUtils.getExtension(resource.getFilename())));
                IOUtils.copy(in, response.getOutputStream());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Something went wrong whilst rendering the asset");
        }
    }

    private String getTypeFromFileExtension(@Nullable String ext) {
        if (ext == null) return MediaType.ALL_VALUE;
        return switch (ext) {
            case "jpg, jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            case "gif" -> MediaType.IMAGE_GIF_VALUE;
            case "pdf" -> MediaType.APPLICATION_PDF_VALUE;
            default -> MediaType.ALL_VALUE;
        };
    }

}
