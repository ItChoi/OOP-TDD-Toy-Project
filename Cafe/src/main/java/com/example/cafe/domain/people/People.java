package com.example.cafe.domain.people;

import lombok.Builder;
import lombok.Getter;

@Getter
public class People {
    public static Long CREATOR_USER_ID = 1L;

    private long id;
    private String name;

    public People(String name) {
        this.id = CREATOR_USER_ID++;
        this.name = name;
    }
}
