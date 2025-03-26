package org.whut.controller;

import org.json.JSONObject;
import org.whut.model.PlantUnlock;
import org.whut.model.UpgradeEntry;
import org.whut.service.AdvancedUpgrades;
import org.whut.service.GameSaveManager;
import org.whut.service.PlantUnlocks;
import org.whut.service.UltimateUpgrades;
import org.whut.utils.FileUtils;
import org.whut.view.ConsoleUI;

import java.util.List;

public class EditorController {
    private final GameSaveManager saveManager;
    private final ConsoleUI ui;
    private boolean unsavedChanges = false;

    public EditorController(JSONObject json) {
        this.saveManager = new GameSaveManager(json);
        this.ui = new ConsoleUI();
    }

    /**
     * 主运行循环
     */
    public void run() {
        ui.showWelcomeMessage();

        mainLoop:
        while (true) {
            try {
                ui.showMainMenu();
                int choice = ui.getIntInput("请选择操作");

                switch (choice) {
                    case 1 -> showAllStatus();
                    case 2 -> modifyAdvancedUpgrades();
                    case 3 -> modifyUltimateUpgrades();
                    case 4 -> modifyPlantUnlocks();
                    case 5 -> saveChanges();
                    case 6 -> {
                        if (checkUnsavedChanges()) {
                            break mainLoop;
                        }
                    }
                    case 7 -> showHelp();
                    default -> ui.showError("无效选项，请输入1-7");
                }
            } catch (Exception e) {
                handleException(e);
            }
        }

        ui.showExitMessage();
    }

    // === 核心功能方法 ===

    /**
     * 显示所有修改项状态
     */
    private void showAllStatus() {
        // 弱究极词条
        AdvancedUpgrades advanced = saveManager.getComponent(AdvancedUpgrades.class);
        ui.showSectionTitle("弱究极植物词条");
        ui.displayEntries(advanced.getEntries());

        // 强究极词条
        UltimateUpgrades ultimate = saveManager.getComponent(UltimateUpgrades.class);
        ui.showSectionTitle("强究极植物词条");
        ui.displayEntries(ultimate.getEntries());

        // 植物解锁
        PlantUnlocks plants = saveManager.getComponent(PlantUnlocks.class);
        ui.showSectionTitle("植物解锁状态");
        ui.displayPlantUnlocks(plants.getAllUnlocks());

        ui.pressAnyKeyToContinue();
    }

    /**
     * 修改弱究极词条
     */
    private void modifyAdvancedUpgrades() {
        AdvancedUpgrades upgrades = saveManager.getComponent(AdvancedUpgrades.class);
        List<UpgradeEntry> entries = upgrades.getEntries();

        ui.showSectionTitle("修改弱究极词条");
        ui.displayEntries(entries);

        int id = ui.getIntInput("输入要修改的词条ID (0-" + (entries.size()-1) + ")");
        if (id < 0 || id >= entries.size()) {
            ui.showError("无效ID");
            return;
        }

        boolean newState = ui.getBooleanInput("设置新状态 (true=启用/false=禁用)");
        upgrades.updateEntry(id, newState);
        unsavedChanges = true;

        ui.showSuccess("词条 " + entries.get(id).getName() + " 已修改为: " + newState);
    }

    /**
     * 修改强究极词条 (实现与弱究极类似)
     */
    private void modifyUltimateUpgrades() {
        UltimateUpgrades upgrades = saveManager.getComponent(UltimateUpgrades.class);
        // ... 实现与modifyAdvancedUpgrades()类似 ...
    }

    /**
     * 修改植物解锁状态
     */
    private void modifyPlantUnlocks() {
        PlantUnlocks unlocks = saveManager.getComponent(PlantUnlocks.class);
        List<PlantUnlock> plants = unlocks.getAllUnlocks();

        ui.showSectionTitle("修改植物解锁");
        ui.displayPlantUnlocks(plants);

        int plantId = ui.getIntInput("输入植物ID (0-11)");
        if (plantId < 0 || plantId >= plants.size()) {
            ui.showError("无效植物ID");
            return;
        }

        boolean newState = ui.getBooleanInput("设置解锁状态 (true=解锁/false=锁定)");
        unlocks.updateUnlockStatus(plantId, newState);
        unsavedChanges = true;

        ui.showSuccess(plants.get(plantId).getName() + " 已" + (newState ? "解锁" : "锁定"));
    }

    // === 辅助方法 ===

    /**
     * 保存修改到文件
     */
    private void saveChanges() {
        try {
            saveManager.saveAll();
            FileUtils.saveJson(saveManager.getRawData());
            unsavedChanges = false;
            ui.showSuccess("存档已保存！");
        } catch (Exception e) {
            ui.showError("保存失败: " + e.getMessage());
        }
    }

    /**
     * 检查未保存的修改
     */
    private boolean checkUnsavedChanges() {
        if (unsavedChanges) {
            boolean confirm = ui.getBooleanInput("有未保存的修改，确定退出吗？(true=退出/false=取消)");
            if (!confirm) {
                ui.showMessage("取消退出");
                return false;
            }
        }
        return true;
    }

    /**
     * 异常处理
     */
    private void handleException(Exception e) {
        ui.showError("操作失败: " + e.getMessage());
        if (e instanceof ArrayIndexOutOfBoundsException) {
            ui.showError("提示：请输入有效的ID范围");
        }
        ui.pressAnyKeyToContinue();
    }

    /**
     * 显示帮助信息
     */
    private void showHelp() {
        ui.showSectionTitle("帮助信息");
        ui.showMessage("1. 修改词条时请确保输入正确的ID");
        ui.showMessage("2. 植物ID对应关系：");

        PlantUnlocks unlocks = saveManager.getComponent(PlantUnlocks.class);
        unlocks.getAllUnlocks().forEach(plant ->
                ui.showMessage(String.format("%2d: %s", plant.getId(), plant.getName()))
        );

        ui.pressAnyKeyToContinue();
    }
}
