package com.example.cafe.domain.people;

import lombok.Builder;

public class User extends People {

    @Builder
    public User(String name) {
        super(name);
    }
}
