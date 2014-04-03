package org.kratos.framework.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by FakeYou on 3/29/14.
 */
public class Settings {
    private JsonObject root;

    public Settings() {
    }

    public void loadSettingsFile(String path) {
        loadSettingsFile(path, StandardCharsets.UTF_8);
    }

    public void loadSettingsFile(String path, Charset encoding) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);

            System.out.println(inputStream);

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, encoding.toString());
            String fileContent = writer.toString();

            JsonParser parser = new JsonParser();

            root = parser.parse(fileContent).getAsJsonObject();
        } catch (IOException e) {
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
