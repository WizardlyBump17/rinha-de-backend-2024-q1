package com.wizardlybump17.rinha.account.transaction;

import org.jetbrains.annotations.Nullable;

public record TransactionResult(int balance, int limit, @Nullable Throwable error) {

    public TransactionResult {
        if (limit < 0)
            throw new IllegalArgumentException("The limit can't be negative");
    }

    public TransactionResult(int balance, int limit) {
        this(balance, limit, null);
    }
}
