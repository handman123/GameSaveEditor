package org.whut.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.whut.model.UpgradeEntry;

import java.util.ArrayList;
import java.util.List;

public class AdvancedUpgrades implements GameSaveComponent {

    private static final String[] ENTRY_NAMES = {
            "撒豆成兵", "精兵强将", "枕戈待旦", /* 其他词条名称... */
    };

    private final List<UpgradeEntry> entries = new ArrayList<>();

    @Override
    public void loadFromJson(JSONObject json) {
        JSONArray array = json.getJSONArray("advancedUpgrades");
        for (int i = 0; i < array.length(); i++) {
            String name = i < ENTRY_NAMES.length ? ENTRY_NAMES[i] : "弱究极词条" + i;
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
