package org.whut.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.whut.model.PlantUnlock;

import java.util.ArrayList;
import java.util.List;

public class PlantUnlocks implements GameSaveComponent {
    // 植物名称对照表（按您提供的顺序）
    private static final String[] PLANT_NAMES = {
            "究极樱桃战神",    // 0
            "究极樱桃机枪",    // 1
            "究极大喷菇",      // 2
            "究极莴炬",        // 3
            "究极杨桃",        // 4
            "究极忧郁菇",      // 5
            "究极投手",        // 6
            "究极加农炮",      // 7
            "究极魅惑菇",      // 8
            "究极炮台",        // 9
            "究极卷心菜",      // 10
            "究极南瓜"         // 11
    };

    private final List<PlantUnlock> unlocks = new ArrayList<>();

    @Override
    public void loadFromJson(JSONObject json) {
        unlocks.clear(); // 清空现有数据
        JSONArray array = json.getJSONArray("unlockPlant");

        // 遍历JSON数组并创建植物解锁对象
        for (int i = 0; i < array.length(); i++) {
            String name = (i < PLANT_NAMES.length) ? PLANT_NAMES[i] : "未知植物-" + i;
            boolean unlocked = array.getBoolean(i);
            unlocks.add(new PlantUnlock(i, name, unlocked));
        }
    }

    @Override
    public void saveToJson(JSONObject json) {
        JSONArray array = new JSONArray();
        // 将解锁状态写入JSON数组
        unlocks.forEach(plant -> array.put(plant.isUnlocked()));
        json.put("unlockPlant", array);
    }

    /**
     * 获取所有植物解锁状态
     */
    public List<PlantUnlock> getAllUnlocks() {
        return new ArrayList<>(unlocks); // 返回副本保护数据
    }

    /**
     * 更新植物解锁状态
     * @param plantId 植物ID (0-11)
     * @param unlocked 是否解锁
     * @throws IllegalArgumentException 如果plantId无效
     */
    public void updateUnlockStatus(int plantId, boolean unlocked) {
        if (plantId < 0 || plantId >= unlocks.size()) {
            throw new IllegalArgumentException("无效的植物ID: " + plantId);
        }
        unlocks.get(plantId).setUnlocked(unlocked);
    }

    /**
     * 获取特定植物的解锁状态
     */
    public boolean isPlantUnlocked(int plantId) {
        return unlocks.get(plantId).isUnlocked();
    }

    /**
     * 获取植物名称
     */
    public String getPlantName(int plantId) {
        return unlocks.get(plantId).getName();
    }
}
