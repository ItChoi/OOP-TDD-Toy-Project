package com.example.cafe;

import com.example.cafe.console.ConsoleUtil;
import com.example.cafe.domain.cafe.Cafe;
import com.example.cafe.domain.cafe.Starbucks;
import com.example.cafe.domain.order.Order;
import com.example.cafe.domain.people.User;
import com.example.cafe.repository.UserRepository;

import static com.example.cafe.domain.furniture.TableInfo.initBasicTableInfo;
import static com.example.cafe.domain.menu.StarbucksMenuBoard.initBasicStarbucksMenuBoard;

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


        Cafe cafe = new Starbucks(
                initBasicStarbucksMenuBoard(),
                initBasicTableInfo()
        );

        String input = "";
        while (ConsoleUtil.isFinishConsole(input = ConsoleUtil.inputStrWithLowerCase())) {

            if ("enter".equals(input) || "exit".equals(input)) {
                UserRepository.showUsers();
                System.out.print("입장 또는 퇴장할 유저의 ID를 입력해주세요.");
                User user = UserRepository.userWithUserId.get(Long.valueOf(ConsoleUtil.inputStrWithLowerCase()));
                if (user == null) {
                    System.out.println("존재하지 않는 유저입니다.");
                    continue;
                }

                if (user.isAvailableCafeStatus(input)) {
                    System.out.println("올바르게 입력해주세요.");
                    continue;
                }

                if ("enter".equals(input)) {
                    cafe.enter(user);
                    cafe.showMenus();


                }

                if ("exit".equals(input)) {
                    cafe.exit(user);
                }
            }
        }

        //cafe.
    }



}
