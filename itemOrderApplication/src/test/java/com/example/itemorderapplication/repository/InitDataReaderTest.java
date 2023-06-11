package com.example.itemorderapplication.repository;

import com.example.itemorderapplication.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.Assertions.assertThat;

class InitDataReaderTest {

    public static ConcurrentLinkedQueue<Item> itemDatas = InitDataReader.itemDatas;

    @DisplayName("데이터 저장소 - 아이템 리스트 체크 테스트")
    @Test
    void 데이터_저장소_아이템_리스트_체크_테스트() {
        assertThat(itemDatas).isNotNull();
    }

    @DisplayName("csv row 데이터 정확성 성공 테스트")
    @ParameterizedTest
    @CsvFileSource(resources = "/static/data/csv/items.csv", numLinesToSkip = 1)
    void csv_row_데이터_정확성_성공_테스트(Long id,
                                    String name,
                                    int price,
                                    int stockQuantity) {

        String[] csvRow = {
                String.valueOf(id),
                name,
                String.valueOf(price),
                String.valueOf(stockQuantity)
        };

        assertThat(InitDataReader.isCorrectRowForCsv(csvRow)).isTrue();
    }

    @DisplayName("csv row 데이터 정확성 실패 테스트")
    @ParameterizedTest
    @CsvFileSource(resources = "/static/data/csv/items-incorrect-test.csv", numLinesToSkip = 1)
    void csv_row_데이터_정확성_실패_테스트(String name,
                                int price,
                                int stockQuantity,
                                Long id) {

        String[] csvRow = {
                name,
                String.valueOf(price),
                String.valueOf(stockQuantity),
                String.valueOf(id)
        };

        assertThat(InitDataReader.isCorrectRowForCsv(csvRow)).isFalse();
        assertThat(InitDataReader.isCorrectRowForCsv(null)).isFalse();
        assertThat(InitDataReader.isCorrectRowForCsv(new String[] {})).isFalse();
    }


    @DisplayName("CSV 필드 인덱스 테스트")
    @Test
    void CSV_필드_인덱스_테스트() {
        assertThat(InitDataReader.CSV_FILE_LENGTH).isEqualTo(4);
        assertThat(InitDataReader.ITEM_ID_INDEX_IN_CSV).isEqualTo(0);
        assertThat(InitDataReader.ITEM_NAME_INDEX_IN_CSV).isEqualTo(1);
        assertThat(InitDataReader.ITEM_PRICE_INDEX_IN_CSV).isEqualTo(2);
        assertThat(InitDataReader.ITEM_STOCK_QUANTITY_INDEX_IN_CSV).isEqualTo(3);
    }
}