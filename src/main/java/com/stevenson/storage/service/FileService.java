package com.stevenson.storage.service;

import com.stevenson.storage.api.controller.dto.DirectoryDetailResponse;
import com.stevenson.storage.api.controller.dto.FileUploadRequest;
import com.stevenson.storage.api.controller.dto.FileDetailResponse;

import java.io.IOException;

public interface FileService {
    FileDetailResponse uploadFile(FileUploadRequest request) throws IllegalStateException, IOException;

    FileDetailResponse retrieveFile(String filePath) throws IOException;
    DirectoryDetailResponse createDirectory(String relativeFilePath) throws IOException;
    DirectoryDetailResponse retrieveDirectory(String relativeFilePath) throws IOException;


}
