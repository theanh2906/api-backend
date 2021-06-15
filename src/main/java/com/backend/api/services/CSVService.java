package com.backend.api.services;

import java.io.File;
import java.util.List;

public interface CSVService {
    List<String> toJson(String filePath);
}
