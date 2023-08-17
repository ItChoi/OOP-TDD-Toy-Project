package com.example.cafe.repository;

import com.example.cafe.domain.people.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public static Map<Long, User> userWithUserId = new HashMap<>() {{
        put(1L, new User(1L, "최씨", false));
        put(2L, new User(2L, "김씨", false));
        put(3L, new User(3L, "이씨", false));
        put(4L, new User(4L, "오씨", false));
        put(5L, new User(5L, "박씨", false));
        put(6L, new User(6L, "명씨", false));
        put(7L, new User(7L, "호씨", false));
        put(8L, new User(8L, "팍씨", false));
    }};

    public static void showUsers() {
        for (Long key : userWithUserId.keySet()) {
            System.out.println(userWithUserId.get(key).toString());
        }
    }
}
