package org.whut.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.whut.model.UpgradeEntry;

import java.util.ArrayList;
import java.util.List;

public class AdvancedUpgrades implements GameSaveComponent {

    private static final String[] ENTRY_NAMES = {
            "撒豆成兵",      // 0
            "精兵强将",      // 1
            "枕戈待旦",      // 2
            "核能威慑",      // 3
            "妙手回春",      // 4
            "无关痛痒",      // 5
            "尸愁之路",      // 6
            "百炼成钢",      // 7
            "熠熠生辉",      // 8
            "急速战备",      // 9
            "强力打击",      // 10
            "精准打击",      // 11
            "腐化",         // 12
            "全副武装",      // 13
            "合理投资",      // 14
            "怒火攻心",      // 15
            "极致手速",      // 16
            "势如破竹",      // 17
            "冻彻心扉",      // 18
            "斗转星移",      // 19
            "灯火通明",      // 20
            "多多益善",      // 21
            "等价交换",      // 22
            "人工智能",      // 23
            "弹射起步",      // 24
            "聚光盆",       // 25
            "运斤如风",      // 26
            "全息制冷",      // 27
            "致命一击",      // 28
            "拆分",         // 29
            "肉身成圣",      // 30
            "百步穿杨",      // 31
            "复制中心",      // 32
            "连连看",       // 33
            "特制弹药",      // 34
            "一针见血",      // 35
            "真正的樱桃炸弹", // 36
            "真正的毁灭菇",   // 37
            "可控核聚变",    // 39
            "人多势众",      // 40
            "我是传奇",      // 41
            "大富翁",       // 42
            "好运连连",      // 43
            "过载",         // 44
            "排山倒海",      // 45
            "星神合一",      // 46
            "量子护盾",      // 47
            "高能射线"      // 48
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
