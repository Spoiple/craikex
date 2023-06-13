package org.example;

public class PrimitiveComponents {
    primitive public static class PositionPrimitive {
        public float x, y;
        public PositionPrimitive(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    primitive public static class ColorPrimitive {
        public float r, g, b;
        public ColorPrimitive(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
    primitive public static class VelocityPrimitive {
        public float x, y;
        public VelocityPrimitive(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    primitive public static class SizePrimitive {
        public float w, h;
        public SizePrimitive(float w, float h) {
            this.w = w;
            this.h = h;
        }
    }
    primitive public static class CollisionPrimitive {
        public boolean collided;
        public CollisionPrimitive(boolean collided) {
            this.collided = collided;
        }
    }

}
