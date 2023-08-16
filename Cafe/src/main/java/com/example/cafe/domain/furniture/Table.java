package com.example.cafe.domain.furniture;

import com.example.cafe.domain.people.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Table {
    private Long id;
    private boolean isUseTable;
    private int chairNumber;
    private List<User> useUsers;

    public Table(Long id,
                 int chairNumber) {
        this.id = id;
        this.isUseTable = false;
        this.chairNumber = chairNumber;
        this.useUsers = new ArrayList<>();
    }

    public boolean isAvailableSeatBy() {
        return useUsers != null && useUsers.size() != 0 && chairNumber - useUsers.size() > 0;
    }

    public void leaveTable(Long userId) {
        List<User> useUsers = this.useUsers;
        if (useUsers.size() == 0) {
            return;
        }

        for (User user : useUsers) {
            if (userId.equals(user.getId())) {
                if (useUsers.size() == 1) {
                    isUseTable = false;
                }

                useUsers.remove(id);
            }
        }
    }

}
