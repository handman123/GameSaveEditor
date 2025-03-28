package org.whut.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static JSONObject loadJson(String path) throws Exception {
        java.nio.file.Path filePath = Paths.get(path).toAbsolutePath();
        if (!Files.exists(filePath)) {
            throw new Exception("文件不存在: " + filePath);
        }
        byte[] bytes = Files.readAllBytes(filePath);
        return new JSONObject(new String(bytes, StandardCharsets.UTF_8));
    }

    public static void saveJson(JSONObject json) throws IOException {
        Files.write(Paths.get("level16.json"),
                json.toString().getBytes(StandardCharsets.UTF_8));
    }
}
