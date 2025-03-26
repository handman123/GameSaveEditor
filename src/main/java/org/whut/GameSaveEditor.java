package org.whut;

import org.json.JSONObject;
import org.whut.controller.EditorController;
import org.whut.utils.FileUtils;

import java.nio.file.Paths;


public class GameSaveEditor {
    public static void main(String[] args) {
        try {
            JSONObject json = FileUtils.loadJson("level16.json");
            new EditorController(json).run();
        } catch (Exception e) {
            System.err.println("程序启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

