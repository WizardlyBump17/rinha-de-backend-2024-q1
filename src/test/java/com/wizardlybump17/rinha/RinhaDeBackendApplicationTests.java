package com.wizardlybump17.rinha;

import com.wizardlybump17.rinha.account.Account;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RinhaDeBackendApplicationTests {

	private static final @NonNull Map<Long, Account> ACCOUNTS = new HashMap<>();

	@BeforeAll
	static void createAccounts() {
		AtomicLong counter = Account.COUNTER;

		ACCOUNTS.put(counter.get(), new Account(counter.getAndIncrement(), 0, 100000, new ArrayList<>()));
		ACCOUNTS.put(counter.get(), new Account(counter.getAndIncrement(), 0, 80000, new ArrayList<>()));
		ACCOUNTS.put(counter.get(), new Account(counter.getAndIncrement(), 0, 1000000, new ArrayList<>()));
		ACCOUNTS.put(counter.get(), new Account(counter.getAndIncrement(), 0, 10000000, new ArrayList<>()));
		ACCOUNTS.put(counter.get(), new Account(counter.getAndIncrement(), 0, 500000, new ArrayList<>()));
	}

	@Test
	void withdraw80000from2ndAccount() {
		Account account = ACCOUNTS.get(2L);
		account.withdraw(80000, "a");
        assertEquals(-80000, account.getBalance());
	}
}
