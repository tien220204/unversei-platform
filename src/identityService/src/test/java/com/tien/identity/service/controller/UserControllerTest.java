package com.tien.identity.service.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 05, 05);

        request = UserCreationRequest.builder()
                .username("Johnabcdnnnn")
                .firstname("Johnabcdnnnn")
                .lastname("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("cf0600f538b3")
                .username("Johnabcdnnnn")
                .firstname("Johnabcdnnnn")
                .lastname("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        // initData()
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String content = objectMapper.writeValueAsString(request);

        // tra ve response da viet thay vi chayj service
        Mockito.when(userService.createRequest(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
    }

    @Test
    void createUser_invalidUsername_fail() throws Exception {
        // GIVEN
        // initData()
        request.setUsername("joh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username requires more than 8 characters"));
    }
}
