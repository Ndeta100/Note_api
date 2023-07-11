package io.ndeta.springwithnextjs.enumeration;

public enum Level {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    private final  int level;
}
