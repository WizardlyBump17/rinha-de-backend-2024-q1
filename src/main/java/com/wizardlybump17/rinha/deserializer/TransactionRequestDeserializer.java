package com.wizardlybump17.rinha.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wizardlybump17.rinha.account.transaction.TransactionRequest;
import com.wizardlybump17.rinha.account.transaction.TransactionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionRequestDeserializer extends JsonDeserializer<TransactionRequest> {

    public static final @NonNull TransactionRequestDeserializer INSTANCE = new TransactionRequestDeserializer();

    @Override
    public TransactionRequest deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        System.out.println("a");
        JsonNode node = parser.getCodec().readTree(parser);
        return new TransactionRequest(
                node.get("valor").asInt(),
                TransactionType.fromDisplay(node.get("tipo").asText()),
                node.get("descricao").asText()
        );
    }

    @Override
    public Class<?> handledType() {
        return TransactionRequest.class;
    }
}
