package com.example.cafe.domain.people;

import lombok.Builder;

public class Barista extends People {

    @Builder
    public Barista(String name) {
        super(name);
    }
}
