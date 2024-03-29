package com.wizardlybump17.rinha.account.transaction;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum TransactionType {

    DEPOSIT("c"), //creditar
    WITHDRAW("d"); //debitar

    private final @NonNull String display;

    public static @NonNull TransactionType fromDisplay(@NonNull String display) {
        for (TransactionType type : values())
            if (type.display.equals(display))
                return type;
        throw new IllegalArgumentException("Invalid display");
    }
}
