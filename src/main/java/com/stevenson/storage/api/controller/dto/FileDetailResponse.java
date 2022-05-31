package com.stevenson.storage.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDetailResponse {
    String name;
    String directory;
    long size;
    LocalDateTime createdAt;
}
