package org.whut.utils;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static JSONObject loadJson(String path) throws Exception {
        Path filePath = Path.of(path).toAbsolutePath();
        if (!Files.exists(filePath)) {
            throw new Exception("文件不存在: " + filePath);
        }
        return new JSONObject(Files.readString(filePath));
    }

    public static void saveJson(JSONObject json) throws Exception {
        Files.writeString(Path.of("level16.json"), json.toString());
    }
}
