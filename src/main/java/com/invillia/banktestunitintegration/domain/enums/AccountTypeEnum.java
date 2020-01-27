package com.invillia.banktestunitintegration.domain.enums;

public enum AccountTypeEnum {
    SAVINGS,
    CHECKING;


    public static AccountTypeEnum toEnum(String typeEnumString) {

        if (typeEnumString == null || typeEnumString.isBlank()) {
            return null;
        }

        for (AccountTypeEnum typeEnum : AccountTypeEnum.values()) {

            if (typeEnum.toString().equals(typeEnumString)) {
                return typeEnum;
            }
        }

        throw new IllegalArgumentException("Tipo de conta inválida: " + typeEnumString);
    }
}