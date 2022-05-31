package com.stevenson.storage.service;

import com.stevenson.storage.api.controller.request.FileUploadRequest;
import com.stevenson.storage.model.StorageModel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class DefaultFileService implements FileService{
    private static final String defaultDirectory = "/tmp/directory/";

    public StorageModel uploadFile(FileUploadRequest request) throws IllegalStateException, IOException {
        String relativeFilePath = "/";
        if(request.getDirectory() != null){
            relativeFilePath+=request.getDirectory();
        }
        relativeFilePath += "/"+ request.getFile().getOriginalFilename();
        System.out.println(relativeFilePath);
        File actualFile = new File(defaultDirectory+relativeFilePath);
        if (!actualFile.getParentFile().exists()) {
            actualFile.getParentFile().mkdirs();
        }
        request.getFile().transferTo(actualFile);
        return getStorageModel(relativeFilePath);
    }

    @Override
    public StorageModel retrieveFile(String relativeFilePath) throws IOException {
        return getStorageModel(relativeFilePath);
    }

    @Override
    public StorageModel createDirectory(String relativeFilePath) throws IOException {
        Path filepath = Paths.get(defaultDirectory+"/"+relativeFilePath);
        if(!Files.exists(filepath)){
            File pathAsFile = new File(defaultDirectory+"/"+relativeFilePath);
            pathAsFile.mkdir();
        }
        return this.retrieveDirectory(relativeFilePath);
    }

    @Override
    public StorageModel retrieveDirectory(String relativeFilePath) throws IOException {
        return getStorageModel(relativeFilePath);
    }
    @Override
    public List<StorageModel> retrieveDirectoryContents(String relativeFilePath) throws IOException {
        List<StorageModel> fileModels = new ArrayList<>();
        Path filepath = Paths.get(defaultDirectory+"/"+relativeFilePath);
        BasicFileAttributes attribs = Files.readAttributes(filepath, BasicFileAttributes.class);
        if(attribs.isDirectory()) {
            String folderPath = defaultDirectory+"/"+relativeFilePath;
            File folder = new File(folderPath);
            BasicFileAttributes innerFileAttrib = Files.readAttributes(Paths.get(folder.getPath()), BasicFileAttributes.class);
            File[] files = folder.listFiles();
            int count = files.length;
            for (File file : files) {
                System.out.println(file.getPath());
                String innerFilePath = file.getPath().split(defaultDirectory)[1];
                StorageModel fileDto = getStorageModel(innerFilePath);
                if (!file.isFile()) {
                    fileDto.setSize(getFolderSize(file));
                }
                fileModels.add(fileDto);
            }
        }
        return fileModels;
    }

    private static long getFolderSize(File folder)
    {
        long size = 0;
        File[] files = folder.listFiles();
        int count = files.length;
        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }
        return size;
    }

    private static StorageModel getStorageModel(String relativeFilePath) throws IOException {
        File file = new File(defaultDirectory + relativeFilePath);
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        String type = (basicFileAttributes.isRegularFile())? "file": "directory";
        long size = basicFileAttributes.size();
        if(basicFileAttributes.isDirectory()){
            File folder = new File(defaultDirectory+"/"+relativeFilePath);
            size = getFolderSize(folder);
        }
        long timestamp = basicFileAttributes.lastModifiedTime().toMillis();
        return StorageModel.builder()
                .name(relativeFilePath)
                .type(type)
                .size(size)
                .createdAt(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId() ))
                .build();
    }

}
