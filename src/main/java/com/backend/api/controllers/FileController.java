package com.backend.api.controllers;

import com.backend.api.dtos.FileDto;
import com.backend.api.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/files")
public class FileController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] file) {
        try {
            Arrays.stream(file).forEach(storageService::save);
            return new ResponseEntity<>(String.format("Successfully upload "), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Failed to upload. Error: %s", e.getMessage()), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FileDto>> getListOfFiles() {
        List<FileDto> fileInfos = storageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileDto(fileName, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile (@PathVariable String fileName) {
        Resource file = storageService.load(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
