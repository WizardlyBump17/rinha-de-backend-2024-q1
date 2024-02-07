package com.wizardlybump17.rinha.account.transaction;

import lombok.NonNull;

public record TransactionRequest(int amount, @NonNull TransactionType type, @NonNull String description) {
}
