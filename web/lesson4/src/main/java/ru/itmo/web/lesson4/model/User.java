package ru.itmo.web.lesson4.model;

public class User {

    private final long id;
    private final String handle;
    private final Color color;
    private final String name;

    public User(long id, String handle, Color color, String name) {
        this.id = id;
        this.handle = handle;
        this.color = color;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public enum Color {
        BLACK, RED, GREEN, BLUE;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
