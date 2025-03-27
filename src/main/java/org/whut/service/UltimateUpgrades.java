package org.whut.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.whut.model.UpgradeEntry;

import java.util.ArrayList;
import java.util.List;

public class UltimateUpgrades implements GameSaveComponent {
    private static final String[] ENTRY_NAMES = {
            "泯灭一击",    // 0
            "泰坦之躯",    // 1
            "力大砖飞",    // 2
            "快速填装",    // 3
            "凛风刺骨",    // 4
            "三尺之寒",    // 5
            "事半功倍",    // 6
            "窝红温了",    // 7
            "流星雨",     // 8
            "众星之力",    // 9
            "万籁俱寂",    // 10
            "以爆制爆",    // 11
            "见者有份",    // 12
            "瓜熟蒂落",    // 13
            "中心爆炸",    // 14
            "兵贵神速",    // 15
            "冰淬火炼",    // 16
            "两肋插刀",    // 17
            "普度众生",    // 18
            "永动机",     // 19
            "阻冲之",     // 20
            "狂战士",     // 21
            "金光闪闪",    // 22
            "人造太阳",    // 23
            "三位一体",    // 24
            "镭射激光"     // 25
    };


    private final List<UpgradeEntry> entries = new ArrayList<>();

    @Override
    public void loadFromJson(JSONObject json) {
        JSONArray array = json.getJSONArray("ultimateUpgrades");
        for (int i = 0; i < array.length(); i++) {
            String name = i < ENTRY_NAMES.length ? ENTRY_NAMES[i] : "强究极词条" + i;
            entries.add(new UpgradeEntry(i, name, array.getBoolean(i)));
        }
    }

    @Override
    public void saveToJson(JSONObject json) {
        JSONArray array = new JSONArray();
        entries.forEach(entry -> array.put(entry.isEnabled()));
        json.put("advancedUpgrades", array);
    }

    public List<UpgradeEntry> getEntries() {
        return entries;
    }

    public void updateEntry(int id, boolean enabled) {
        if (id >= 0 && id < entries.size()) {
            entries.get(id).setEnabled(enabled);
        }
    }
}
