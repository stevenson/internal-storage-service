package com.stevenson.storage.api.controller;

import com.stevenson.storage.api.controller.request.FileUploadRequest;
import org.springframework.http.ResponseEntity;

public interface FileController {
    ResponseEntity<?> uploadFile(FileUploadRequest request);
}
