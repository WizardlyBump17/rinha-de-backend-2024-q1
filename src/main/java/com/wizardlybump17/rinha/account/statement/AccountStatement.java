package com.wizardlybump17.rinha.account.statement;

import com.wizardlybump17.rinha.account.transaction.Transaction;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record AccountStatement(int balance, @NonNull Instant requestTime, int limit, @NonNull List<Transaction> transactions) {

    public static final int MAX_TRANSACTIONS = 10;

    public AccountStatement {
        if (transactions.size() > MAX_TRANSACTIONS)
            throw new IllegalArgumentException("The transactions list can't have more than " + MAX_TRANSACTIONS + " elements");
    }
}
