package com.wizardlybump17.rinha.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wizardlybump17.rinha.account.transaction.TransactionResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionResultSerializer extends JsonSerializer<TransactionResult> {

    public static final @NonNull TransactionResultSerializer INSTANCE = new TransactionResultSerializer();

    @Override
    public void serialize(TransactionResult value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("limite", value.limit());
        gen.writeNumberField("saldo", value.balance());

        if (value.error() != null)
            gen.writeStringField("erro", value.error().getMessage());

        gen.writeEndObject();
    }

    @Override
    public Class<TransactionResult> handledType() {
        return TransactionResult.class;
    }
}
