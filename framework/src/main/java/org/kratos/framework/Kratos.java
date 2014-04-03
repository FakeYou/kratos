package org.kratos.framework;

import com.google.gson.JsonElement;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.Interpreter;
import org.kratos.framework.utils.Settings;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Kratos {
    private Settings settings;

    private Communication communication;
    private Interpreter interpreter;

    public Kratos() {
        settings = new Settings();
        settings.loadSettingsFile("./framework/src/main/resources/settings.json");
    }

    public void connect() {
        String host = getSetting("host").getAsString();
        int port = getSetting("port").getAsInt();

        connect(host, port);
    }

    public void connect(String host, int port) {
        communication = new Communication(this);
        communication.connect(host, port);

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
