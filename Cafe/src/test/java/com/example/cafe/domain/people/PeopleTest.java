package com.example.cafe.domain.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class PeopleTest {
    People user1;
    People user2;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder()
                .name("상현")
                .build();
        user2 = User.builder()
                .name("현상")
                .build();
    }

    @Test
    void 이용자_생성_테스트() {
        assertThat(1L).isEqualTo(user1.getId());
        assertThat(2L).isEqualTo(user2.getId());
    }


}