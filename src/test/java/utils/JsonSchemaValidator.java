package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import exceptions.FileUtilException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static constants.Constants.SCHEMA_FOLDER;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSchemaValidator {

    public void isJsonValid(String jsonBody, String jsonSchemaFileName) {
        JsonSchema jsonSchema = createJsonSchema(jsonSchemaFileName);
        JsonNode jsonNode = convertStringToJsonNode(jsonBody);
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
            throw new FileUtilException(e);
        }
        return schema;
    }

    private JsonNode convertStringToJsonNode(String jsonBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = objectMapper.readTree(jsonBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private String getMessagesOnFailedValidation(Set<ValidationMessage> validationResult) {
        StringBuilder fullErrorMessage = new StringBuilder(System.lineSeparator());
        for (ValidationMessage vm : validationResult) {
            fullErrorMessage.append(vm.getMessage()).append(System.lineSeparator());
        }
        return fullErrorMessage.toString();
    }
}