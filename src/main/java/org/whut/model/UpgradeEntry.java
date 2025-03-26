package org.whut.model;

public class UpgradeEntry {
    private final int id;
    private final String name;
    private boolean enabled;

    public UpgradeEntry(int id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return String.format("%2d. %s: %s", id, name, enabled ? "已启用" : "未启用");
    }
}
