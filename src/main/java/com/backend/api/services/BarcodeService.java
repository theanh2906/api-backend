package com.backend.api.services;

import java.awt.image.BufferedImage;

public interface BarcodeService {
    BufferedImage generateQRCode(String barcodeText) throws Exception;
}
