package steps;

import com.fasterxml.jackson.databind.JsonNode;
import constants.Constants;
import io.cucumber.java.en.And;
import stephelper.Memory;
import utils.FileUtil;
import utils.JsonNodeUtil;

public class MakeOrderForPetSteps {

    @And("конвертируем ожидаемый ответ сервиса {string} в jsonNode и сохраняем в Memory как {string}")
    public void convertExpectedAppAnswerToString(String expectedResFileName, String memoryKeyName) {
        String body = FileUtil.read(Constants.SAMPLES_FOLDER + expectedResFileName);
        JsonNode jsonNode = JsonNodeUtil.convertStringToJsonNode(body);
        Memory.put(memoryKeyName, jsonNode);
    }
}
