package com.wizardlybump17.rinha.account.transaction;

import lombok.NonNull;

import java.util.Date;

public record Transaction(int amount, @NonNull TransactionType type, @NonNull String description, @NonNull Date date) {

    public static final int MIN_DESCRIPTION_LENGTH = 1;
    public static final int MAX_DESCRIPTION_LENGTH = 10;

    public Transaction {
        int descriptionLength = description.length();
        if (descriptionLength < MIN_DESCRIPTION_LENGTH || descriptionLength > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("The description length must be between " + MIN_DESCRIPTION_LENGTH + " and " + MAX_DESCRIPTION_LENGTH);
    }
}
