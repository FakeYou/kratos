package org.kratos.framework.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Settings {
    private JsonObject root;

    public Settings() {
    }

    public void loadSettingsFile(String path) {
        JsonParser parser = new JsonParser();

        try {
            File file = new File(path);
            String fileContent = FileUtils.readFileToString(file);

            root = parser.parse(fileContent).getAsJsonObject();
        }
        catch (Exception e) {
            root = parser.parse("{ host: \"localhost\", port: 7789 }").getAsJsonObject();
        }
    }

    public JsonElement get(String ... keys) {
        JsonObject object = root.getAsJsonObject();

        for(int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];

            if(!object.has(key)) {
                return null;
            }

            object = object.get(key).getAsJsonObject();
        }

        return object.get(keys[keys.length - 1]);
    }
}
