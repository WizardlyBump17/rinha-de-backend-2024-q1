package com.wizardlybump17.rinha.account;

import com.wizardlybump17.rinha.account.statement.AccountStatement;
import com.wizardlybump17.rinha.account.transaction.Transaction;
import com.wizardlybump17.rinha.account.transaction.TransactionType;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

@Data
public class Account {

    private final long id;
    private int balance;
    private int limit;
    private final @NonNull List<Transaction> transactions;

    public Account(long id, int balance, int limit, @NonNull List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.limit = limit;
        this.transactions = transactions;
    }

    public @NonNull AccountStatement createStatement() {
        List<Transaction> transactions = this.transactions.subList(0, Math.min(this.transactions.size(), AccountStatement.MAX_TRANSACTIONS));
        return new AccountStatement(balance, Instant.now(), limit, transactions);
    }

    public void withdraw(int amount, @NonNull String description) {
        if (amount < 1)
            throw new IllegalArgumentException("The amount must be greater than 1");

        if (balance - amount < -limit)
            throw new IllegalArgumentException("The balance after the withdrawal can't be less than the limit");

        transactions.add(new Transaction(-amount, TransactionType.WITHDRAW, description, Instant.now()));
        balance -= amount;
    }

    public void deposit(int amount, @NonNull String description) {
        if (amount < 1)
            throw new IllegalArgumentException("The amount must be greater than 1");

        transactions.add(new Transaction(amount, TransactionType.DEPOSIT, description, Instant.now()));
        balance += amount;
    }
}
