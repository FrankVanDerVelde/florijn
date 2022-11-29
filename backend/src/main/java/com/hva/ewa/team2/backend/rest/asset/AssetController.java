package com.hva.ewa.team2.backend.rest.asset;


import com.hva.ewa.team2.backend.domain.usecases.asset.AssetBusinessLogic;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public ResponseEntity<Resource> getAsset(HttpServletRequest request) {
        String fileName = request.getRequestURI().split(request.getContextPath() + "/assets/")[1];
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(assetBusinessLogic.getAsset(fileName));
    }


}
