package com.example.cafe.domain.people;

import lombok.Builder;

public class Manager extends People {

    @Builder
    public Manager(String name) {
        super(name);
    }
}
