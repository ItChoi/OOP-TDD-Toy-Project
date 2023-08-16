package com.example.cafe.domain.cafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StarbucksTest {

    @BeforeEach
    void beforeEach() {
        People user1 = User.builder()
                .name("상현")
                .build();

        People user2 = User.builder()
                .name("현상")
                .build();
    }

    @Test
    public void 카페_입장_테스트() {

    }


}