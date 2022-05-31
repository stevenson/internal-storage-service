package com.stevenson.storage.api.controller.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadRequest {
    @NotBlank(message = "File to be uploaded is required.")
    private MultipartFile file;
    @NotBlank(message = "Directory Path String is required.")
    private String directory;
}
