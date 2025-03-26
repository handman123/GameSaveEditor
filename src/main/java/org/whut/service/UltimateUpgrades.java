package org.whut.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.whut.model.UpgradeEntry;

import java.util.ArrayList;
import java.util.List;

public class UltimateUpgrades implements GameSaveComponent {
    private static final String[] ENTRY_NAMES = {
            "泯灭一击", "泰坦之躯", "力大砖飞", /* 其他词条名称... */
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
