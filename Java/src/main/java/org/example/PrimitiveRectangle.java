package org.example;

primitive public class PrimitiveRectangle {
    public float xPos, yPos;
    public float width, height;
    public float r, g, b;
    public float xVel, yVel;
    public boolean collided;

    public PrimitiveRectangle(float xPos, float yPos, float width, float height, float r, float g, float b, float xVel, float yVel, boolean collided) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.r = r;
        this.g = g;
        this.b = b;
        this.xVel = xVel;
        this.yVel = yVel;
        this.collided = collided;
    }

    public final PrimitiveRectangle updatePosition(float deltaTime) {
        return new PrimitiveRectangle(xPos + xVel * deltaTime, yPos + yVel * deltaTime, width, height, r, g, b, xVel, yVel, collided);
    }

    public final PrimitiveRectangle checkBoundingBox(int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        float xPos = this.xPos, xVel = this.xVel, yPos = this.yPos, yVel = this.yVel;
        boolean collided = false;
        if (xPos < 0) {
            xPos = 0;
            xVel = -this.xVel;
            collided = true;
        } else if (xPos + width > WINDOW_WIDTH) {
            xPos = WINDOW_WIDTH - width;
            xVel = -this.xVel;
            collided = true;
        }
        if (yPos < 0) {
            yPos = 0;
            yVel = -this.yVel;
            collided = true;
        } else if (yPos + height > WINDOW_HEIGHT) {
            yPos = WINDOW_HEIGHT - height;
            yVel = -this.yVel;
            collided = true;
        }
        if (collided)
            return new PrimitiveRectangle(xPos, yPos, width, height, r, g, b, xVel, yVel, collided);
        return this;
    }

    public final PrimitiveRectangle handleCollision() {
        if (collided) {
            return new PrimitiveRectangle(xPos, yPos, width, height, (float) Math.random(), (float) Math.random(), (float) Math.random(), xVel, yVel, false);
        }
        return this;
    }
}
