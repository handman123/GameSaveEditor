package org.whut.model;

public class PlantUnlock {
    private final int id;
    private final String name;
    private boolean unlocked;

    public PlantUnlock(int id, String name, boolean unlocked) {
        this.id = id;
        this.name = name;
        this.unlocked = unlocked;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isUnlocked() { return unlocked; }

    // Setter
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public String toString() {
        return String.format("%2d. %-8s: %s",
                id,
                name,
                unlocked ? "✓ 已解锁" : "✗ 未解锁");
    }
}
