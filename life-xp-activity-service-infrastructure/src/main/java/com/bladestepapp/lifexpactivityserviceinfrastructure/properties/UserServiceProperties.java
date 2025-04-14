package com.bladestepapp.lifexpactivityserviceinfrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user-service")
@Getter
@Setter
public class UserServiceProperties {

    private String baseUrl;

    private String userPath;
}