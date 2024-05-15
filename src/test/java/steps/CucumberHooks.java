package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import report.Log;
import stephelper.Memory;

public class CucumberHooks {

    @Before
    public void before(Scenario scenario) {
        Memory.getInstance();
        Log.setScenario(scenario);
    }

    @After
    public void after() {
        Memory.clear();
    }
}
