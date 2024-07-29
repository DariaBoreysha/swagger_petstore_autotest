package steps;

import io.cucumber.java.en.And;
import org.apache.http.HttpResponse;
import stephelper.Memory;

public class UpdatePetSteps extends BaseSteps {

    @And("отправляем PUT запрос c телом из Memory: {string} на {string} и сохраняем ответ в Memory как {string}")
    public void sendPutRequest(
            String memoryRequestKey,
            String url,
            String memoryResponseKey
    ) {
        String requestBody = Memory.asString(memoryRequestKey);
        HttpResponse response = httpClient.methodPut().sendRequest(url, requestBody);
        Memory.put(memoryResponseKey, response);
    }
}
