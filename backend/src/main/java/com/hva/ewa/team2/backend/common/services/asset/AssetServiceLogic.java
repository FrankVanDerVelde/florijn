package com.hva.ewa.team2.backend.common.services.asset;

import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AssetServiceLogic {

    Resource getAsset(String assetName);

    FileResult uploadAsset(MultipartFile file) throws IOException;

    FileResult uploadAsset(MultipartFile file, String fileName) throws IOException;

    FileResult uploadAsset(MultipartFile file, String fileName, boolean replaceSameNames) throws IOException;

    String deleteAsset(String fileName);

}
