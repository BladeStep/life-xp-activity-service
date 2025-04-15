package com.bladestepapp.lifexpactivityservicetest.integration.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.UserGateway;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.IntegrationTest;
import com.bladestepapp.model.MonoUserResponseDto;
import com.bladestepapp.model.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@IntegrationTest
class UserGatewayIntegrationTest {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WireMock wireMock;

    @Test
    @SneakyThrows
    void shouldReturnUserWhenFound() {
        //given
        UUID userId = UUID.randomUUID();
        String userName = "Test User";
        String userEmail = "test@example.com";
        MonoUserResponseDto monoUserResponseDto = new MonoUserResponseDto();
        monoUserResponseDto.setIsSuccess(true);
        monoUserResponseDto.setData(new UserResponseDto(userId, userName, userEmail));

        String userJson = objectMapper.writeValueAsString(monoUserResponseDto);

        wireMock.register(get(urlPathMatching("/api/user/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(userJson)));

        //when
        var result = userGateway.find(userId);

        //then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals(userName, result.get().getName());
        assertEquals(userEmail, result.get().getEmail());
    }

    @Test
    @SneakyThrows
    void shouldReturnEmptyWhenUserNotFound() {
        //given,when
        UUID userId = UUID.randomUUID();
        MonoUserResponseDto monoUserResponseDto = new MonoUserResponseDto();
        monoUserResponseDto.setIsSuccess(false);
        monoUserResponseDto.setErrorMessage("Not Found");

        String userJson = objectMapper.writeValueAsString(monoUserResponseDto);

        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(userJson)));

        //then
        assertTrue(userGateway.find(userId).isEmpty());
    }

    @Test
    @SneakyThrows
    void shouldThrowExceptionWhenServerError() {
        //given,when
        UUID userId = UUID.randomUUID();
        MonoUserResponseDto monoUserResponseDto = new MonoUserResponseDto();
        monoUserResponseDto.setIsSuccess(false);
        monoUserResponseDto.setErrorMessage("Server error");

        String userJson = objectMapper.writeValueAsString(monoUserResponseDto);

        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(userJson)));

        //then
        assertThrows(ResponseStatusException.class, () -> userGateway.find(userId));
    }
}