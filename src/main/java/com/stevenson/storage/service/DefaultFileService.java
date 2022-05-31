package com.stevenson.storage.service;

import com.stevenson.storage.api.controller.dto.DirectoryDetailResponse;
import com.stevenson.storage.api.controller.dto.FileUploadRequest;
import com.stevenson.storage.api.controller.dto.FileDetailResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
public class DefaultFileService implements FileService{
    private final String defaultDirectory = "/tmp/directory/";

    public FileDetailResponse uploadFile(FileUploadRequest request) throws IllegalStateException, IOException {

        String pathName = defaultDirectory;
        if(request.getDirectory() != null){
            pathName+=request.getDirectory()+'/';
        }
        File actualFile = new File(pathName+request.getFile().getName());
        if (!actualFile.getParentFile().exists()) {
            actualFile.getParentFile().mkdirs();
        }
        request.getFile().transferTo(actualFile);
        long timestamp = actualFile.lastModified();
        FileDetailResponse fileDto = FileDetailResponse.builder()
                .name(request.getFile().getOriginalFilename())
                .directory(request.getDirectory())
                .size(request.getFile().getSize())
                .createdAt(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId() ))
                .build();

        return fileDto;
    }

    @Override
    public FileDetailResponse retrieveFile(String relativeFilePath) throws IOException {
        Path file = Paths.get(defaultDirectory+'/'+relativeFilePath);
        BasicFileAttributes attribs = Files.readAttributes(file, BasicFileAttributes.class);
        FileDetailResponse fileDto = FileDetailResponse.builder()
                .name(String.valueOf(file.getFileName()))
                .directory(relativeFilePath)
                .size(attribs.size())
                .createdAt(LocalDateTime.ofInstant(
                        attribs.creationTime().toInstant(), TimeZone.getDefault().toZoneId() ))
                .build();

        return fileDto;
    }

    @Override
    public DirectoryDetailResponse createDirectory(String relativeFilePath) throws IOException {
        Path filepath = Paths.get(defaultDirectory+"/"+relativeFilePath);
        if(!Files.exists(filepath)){
            File pathAsFile = new File(defaultDirectory+"/"+relativeFilePath);
            pathAsFile.mkdir();
        }
        return this.retrieveDirectory(relativeFilePath);
    }

    @Override
    public DirectoryDetailResponse retrieveDirectory(String relativeFilePath) throws IOException {
        Path filepath = Paths.get(defaultDirectory+"/"+relativeFilePath);
        BasicFileAttributes attribs = Files.readAttributes(filepath, BasicFileAttributes.class);
        long size = attribs.size();
        if(attribs.isDirectory()){
            File folder = new File(defaultDirectory+"/"+relativeFilePath);
            size = getFolderSize(folder);
        }
        DirectoryDetailResponse dto = DirectoryDetailResponse.builder()
                .directory(relativeFilePath)
                .size(size)
                .createdAt(LocalDateTime.ofInstant(
                        attribs.creationTime().toInstant(), TimeZone.getDefault().toZoneId() ))
                .build();
        return dto;
    }

    private static long getFolderSize(File folder)
    {
        long size = 0;
        File[] files = folder.listFiles();
        int count = files.length;
        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                size += files[i].length();
            }
            else {
                size += getFolderSize(files[i]);
            }
        }
        return size;
    }

}
