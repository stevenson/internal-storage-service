package com.stevenson.storage.api.controller;

import com.stevenson.storage.model.StorageModel;
import com.stevenson.storage.service.DefaultFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DefaultDirectoryControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DefaultFileService mockService;

    private StorageModel sm = StorageModel.builder()
            .name("somedirectory")
            .type("file")
            .size(100000)
            .createdAt(LocalDateTime.now())
            .build();
    @Test
    public void postShouldCreateDirectory() throws IOException {
        given(this.mockService.uploadFile(any())).willReturn(sm);

        ResponseEntity<StorageModel> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/directories",
                sm,
                StorageModel.class);
        assertThat(response, is(notNullValue()));
    }

}