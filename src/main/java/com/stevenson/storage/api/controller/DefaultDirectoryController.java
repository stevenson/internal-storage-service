package com.stevenson.storage.api.controller;

import com.stevenson.storage.api.controller.exception.ApiStorageException;
import com.stevenson.storage.api.controller.request.DirectoryRequest;
import com.stevenson.storage.model.StorageModel;
import com.stevenson.storage.service.DefaultFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/directories")
@RestController
public class DefaultDirectoryController {
    @Autowired
    private final DefaultFileService fileService;

    public DefaultDirectoryController(DefaultFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> createdDirectory(DirectoryRequest request) throws IOException {
        StorageModel data = fileService.createDirectory(request.getPath());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> retrieveDirectory(@RequestParam String path) {
        try {
            StorageModel data = fileService.retrieveDirectory(path);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e){
            throw new ApiStorageException("File or path being referenced is non existent");
        }
    }
    @GetMapping("/contents")
    public ResponseEntity<?> retrieveDirectoryContents(@RequestParam String path) {
        try {
            List<StorageModel> data = fileService.retrieveDirectoryContents(path);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e){
            throw new ApiStorageException("File or path being referenced is non existent");
        }
    }

}
