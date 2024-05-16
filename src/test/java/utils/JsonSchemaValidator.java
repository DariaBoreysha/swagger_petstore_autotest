package utils;

import client.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import exceptions.AtJsonSchemaValidatorException;
import org.apache.http.HttpResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static constants.Constants.SCHEMA_FOLDER;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSchemaValidator {

    public void isJsonValid(HttpResponse response, String jsonSchemaFileName) {
        JsonSchema jsonSchema = createJsonSchema(jsonSchemaFileName);
        JsonNode jsonNode = convertHttpResponseToJsonNode(response);
        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);
        if (!validationResult.isEmpty()) {
            fail(getMessagesOnFailedValidation(validationResult));
        }
    }

    private JsonSchema createJsonSchema(String jsonSchemaFileName) {
        JsonSchema schema;
        try (
                InputStream schemaStream = new FileInputStream(SCHEMA_FOLDER + jsonSchemaFileName)
        ) {
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            schema = schemaFactory.getSchema(schemaStream);
        } catch (IOException e) {
            throw new AtJsonSchemaValidatorException(e);
        }
        return schema;
    }

    private JsonNode convertHttpResponseToJsonNode(HttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream entityContent = HttpClient.extractHttpEntityContent(response);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readValue(entityContent, JsonNode.class);
        } catch (IOException e) {
            throw new AtJsonSchemaValidatorException(e);
        }
        return jsonNode;
    }

    private String getMessagesOnFailedValidation(Set<ValidationMessage> validationResult) {
        StringBuilder fullErrorMessage = new StringBuilder(System.lineSeparator());
        for (ValidationMessage vm : validationResult) {
            fullErrorMessage.append(vm.getMessage()).append(System.lineSeparator());
        }
        return fullErrorMessage.toString();
    }
}
