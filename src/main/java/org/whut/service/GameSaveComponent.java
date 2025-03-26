package org.whut.service;

import org.json.JSONObject;

public interface GameSaveComponent {
    void loadFromJson(JSONObject json);
    void saveToJson(JSONObject json);
}
