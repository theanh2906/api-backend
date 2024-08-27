package com.backend.api.services;

import com.azure.security.keyvault.secrets.SecretClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyVaultService {
    @Autowired
    private SecretClient secretClient;
    public String getSecret(String key) {
        return secretClient.getSecret(key).getValue();
    }
}
