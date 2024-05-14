package report;

import io.cucumber.java.Scenario;

import java.util.HashMap;

public class Log {

    private static Scenario currentScenario;

    public static void setScenario(Scenario scenario) {
        currentScenario = scenario;
    }

    public static void log(String msg) {
        currentScenario.log(msg);
    }
}
