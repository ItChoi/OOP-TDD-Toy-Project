package com.example.cafe;

import com.example.cafe.console.ConsoleUtil;
import com.example.cafe.domain.cafe.Cafe;
import com.example.cafe.domain.cafe.Starbucks;
import com.example.cafe.domain.furniture.TableInfo;
import com.example.cafe.domain.menu.MenuBoard;
import com.example.cafe.domain.menu.StarbucksMenuBoard;
import com.example.cafe.domain.people.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.cafe.domain.furniture.TableInfo.*;
import static com.example.cafe.domain.menu.StarbucksMenuBoard.*;

public class CafeApplication {

    public static void main(String[] args) {
        /**
         * 객체 생성
         * 1. Cafe 카페 (인터페이스) - 스타벅스, 투썸, ....
         * 2. StoreInfo 매장 정보 (테이블, 의자)
         * 3. People 유저 (인터페이스 - 직원, 이용자) - 점장, 직원, 이용자
         * 4. MenuBoard 메뉴
         * 5. Order 주문
         *
         * - 카페의 구현체는 메뉴를 포함한다.
         * - 카페의 구현체는 테이블 정보를 포함한다 (개수, 테이블에 해당하는 의자)
         * - 주문은 독립적으로 실행하도록 별도로 둔다.
         */

        Map<Long, User> userWithUserId = new HashMap<>() {{
            put(1L, new User(1L, "최씨"));
            put(2L, new User(2L, "김씨"));
            put(3L, new User(3L, "이씨"));
            put(4L, new User(4L, "오씨"));
            put(5L, new User(5L, "박씨"));
            put(6L, new User(6L, "명씨"));
            put(7L, new User(7L, "호씨"));
            put(8L, new User(8L, "팍씨"));
        }};

        Cafe cafe = new Starbucks(
                initBasicStarbucksMenuBoard(),
                initBasicTableInfo()
        );

        String input = "";
        while (ConsoleUtil.isFinishConsole(input = ConsoleUtil.inputStr())) {

        }

        cafe.enter(choi);
        //cafe.



    }

}
