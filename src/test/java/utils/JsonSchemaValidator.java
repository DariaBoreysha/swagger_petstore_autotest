package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import constants.Constants;
import exceptions.AtJsonSchemaValidatorException;
import org.assertj.core.api.Assertions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JsonSchemaValidator {

    public void validate(JsonNode jsonBody, String jsonSchemaFileName) {
        JsonSchema jsonSchema = createJsonSchema(jsonSchemaFileName);
        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonBody);
        if (!validationResult.isEmpty()) {
            Assertions.fail(getMessagesOnFailedValidation(validationResult));
        }
    }

    private JsonSchema createJsonSchema(String jsonSchemaFileName) {
        JsonSchema schema;
        try (
                InputStream schemaStream = new FileInputStream(Constants.SCHEMA_FOLDER + jsonSchemaFileName)
        ) {
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            schema = schemaFactory.getSchema(schemaStream);
        } catch (IOException e) {
            throw new AtJsonSchemaValidatorException(e);
        }
        return schema;
    }

    private String getMessagesOnFailedValidation(Set<ValidationMessage> validationResult) {
        StringBuilder fullErrorMessage = new StringBuilder(System.lineSeparator());
        for (ValidationMessage vm : validationResult) {
            fullErrorMessage.append(vm.getMessage()).append(System.lineSeparator());
        }
        return fullErrorMessage.toString();
    }
}
