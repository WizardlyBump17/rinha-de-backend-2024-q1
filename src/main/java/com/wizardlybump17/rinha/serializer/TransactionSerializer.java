package com.wizardlybump17.rinha.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wizardlybump17.rinha.account.transaction.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionSerializer extends JsonSerializer<Transaction> {

    public static final @NonNull TransactionSerializer INSTANCE = new TransactionSerializer();

    @Override
    public void serialize(Transaction value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("valor", value.amount());
        gen.writeStringField("tipo", value.type().getDisplay());
        gen.writeStringField("descricao", value.description());
        gen.writeStringField("realizada_em", value.time().toString());
        gen.writeEndObject();
    }

    @Override
    public Class<Transaction> handledType() {
        return Transaction.class;
    }
}
