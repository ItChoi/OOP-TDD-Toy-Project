package com.example.cafe.domain.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    @Setter
    private boolean isEnterCafe;

    @Override
    public String toString() {
        return "[ID] " + this.id + " [NAME] " + this.name +" [in cafe] " + this.isEnterCafe;
    }

    public boolean isAvailableCafeStatus(String input) {
        if (!StringUtils.hasText(input)) {
            return false;
        }

        if ("enter".equals(input)) {
            return !isEnterCafe;
        }

        return isEnterCafe;
    }
}
