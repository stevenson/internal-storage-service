package com.stevenson.storage.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface DirectoryController {

    ResponseEntity<?> createdDirectory(String path);
}
