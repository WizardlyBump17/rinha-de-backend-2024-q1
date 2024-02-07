package com.wizardlybump17.rinha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wizardlybump17.rinha.account.Account;
import com.wizardlybump17.rinha.account.statement.AccountStatement;
import com.wizardlybump17.rinha.account.transaction.Transaction;
import com.wizardlybump17.rinha.account.transaction.TransactionRequest;
import com.wizardlybump17.rinha.account.transaction.TransactionResult;
import com.wizardlybump17.rinha.deserializer.TransactionRequestDeserializer;
import com.wizardlybump17.rinha.serializer.AccountStatementSerializer;
import com.wizardlybump17.rinha.serializer.TransactionResultSerializer;
import com.wizardlybump17.rinha.serializer.TransactionSerializer;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class RinhaDeBackendApplication {

	private final @NonNull Map<Long, Account> accounts = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(RinhaDeBackendApplication.class, args);
	}

	@Bean
	public ObjectMapper registerSerializers() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("RinhaDeBackendApplication");
		module
				.addSerializer(TransactionResult.class, TransactionResultSerializer.INSTANCE)
				.addSerializer(AccountStatement.class, AccountStatementSerializer.INSTANCE)
				.addSerializer(Transaction.class, TransactionSerializer.INSTANCE)
				.addDeserializer(TransactionRequest.class, TransactionRequestDeserializer.INSTANCE);
		mapper.registerModule(module);
		return mapper;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void createDefaultAccounts() {
		AtomicLong counter = Account.COUNTER;

		Account value = new Account(counter.get(), 0, 100000, new ArrayList<>());
		value.deposit(10000, "a");
		value.withdraw(10000, "a");
		accounts.put(counter.getAndIncrement(), value);

		accounts.put(counter.get(), new Account(counter.getAndIncrement(), 0, 80000, new ArrayList<>()));
		accounts.put(counter.get(), new Account(counter.getAndIncrement(), 0, 1000000, new ArrayList<>()));
		accounts.put(counter.get(), new Account(counter.getAndIncrement(), 0, 10000000, new ArrayList<>()));
		accounts.put(counter.get(), new Account(counter.getAndIncrement(), 0, 500000, new ArrayList<>()));
	}

	@PostMapping("/clientes/{id}/transacoes")
	public ResponseEntity<TransactionResult> createTransaction(@PathVariable long id, TransactionRequest request) {
		Account account = accounts.get(id);
		TransactionResult result = account == null ? new TransactionResult(0, 0, TransactionResult.ResultType.INVALID_AMOUNT, new NullPointerException("Invalid account")) : account.processTransaction(request);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.resultType().getHttpStatus()));
	}

	@GetMapping("/clientes/{id}/extrato")
	public ResponseEntity<AccountStatement> getStatement(@PathVariable long id) {
		Account account = accounts.get(id);
		return account == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(account.createStatement(), HttpStatus.OK);
	}
}
