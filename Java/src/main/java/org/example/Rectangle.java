package org.example;

public class Rectangle {
    public float xPos, yPos;
    public float width, height;
    public float r, g, b;
    private float xVel, yVel;
    private boolean collided;

    public Rectangle(float xPos, float yPos, float width, float height, float r, float g, float b, float xVel, float yVel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.r = r;
        this.b = b;
        this.g = g;
        this.xVel = xVel;
        this.yVel = yVel;
        collided = false;
    }

    public void updatePosition(float deltaTime) {
        xPos += xVel * deltaTime;
        yPos += yVel * deltaTime;
    }

    public void checkBoundingBox(int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        if (xPos < 0) {
            xPos = 0;
            xVel = -xVel;
            collided = true;
        } else if (xPos + width > WINDOW_WIDTH) {
            xPos = WINDOW_WIDTH - width;
            xVel = -xVel;
            collided = true;
        }
        if (yPos < 0) {
            yPos = 0;
            yVel = -yVel;
            collided = true;
        } else if (yPos + height > WINDOW_HEIGHT) {
            yPos = WINDOW_HEIGHT - height;
            yVel = -yVel;
            collided = true;
        }
    }

    public void handleCollision() {
        if (collided) {
            r = (float) Math.random();
            g = (float) Math.random();
            b = (float) Math.random();
            collided = false;
        }
    }
}
