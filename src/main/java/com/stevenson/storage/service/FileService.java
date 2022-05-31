package com.stevenson.storage.service;

import com.stevenson.storage.api.controller.request.FileUploadRequest;
import com.stevenson.storage.model.StorageModel;

import java.io.IOException;
import java.util.List;

public interface FileService {
    StorageModel uploadFile(FileUploadRequest request) throws IllegalStateException, IOException;
    StorageModel retrieveFile(String filePath) throws IOException;
    StorageModel createDirectory(String relativeFilePath) throws IOException;
    StorageModel retrieveDirectory(String relativeFilePath) throws IOException;
    List<StorageModel> retrieveDirectoryContents(String relativeFilePath) throws IOException;

}
