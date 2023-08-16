package com.example.cafe.domain.menu;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

public class StarbucksMenuBoard implements MenuBoard {

    private List<StarbucksMenuInfo> menus;

    public StarbucksMenuBoard(List<StarbucksMenuInfo> menus) {
        this.menus = menus;
    }

    public static StarbucksMenuBoard initBasicStarbucksMenuBoard() {
        List<StarbucksMenuInfo> starbucksMenuInfos = List.of(
                new StarbucksMenuInfo(1L, "아메리카노", 5000L),
                new StarbucksMenuInfo(2L, "헤이즐넛 아메리카노", 5500L),
                new StarbucksMenuInfo(3L, "카페라떼", 5500L),
                new StarbucksMenuInfo(4L, "카페모카", 6000L)
        );

        return new StarbucksMenuBoard(starbucksMenuInfos);
    }

    @Nullable
    @Override
    public void showMenus() {
        if (menus == null) {
            return;
        }

        menus.forEach(StarbucksMenuInfo::showMenu);
    }

    @AllArgsConstructor
    public static class StarbucksMenuInfo {
        private Long id;
        private String menuName;
        private Long price;

        public void showMenu() {
            System.out.println("[ID] " + this.id + ", [메뉴명] : " + this.menuName + ", price: " + this.price);
        }
    }
}
