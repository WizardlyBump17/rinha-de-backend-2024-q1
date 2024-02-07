package com.wizardlybump17.rinha.account.transaction;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

public record TransactionResult(int balance, int limit, @NonNull ResultType resultType, @Nullable Throwable error) {

    public TransactionResult {
        if (limit < 0)
            throw new IllegalArgumentException("The limit can't be negative");
    }

    public TransactionResult(int balance, int limit, @NonNull ResultType resultType) {
        this(balance, limit, resultType, null);
    }

    @RequiredArgsConstructor
    @Getter
    public enum ResultType {

        SUCCESS(200),
        NOT_ENOUGH_BALANCE(422),
        INVALID_AMOUNT(422),
        INVALID_ACCOUNT(404);

        private final int httpStatus;
    }
}
