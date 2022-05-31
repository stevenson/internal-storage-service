package com.stevenson.storage.api.controller;

import com.stevenson.storage.api.controller.dto.FileUploadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {
    ResponseEntity<?> uploadFile(FileUploadRequest request);
}
