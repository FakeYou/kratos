package framework;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import framework.communication.Communication;
import framework.communication.Interpreter;
import framework.utils.Settings;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Kratos {
    private Settings settings;

    private Communication communication;
    private Interpreter interpreter;

    public Kratos() {
        settings = new Settings();
        settings.loadSettingsFile("settings.json");

        communication = new Communication(this);
        communication.connect();

        interpreter = new Interpreter(communication);
    }

    public JsonElement getSetting(String... keys) {
        return settings.get(keys);
    }

    public Communication getCommunication() {
        return communication;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public static void main(String[] args) {
        new Kratos();
    }
}
