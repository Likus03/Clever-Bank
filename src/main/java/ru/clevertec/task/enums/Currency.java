package ru.clevertec.task.enums;

import java.util.List;

public enum Currency {
    EUR,
    USD,
    BYN;
    private static final List<Currency> currencies = List.of(values());
    public static List<Currency> getCurrencyList() {
        return List.copyOf(currencies);
    }
}
