package com.stevenson.storage.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectoryRequest {

    @NotBlank(message = "Directory Path String is required.")
    private String path;
}
