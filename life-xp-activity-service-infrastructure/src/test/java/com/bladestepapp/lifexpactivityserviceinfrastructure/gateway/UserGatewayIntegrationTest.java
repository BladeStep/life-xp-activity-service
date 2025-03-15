package com.bladestepapp.lifexpactivityserviceinfrastructure.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bladestepapp.lifexpactivityserviceinfrastructure.config.IntegrationTestConfig;
import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.model.UserModelResponse;
import com.bladestepapp.lifexpactivityserviceinfrastructure.support.WireMockInitializer;
import com.bladestepapp.model.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@ContextConfiguration(initializers = WireMockInitializer.class)
class UserGatewayIntegrationTest {

    @Autowired
    private UserGateway userGateway;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }

    @Test
    void shouldReturnUser_whenUserExists() throws Exception {
        //given
        UUID userId = UUID.randomUUID();
        UserResponseDto userResponseDto = new UserResponseDto(userId, "John Doe", "john.doe@example.com");
        ObjectMapper objectMapper = new ObjectMapper();

        stubFor(get(urlEqualTo("/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(userResponseDto))));

        //when
        Optional<UserModelResponse> result = userGateway.get(userId);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userId);
        assertThat(result.get().getName()).isEqualTo("John Doe");
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");

        verify(getRequestedFor(urlEqualTo("/user/" + userId)));
    }

    @Test
    void shouldReturnEmpty_whenUserNotFound() {
        //given
        UUID userId = UUID.randomUUID();

        stubFor(get(urlEqualTo("/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        //when
        Optional<UserModelResponse> result = userGateway.get(userId);

        //then
        assertThat(result).isEmpty();

        verify(getRequestedFor(urlEqualTo("/user/" + userId)));
    }

    @Test
    void getUser_shouldThrowException_whenServerError() {
        //given
        UUID userId = UUID.randomUUID();

        stubFor(get(urlEqualTo("/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        //when,then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userGateway.get(userId);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(exception.getReason()).isEqualTo("Failed to fetch user due to Feign error");

        verify(getRequestedFor(urlEqualTo("/user/" + userId)));
    }

    @Test
    void getUser_shouldThrowException_whenFeignError() {
        //given
        UUID userId = UUID.randomUUID();

        stubFor(get(urlEqualTo("/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.GATEWAY_TIMEOUT.value())));

        //when,then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userGateway.get(userId);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(exception.getReason()).isEqualTo("Failed to fetch user due to Feign error");

        verify(getRequestedFor(urlEqualTo("/user/" + userId)));
    }
}