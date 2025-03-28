package org.whut.service;

import org.json.JSONObject;
import org.whut.service.AdvancedUpgrades;
import org.whut.service.GameSaveComponent;
import org.whut.service.PlantUnlocks;
import org.whut.service.UltimateUpgrades;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        for (GameSaveComponent component : components) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
        }
        throw new NoSuchElementException(); // 或者自定义异常
    }

    public JSONObject getRawData() {
        return rawData;
    }
}
