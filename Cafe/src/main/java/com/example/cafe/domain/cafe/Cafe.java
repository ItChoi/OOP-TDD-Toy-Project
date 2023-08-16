package com.example.cafe.domain.cafe;

import com.example.cafe.domain.people.User;

import java.util.List;

public interface Cafe {
    void enter(User user); // 입장
    void exit(User user); // 퇴장
    void showMenus(); // 메뉴 보여주기
    boolean isAvailableTable(); // 이용 가능한 테이블 존재
    boolean isAvailableSeatBy(Long tableId); // 테이블에 자리 존재 유무

}

