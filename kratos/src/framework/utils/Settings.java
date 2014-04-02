package framework.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            String fileContent = encoding.decode(ByteBuffer.wrap(encoded)).toString();

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
