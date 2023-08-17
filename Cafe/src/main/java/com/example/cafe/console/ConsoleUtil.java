package com.example.cafe.console;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleUtil {

    private static Scanner scanner = getScanner();

    public static String inputStrWithLowerCase() {
        makeNewScannerIfScannerIsClosed();
        return scanner.nextLine();
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

        return "quit".equals(value.toLowerCase());
    }


}
