package com.hva.ewa.team2.backend.domain.usecases.asset;

import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AssetBusinessLogic {

    Resource getAsset(String assetName);

    FileResult uploadFile(MultipartFile file) throws IOException;

    FileResult uploadFile(MultipartFile file, String fileName) throws IOException;

}
