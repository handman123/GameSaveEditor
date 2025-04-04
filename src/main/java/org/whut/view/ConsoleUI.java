package org.whut.view;

import org.fusesource.jansi.Ansi;
import org.whut.model.PlantUnlock;
import org.whut.model.UpgradeEntry;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private static final String DIVIDER = "====================================";

    // ================= 基础交互方法 =================

    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " > ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                showError("请输入有效数字！");
            }
        }
    }

    public boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (T/F) > ");
            String input = scanner.nextLine();
            if (input.equals("T")) return true;
            if (input.equals("F")) return false;
            showError("请输入 T 或 F");
        }
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt + " > ");
        return scanner.nextLine();
    }

    // ================= 显示方法 =================

    public void showWelcomeMessage() {
        clearScreen();
        System.out.println(DIVIDER);
        System.out.println("     植物大战僵尸融合版本 存档编辑器");
        System.out.println("            版本: 1.0");
        System.out.println(DIVIDER);
        pause(1000);
    }

    public void showMainMenu() {
        clearScreen();
        System.out.println(DIVIDER);
        System.out.println("           主菜单");
        System.out.println(DIVIDER);
        System.out.println("1. 查看当前所有状态");
        System.out.println("2. 修改弱究极植物词条");
        System.out.println("3. 修改强究极植物词条");
        System.out.println("4. 修改植物解锁状态");
        System.out.println("5. 保存修改");
        System.out.println("6. 退出程序");
        System.out.println("7. 显示帮助");
        System.out.println(DIVIDER);
    }

    public void displayEntries(List<UpgradeEntry> entries) {
        // 打印表头
        System.out.println(Ansi.ansi().bold().a("\nID | 词条名称\t\t\t | 状态").reset());
        System.out.println(dashes(36));

        // 打印内容
        entries.forEach(entry -> {
            String paddedName = padToFixedWidth(entry.getName());
            String status = entry.isEnabled()
                    ? Ansi.ansi().fgGreen().a("已解锁").reset().toString()
                    : Ansi.ansi().fgRed().a("未解锁").reset().toString();

            System.out.printf("%2d | %s\t\t\t | %s%n",
                    entry.getId(),
                    paddedName,
                    status);
        });
    }

    public void displayPlantUnlocks(List<PlantUnlock> unlocks) {
        // 打印表头
        System.out.println("\nID | 植物名称\t\t\t | 解锁状态");
        System.out.println(dashes(36));

        // 打印内容
        unlocks.forEach(plant -> {
            String status = plant.isUnlocked() ?
                    Ansi.ansi().fgGreen().a("已解锁").reset().toString() :
                    Ansi.ansi().fgRed().a("未解锁").reset().toString();
            System.out.printf("%2d | %s\t\t\t | %s%n",
                    plant.getId(),
                    plant.getName(),
                    status);
        });
    }

    public void showSectionTitle(String title) {
        System.out.println("\n" + DIVIDER);
        System.out.println("     " + title);
        System.out.println(DIVIDER);
    }

    // ================= 功能提示方法 =================

    public void showSuccess(String message) {
        System.out.println("\n[✓] " + message);
        pause(800);
    }

    public void showError(String message) {
        System.out.println("\n[!] 错误: " + message);
        pause(1000);
    }

    public void showMessage(String message) {
        System.out.println("\n[i] " + message);
    }

    public void showHelp() {
        clearScreen();
        System.out.println(DIVIDER);
        System.out.println("           帮助文档");
        System.out.println(DIVIDER);
        System.out.println("1. 词条修改说明:");
        System.out.println("   - true表示启用该词条效果");
        System.out.println("   - false表示禁用该词条效果");
        System.out.println("\n2. 植物ID对照表:");
        System.out.println("   0: 究极樱桃战神");
        System.out.println("   1: 究极樱桃机枪");
        System.out.println("   2: 究极大喷菇");
        System.out.println("   ...(其他植物)");
        System.out.println(DIVIDER);
        pressAnyKeyToContinue();
    }

    // ================= 工具方法 =================

    public void pressAnyKeyToContinue() {
        System.out.print("\n按任意键继续...");
        scanner.nextLine();
    }

    public void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // 如果清屏失败，打印50个空行作为替代
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void showExitMessage() {
        clearScreen();
        System.out.println(DIVIDER);
        System.out.println("     感谢使用存档编辑器");
        System.out.println("     再见！");
        System.out.println(DIVIDER);
        pause(1500);
    }

    // 计算字符串的显示宽度（中文算2，英文算1）
    private int calculateDisplayWidth(String str) {
        return str.chars()
                .map(c -> c > 127 ? 1 : 1)
                .sum();
    }

    // 将字符串填充到固定显示宽度
    private String padToFixedWidth(String str) {
        int targetWidth = 6;
        int currentWidth = calculateDisplayWidth(str);
        if (currentWidth >= targetWidth) {
            return str;
        }
        return str + spaces(targetWidth - currentWidth);
    }

    // 生成指定数量的空格 (替代String.repeat)
    private String spaces(int count) {
        return new String(new char[count]).replace('\0', ' ');
    }

    // 生成指定数量的横线
    private String dashes(int count) {
        return new String(new char[count]).replace('\0', '-');
    }
}
