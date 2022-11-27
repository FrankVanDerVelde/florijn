package com.hva.ewa.team2.backend.rest.asset;


import com.hva.ewa.team2.backend.domain.usecases.asset.AssetBusinessLogic;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println(name);
        final FileResult body = name.isEmpty() ? assetBusinessLogic.uploadFile(file) : assetBusinessLogic.uploadFile(file, name.get());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getAsset(@PathVariable String fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(assetBusinessLogic.getAsset(fileName));
    }


}
