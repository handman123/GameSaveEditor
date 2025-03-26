package org.whut.service;

import org.json.JSONObject;
import org.whut.service.AdvancedUpgrades;
import org.whut.service.GameSaveComponent;
import org.whut.service.PlantUnlocks;
import org.whut.service.UltimateUpgrades;

import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {

    private final List<GameSaveComponent> components = new ArrayList<>();
    private final JSONObject rawData;

    public GameSaveManager(JSONObject json) {
        this.rawData = json;
        registerComponents();
        loadAll();
    }

    private void registerComponents() {
        components.add(new AdvancedUpgrades());
        components.add(new UltimateUpgrades());
        components.add(new PlantUnlocks());
    }

    private void loadAll() {
        components.forEach(c -> c.loadFromJson(rawData));
    }

    public void saveAll() {
        components.forEach(c -> c.saveToJson(rawData));
    }

    public <T extends GameSaveComponent> T getComponent(Class<T> type) {
        return type.cast(components.stream()
                .filter(type::isInstance)
                .findFirst()
                .orElseThrow());
    }

    public JSONObject getRawData() {
        return rawData;
    }
}
