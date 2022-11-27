package com.hva.ewa.team2.backend.domain.usecases.asset;

import com.hva.ewa.team2.backend.common.services.asset.AssetServiceLogic;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AssetInteractor implements AssetBusinessLogic {

    private AssetServiceLogic assetService;

    @Autowired
    public AssetInteractor(AssetServiceLogic assetServiceLogic) {
        this.assetService = assetServiceLogic;
    }

    @Override
    public FileResult uploadFile(MultipartFile file) throws IOException {
        return assetService.uploadAsset(file);
    }

    @Override
    public FileResult uploadFile(MultipartFile file, String fileName) throws IOException {
        return assetService.uploadAsset(file, fileName);
    }

    @Override
    public Resource getAsset(String assetName) {
        return assetService.getAsset(assetName);
    }

}
