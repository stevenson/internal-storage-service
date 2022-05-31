package com.stevenson.storage.api.controller;

import com.stevenson.storage.api.controller.request.FileUploadRequest;
import com.stevenson.storage.model.StorageModel;
import com.stevenson.storage.service.DefaultFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/file")
@RestController
public class DefaultFileController implements FileController {

    @Autowired
    private final DefaultFileService fileService;

    public DefaultFileController(DefaultFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(FileUploadRequest request){
        try {
            StorageModel data = fileService.uploadFile(request);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<?> retrieveFile(@RequestParam String filepath){
        try {
            StorageModel data = fileService.retrieveFile(filepath);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
