package com.stevenson.storage.api.controller;

import com.stevenson.storage.api.controller.dto.DirectoryDetailResponse;
import com.stevenson.storage.api.controller.dto.DirectoryRequest;
import com.stevenson.storage.api.controller.dto.FileDetailResponse;
import com.stevenson.storage.service.DefaultFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("api/v1/directory")
@RestController
public class DefaultDirectoryController {
    @Autowired
    private final DefaultFileService fileService;

    public DefaultDirectoryController(DefaultFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> createdDirectory(DirectoryRequest request) throws IOException {
        DirectoryDetailResponse data = fileService.createDirectory(request.getPath());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> retrieveDirectory(@RequestParam String path ){
        try {
            DirectoryDetailResponse data = fileService.retrieveDirectory(path);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
