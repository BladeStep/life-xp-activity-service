package com.bladestepapp.lifexpactivityservicecore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private UUID id;

    private String name;

    private String email;

    public static User create(UUID id, String name, String email) {
        return new User(id, name, email);
    }
}