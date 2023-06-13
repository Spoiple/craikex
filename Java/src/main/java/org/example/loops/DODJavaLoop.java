package org.example.loops;

import org.example.StandardComponents;
import org.example.utils.Renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DODJavaLoop {

    protected final Renderer renderer;
    protected final int NUMBER_OF_RECTANGLES;
    protected final int WINDOW_WIDTH, WINDOW_HEIGHT;
    protected final float RECTANGLE_WIDTH, RECTANGLE_HEIGHT;
    protected long lastFrameTime;
    StandardComponents.Position[] positions;
    StandardComponents.Color[] colors;
    StandardComponents.Velocity[] velocities;
    StandardComponents.Collision[] collisions;
    StandardComponents.Size[] sizes;

    public DODJavaLoop(Renderer renderer, int NUMBER_OF_RECTANGLES, int WINDOW_WIDTH, int WINDOW_HEIGHT, float RECTANGLE_WIDTH, float RECTANGLE_HEIGHT) {
        this.renderer = renderer;
        this.NUMBER_OF_RECTANGLES = NUMBER_OF_RECTANGLES;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.RECTANGLE_WIDTH = RECTANGLE_WIDTH;
        this.RECTANGLE_HEIGHT = RECTANGLE_HEIGHT;
        lastFrameTime = System.currentTimeMillis();
        positions = new StandardComponents.Position[NUMBER_OF_RECTANGLES];
        colors = new StandardComponents.Color[NUMBER_OF_RECTANGLES];
        velocities = new StandardComponents.Velocity[NUMBER_OF_RECTANGLES];
        collisions = new StandardComponents.Collision[NUMBER_OF_RECTANGLES];
        sizes = new StandardComponents.Size[NUMBER_OF_RECTANGLES];
        generatePrimitiveRectangles(NUMBER_OF_RECTANGLES);
    }

    public final void update() {
        long current = System.currentTimeMillis();
        float delta = current - lastFrameTime;
        lastFrameTime = current;
        updateLogic(delta / 1000.f);
    }

    public void updateLogic(float dt) {
        movementSystem(dt);
        collisionSystem();
        colorSystem();
    }

    private void colorSystem() {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            if (collisions[i].hasCollided) {
                collisions[i].hasCollided = false;
                colors[i].r = (float) Math.random();
                colors[i].g = (float) Math.random();
                colors[i].b = (float) Math.random();
            }
        }
    }

    private void collisionSystem() {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            if (positions[i].x < 0) {
                velocities[i].x = -velocities[i].x;
                positions[i].x = 0;
                collisions[i].hasCollided = true;
            } else if (positions[i].x + sizes[i].w > WINDOW_WIDTH) {
                velocities[i].x = -velocities[i].x;
                positions[i].x = WINDOW_WIDTH - sizes[i].w;
                collisions[i].hasCollided = true;
            }
            if (positions[i].y < 0) {
                velocities[i].y = -velocities[i].y;
                positions[i].y = 0;
                collisions[i].hasCollided = true;
            } else if (positions[i].y + sizes[i].h > WINDOW_HEIGHT) {
                velocities[i].y = -velocities[i].y;
                positions[i].y = WINDOW_HEIGHT - sizes[i].h;
                collisions[i].hasCollided = true;
            }
        }
    }

    private void movementSystem(float dt) {
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            positions[i].x += velocities[i].x * dt;
            positions[i].y += velocities[i].y * dt;
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

            positions[i] = new StandardComponents.Position(xPos, yPos);
            velocities[i] = new StandardComponents.Velocity(xVel, yVel);
            colors[i] = new StandardComponents.Color(r, g, b);
            sizes[i] = new StandardComponents.Size(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
            collisions[i] = new StandardComponents.Collision();
        }
    }
}
