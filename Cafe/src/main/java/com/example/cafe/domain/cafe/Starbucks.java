package com.example.cafe.domain.cafe;

import com.example.cafe.domain.furniture.TableInfo;
import com.example.cafe.domain.menu.MenuBoard;
import com.example.cafe.domain.people.User;

public class Starbucks implements Cafe {
    private final String storeName = "스타벅스";

    private MenuBoard menuBoard;
    private TableInfo tableInfo;

    public Starbucks(MenuBoard menuBoard,
                     TableInfo tableInfo) {
        this.menuBoard = menuBoard;
        this.tableInfo = tableInfo;
    }

    @Override
    public void enter(User user) {
        boolean isAvailableTable = isAvailableTable();
        String tableStr = isAvailableTable ? "가능" : "불가능";

        System.out.println(user.getName() + "님 " + this.storeName + "에 입장했습니다. 테이블 이용이" + tableStr + " 합니다.");
    }

    @Override
    public void exit(User user) {
        this.tableInfo.leaveTable(user.getId());

        // 테이블에 앉아있었다면, 해당 테이블의 자리를 비우고, 테이블 자체가 사용하지 않게 됐다면 테이블 이용 수량 제어 -> 테이블 개수 또는 자리 개수
        System.out.println(user.getName() + "님 " + this.storeName + "에서 퇴장했습니다.");
    }

    @Override
    public void showMenus() {
        this.menuBoard.showMenus();
    }

    @Override
    public boolean isAvailableTable() {
        return this.tableInfo.isAvailableTable();
    }

    @Override
    public boolean isAvailableSeatBy(Long tableId) {
        return false;
    }
}
