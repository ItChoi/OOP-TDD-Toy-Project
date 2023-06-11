package com.example.itemorderapplication.repository;

import com.example.itemorderapplication.common.util.PriceUtil;
import com.example.itemorderapplication.domain.Item;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class InitDataReader {
    public static ConcurrentLinkedQueue<Item> itemDatas = initItems();

    public static final int CSV_FILE_LENGTH = 4;
    public static final int ITEM_ID_INDEX_IN_CSV = 0;
    public static final int ITEM_NAME_INDEX_IN_CSV = 1;
    public static final int ITEM_PRICE_INDEX_IN_CSV = 2;
    public static final int ITEM_STOCK_QUANTITY_INDEX_IN_CSV = 3;
    public static final String ITEM_FILE_PATH = "itemOrderApplication/src/main/resources/static/data/csv/items.csv";

    private static ConcurrentLinkedQueue<Item> initItems() {
        if (Objects.nonNull(itemDatas) && itemDatas.size() > 0) {
            return itemDatas;
        }

        return getDirectItems();
    }

    private static ConcurrentLinkedQueue<Item> getDirectItems() {
        ConcurrentLinkedQueue<Item> results = new ConcurrentLinkedQueue<>();
        String[] csvRow;
        try (CSVReader reader = new CSVReader(new FileReader(ITEM_FILE_PATH))) {
            reader.readNext();
            while ((csvRow = reader.readNext()) != null) {
                if (isCorrectRowForCsv(csvRow)) {
                    long itemId = Long.parseLong(csvRow[ITEM_ID_INDEX_IN_CSV]);
                    String itemName = csvRow[ITEM_NAME_INDEX_IN_CSV];
                    int price = Integer.parseInt(csvRow[ITEM_PRICE_INDEX_IN_CSV]);
                    int stockQuantity = Integer.parseInt(csvRow[ITEM_STOCK_QUANTITY_INDEX_IN_CSV]);

                    results.add(new Item(itemId, itemName, price, stockQuantity));
                }
            }
        } catch (IOException | CsvValidationException e) {
            log.error("[EXCEPTION] {}", e);
        }
        return results;
    }

    public static boolean isCorrectRowForCsv(String[] csvRow) {
        return isCorrectRowLengthForCsv(csvRow) && isCorrectRowDataTypeForCsv(csvRow);
    }

    private static boolean isCorrectRowLengthForCsv(String[] csvRow) {
        return csvRow != null && csvRow.length == CSV_FILE_LENGTH;
    }


    private static boolean isCorrectRowDataTypeForCsv(String[] csvRow) {
        if (!isCorrectRowLengthForCsv(csvRow)) {
            return false;
        }

        return PriceUtil.isOnlyDigit(csvRow[ITEM_ID_INDEX_IN_CSV])
                && StringUtils.hasText(csvRow[ITEM_NAME_INDEX_IN_CSV])
                && PriceUtil.isOnlyDigit(csvRow[ITEM_PRICE_INDEX_IN_CSV])
                && PriceUtil.isOnlyDigit(csvRow[ITEM_STOCK_QUANTITY_INDEX_IN_CSV]);

    }
}
