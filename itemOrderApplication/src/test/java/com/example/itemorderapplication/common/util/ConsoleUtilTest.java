package com.example.itemorderapplication.common.util;

import com.example.itemorderapplication.common.Const;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConsoleUtilTest {

    @DisplayName("콘솔 종료 여부 테스트")
    @Test
    void 콘솔_종료_여부_테스트() {
        // true
        Assertions.assertThat(ConsoleUtil.isFinishConsole("Q")).isTrue();
        Assertions.assertThat(ConsoleUtil.isFinishConsole("q")).isTrue();
        Assertions.assertThat(ConsoleUtil.isFinishConsole(Const.QUIT)).isTrue();

        // test
        Assertions.assertThat(ConsoleUtil.isFinishConsole(" ")).isFalse();
        Assertions.assertThat(ConsoleUtil.isFinishConsole("")).isFalse();
        Assertions.assertThat(ConsoleUtil.isFinishConsole(null)).isFalse();
    }
}