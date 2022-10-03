package com.backend.api.controllers;

import com.backend.api.services.BarcodeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/qr")
public class BarcodeController {
    @Autowired
    private BarcodeService barcodeService;

    @GetMapping("/image")
    public ResponseEntity<?> getBase64QRImage(@RequestParam String text) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeService.generateQRCode(text), "png", baos);
            return ResponseEntity.ok().body("data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadQRCode(@RequestParam String text, final HttpServletResponse response) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeService.generateQRCode(text), "png", baos);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename=qr.jpg");
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
            IOUtils.copy(inputStream, response.getOutputStream());
            IOUtils.closeQuietly(response.getOutputStream());
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
