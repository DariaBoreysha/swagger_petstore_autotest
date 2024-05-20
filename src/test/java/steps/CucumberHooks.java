package steps;

import client.HttpClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.assertj.core.api.SoftAssertions;
import report.Log;
import stephelper.Memory;

public class CucumberHooks {

    @Before
    public void before(Scenario scenario) {
        Memory.getInstance();
        Log.setScenario(scenario);
        BaseSteps.httpClient = new HttpClient();
        BaseSteps.softly = new SoftAssertions();
    }

    @After
    public void after() {
        Memory.clear();
    }
}
