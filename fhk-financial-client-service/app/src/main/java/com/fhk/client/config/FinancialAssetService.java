package com.fhk.client.config;

import com.fhk.asset.client.asset.AssetRegisterRequestDTO;
import com.fhk.asset.client.asset.AssetResponseDTO;
import com.fhk.asset.client.asset.AssetServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialAssetService {

  private final AssetServiceClient assetServiceClient;

    public AssetResponseDTO registerAsset(AssetRegisterRequestDTO request) {
        return assetServiceClient.register(request);
    }

}
