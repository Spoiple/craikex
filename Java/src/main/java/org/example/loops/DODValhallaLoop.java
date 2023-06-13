package org.example.loops;

import org.example.PrimitiveComponents;
import org.example.utils.Renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DODValhallaLoop {

    PrimitiveComponents.PositionPrimitive[] positions;
    PrimitiveComponents.ColorPrimitive[] colors;
    PrimitiveComponents.VelocityPrimitive[] velocities;
    PrimitiveComponents.CollisionPrimitive[] collisions;
    PrimitiveComponents.SizePrimitive[] sizes;

    protected final Renderer renderer;
    protected final int NUMBER_OF_RECTANGLES;
    protected final int WINDOW_WIDTH, WINDOW_HEIGHT;
    protected final float RECTANGLE_WIDTH, RECTANGLE_HEIGHT;
    protected long lastFrameTime;

    public final void update() {
        long current = System.currentTimeMillis();
        float delta = current - lastFrameTime;
        lastFrameTime = current;
        updateLogic(delta / 1000.f);
    }
    public DODValhallaLoop(Renderer renderer, int NUMBER_OF_RECTANGLES, int WINDOW_WIDTH, int WINDOW_HEIGHT, float RECTANGLE_WIDTH, float RECTANGLE_HEIGHT) {
        this.renderer = renderer;
        this.NUMBER_OF_RECTANGLES = NUMBER_OF_RECTANGLES;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.RECTANGLE_WIDTH = RECTANGLE_WIDTH;
        this.RECTANGLE_HEIGHT = RECTANGLE_HEIGHT;
        lastFrameTime = System.currentTimeMillis();
        positions = new PrimitiveComponents.PositionPrimitive[NUMBER_OF_RECTANGLES];
        colors = new PrimitiveComponents.ColorPrimitive[NUMBER_OF_RECTANGLES];
        velocities = new PrimitiveComponents.VelocityPrimitive[NUMBER_OF_RECTANGLES];
        collisions = new PrimitiveComponents.CollisionPrimitive[NUMBER_OF_RECTANGLES];
        sizes = new PrimitiveComponents.SizePrimitive[NUMBER_OF_RECTANGLES];
        generatePrimitiveRectangles(NUMBER_OF_RECTANGLES);
    }


    public void updateLogic(float dt) {
        movementSystem(positions, velocities, dt);
        collisionSystem(positions, velocities, collisions);
        colorSystem(colors, collisions);
    }

    private void colorSystem(PrimitiveComponents.ColorPrimitive[] colors, PrimitiveComponents.CollisionPrimitive[] collisions) {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            if (collisions[i].collided) {
                collisions[i] = new PrimitiveComponents.CollisionPrimitive(false);
                colors[i] = new PrimitiveComponents.ColorPrimitive((float) Math.random(), (float) Math.random(), (float) Math.random());
            }
        }
    }

    private void movementSystem(PrimitiveComponents.PositionPrimitive[] positions, PrimitiveComponents.VelocityPrimitive[] velocities, float dt) {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            positions[i] = new PrimitiveComponents.PositionPrimitive(positions[i].x + velocities[i].x * dt, positions[i].y + velocities[i].y * dt);
        }
    }

    private void collisionSystem(PrimitiveComponents.PositionPrimitive[] positions, PrimitiveComponents.VelocityPrimitive[] velocities, PrimitiveComponents.CollisionPrimitive[] collisions) {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            boolean collision = false;
            float xPos = positions[i].x, yPos = positions[i].y;
            float xVel = velocities[i].x, yVel = velocities[i].y;
            if (positions[i].x < 0) {
                xVel = -velocities[i].x;
                xPos = 0;
                collision = true;
            } else if (positions[i].x + sizes[i].w > WINDOW_WIDTH) {
                xVel = -velocities[i].x;
                xPos = WINDOW_WIDTH - sizes[i].w;
                collision = true;
            }
            if (positions[i].y < 0) {
                yVel = -velocities[i].y;
                yPos = 0;
                collision = true;
            } else if (positions[i].y + sizes[i].h > WINDOW_HEIGHT) {
                yVel = -velocities[i].y;
                yPos = WINDOW_HEIGHT - sizes[i].h;
                collision = true;
            }
            if (collision) {
                velocities[i] = new PrimitiveComponents.VelocityPrimitive(xVel, yVel);
                positions[i] = new PrimitiveComponents.PositionPrimitive(xPos, yPos);
                collisions[i] = new PrimitiveComponents.CollisionPrimitive(true);
            }
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        renderer.begin();
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            renderer.drawRect(positions[i].x, positions[i].y, sizes[i].w, sizes[i].h, colors[i].r, colors[i].g, colors[i].b);
        }
        renderer.end();
    }

    private void generatePrimitiveRectangles(int noOfRectangles) {
        for (int i = 0; i < noOfRectangles; i++) {
            float xPos = ((float) Math.random() * (WINDOW_WIDTH - RECTANGLE_WIDTH));
            float yPos = ((float) Math.random() * (WINDOW_HEIGHT - RECTANGLE_HEIGHT));
            float xVel = (((float) Math.random() * 160)) - 80;
            float yVel = (((float) Math.random() * 160)) - 80;
            float r = (float) Math.random();
            float g = (float) Math.random();
            float b = (float) Math.random();

            positions[i] = new PrimitiveComponents.PositionPrimitive(xPos, yPos);
            colors[i] = new PrimitiveComponents.ColorPrimitive(r, g, b);
            velocities[i] = new PrimitiveComponents.VelocityPrimitive(xVel, yVel);
            collisions[i] = new PrimitiveComponents.CollisionPrimitive(false);
            sizes[i] = new PrimitiveComponents.SizePrimitive(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        }
    }
}
