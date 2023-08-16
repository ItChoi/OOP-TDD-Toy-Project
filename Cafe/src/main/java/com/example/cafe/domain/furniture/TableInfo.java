package com.example.cafe.domain.furniture;

import com.example.cafe.domain.people.User;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableInfo {
    private List<Table> tables;
    private Map<Long, Table> tableWithId;

    public TableInfo(List<Table> tables) {
        this.tables = tables;
        this.tableWithId = tables.stream()
                .collect(Collectors.toMap(Table::getId, Function.identity()));
    }

    public static TableInfo initBasicTableInfo() {
        List<Table> tables = List.of(
                new Table(1L, 4),
                new Table(2L, 2),
                new Table(3L, 3),
                new Table(4L, 4)
        );

        return new TableInfo(tables);
    }

    public boolean isAvailableTable() {
        return tables.stream()
                .anyMatch(table -> !table.isUseTable);
    }

    public void leaveTable(Long userId) {
        for (Table table : tables) {
            table.leaveTable(userId);
        }
    }
}
