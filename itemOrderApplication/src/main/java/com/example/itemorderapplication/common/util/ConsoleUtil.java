package com.example.itemorderapplication.common.util;

import com.example.itemorderapplication.common.Const;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleUtil {

    private static Scanner scanner = getScanner();

    public static String inputStr() {
        makeNewScannerIfScannerIsClosed();
        return scanner.nextLine();
    }

    public static String inputStrPrefix(String prefix) {
        System.out.print(prefix + " : ");
        return inputStr();
    }

    private static void makeNewScannerIfScannerIsClosed() {
        if (Objects.isNull(scanner)) {
            scanner = getScanner();
        }
    }

    private static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static boolean isFinishConsole(String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        return Const.QUIT.equals(value.toLowerCase());
    }
}
