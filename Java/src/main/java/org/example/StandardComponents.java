package org.example;

public class StandardComponents {
    public static class Position {
        public float x, y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Color {
        public float r, g, b;

        public Color(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    public static class Velocity {
        public float x, y;

        public Velocity(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Size {
        public float w, h;

        public Size(float w, float h) {
            this.w = w;
            this.h = h;
        }
    }

    public static class Collision {
        public boolean hasCollided = false;
    }
}
