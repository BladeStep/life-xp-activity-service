package com.bladestepapp.lifexpactivityservicetest.integration.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.UserGateway;
import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.model.UserModelResponse;
import com.bladestepapp.lifexpactivityservicetest.config.IntegrationTestConfig;
import com.bladestepapp.lifexpactivityservicetest.config.WireMockContainerConfig;
import com.bladestepapp.model.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@ContextConfiguration(initializers = WireMockContainerConfig.class)
@Import(WireMockContainerConfig.class)
class UserGatewayIntegrationTest {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private WireMock wireMock;

    @Test
    void shouldReturnUser_whenUserExists() throws Exception {
        //given
        UUID userId = UUID.randomUUID();

        UserResponseDto userResponseDto = new UserResponseDto(userId, "John Doe", "john.doe@example.com");
        ObjectMapper objectMapper = new ObjectMapper();

        String userJson = objectMapper.writeValueAsString(userResponseDto);
        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(userJson)));

        //when
        Optional<UserModelResponse> result = userGateway.get(userId);

        //then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(userId);
        Assertions.assertThat(result.get().getName()).isEqualTo("John Doe");
        Assertions.assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");

        wireMock.verifyThat(getRequestedFor(urlEqualTo("/api/user/" + userId)));
    }

    @Test
    void shouldReturnEmpty_whenUserNotFound() {
        //given
        UUID userId = UUID.randomUUID();

        wireMock.register(get(urlEqualTo("api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        //when
        Optional<UserModelResponse> result = userGateway.get(userId);

        //then
        Assertions.assertThat(result).isEmpty();

        wireMock.verifyThat(getRequestedFor(urlEqualTo("/api/user/" + userId)));
    }

    @Test
    void getUser_shouldThrowException_whenServerError() {
        //given
        UUID userId = UUID.randomUUID();

        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        //when,then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userGateway.get(userId);
        });

        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(exception.getReason()).isEqualTo("Failed to fetch user due to Feign error");

        wireMock.verifyThat(getRequestedFor(urlEqualTo("/api/user/" + userId)));
    }

    @Test
    void getUser_shouldThrowException_whenFeignError() {
        //given
        UUID userId = UUID.randomUUID();

        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.GATEWAY_TIMEOUT.value())));

        //when,then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userGateway.get(userId);
        });

        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(exception.getReason()).isEqualTo("Failed to fetch user due to Feign error");

        wireMock.verifyThat(getRequestedFor(urlEqualTo("/api/user/" + userId)));
    }
}