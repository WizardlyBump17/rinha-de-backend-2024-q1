package com.wizardlybump17.rinha.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wizardlybump17.rinha.account.statement.AccountStatement;
import com.wizardlybump17.rinha.account.transaction.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountStatementSerializer extends JsonSerializer<AccountStatement> {

    public static final @NonNull AccountStatementSerializer INSTANCE = new AccountStatementSerializer();

    @Override
    public void serialize(AccountStatement value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeFieldName("saldo");
        gen.writeStartObject();
        gen.writeNumberField("total", value.balance());
        gen.writeStringField("data_extrato", value.requestTime().toString());
        gen.writeNumberField("limite", value.limit());
        gen.writeEndObject();

        gen.writeFieldName("ultimas_transacoes");
        gen.writeStartArray();
        for (Transaction transaction : value.transactions())
            TransactionSerializer.INSTANCE.serialize(transaction, gen, serializers);
        gen.writeEndArray();

        gen.writeEndObject();
    }

    @Override
    public Class<AccountStatement> handledType() {
        return AccountStatement.class;
    }
}
