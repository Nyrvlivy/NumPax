package br.com.numpax.application.enums;

public enum CategoryType {
    ACCOUNTS,
    TRANSACTIONS,
    PERSONAL,
    EXPENSE,
    INCOME,
    SAVINGS,
    INVESTMENT;

    public static CategoryType fromString(String value) {
        try {
            return CategoryType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de categoria inválido: " + value);
        }
    }
}
