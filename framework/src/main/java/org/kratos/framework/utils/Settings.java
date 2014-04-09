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
        try {
            File file = new File(path);
            String fileContent = FileUtils.readFileToString(file);

            JsonParser parser = new JsonParser();

            root = parser.parse(fileContent).getAsJsonObject();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
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
