package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import stephelper.Memory;

public class CucumberHooks {

    @Before
    public void before() {
        Memory.getInstance();
    }

    @After
    public void after() {
        Memory.clear();
    }
}
