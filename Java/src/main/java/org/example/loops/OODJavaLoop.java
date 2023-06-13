package org.example.loops;

import org.example.Rectangle;
import org.example.utils.Renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class OODJavaLoop {

    protected final Renderer renderer;
    protected final int NUMBER_OF_RECTANGLES;
    protected final int WINDOW_WIDTH, WINDOW_HEIGHT;
    protected final float RECTANGLE_WIDTH, RECTANGLE_HEIGHT;
    private final Rectangle[] rects;
    protected long lastFrameTime;

    public OODJavaLoop(Renderer renderer, int NUMBER_OF_RECTANGLES, int WINDOW_WIDTH, int WINDOW_HEIGHT, float RECTANGLE_WIDTH, float RECTANGLE_HEIGHT) {
        this.renderer = renderer;
        this.NUMBER_OF_RECTANGLES = NUMBER_OF_RECTANGLES;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.RECTANGLE_WIDTH = RECTANGLE_WIDTH;
        this.RECTANGLE_HEIGHT = RECTANGLE_HEIGHT;
        lastFrameTime = System.currentTimeMillis();
        rects = generateOODRectangles(NUMBER_OF_RECTANGLES);
    }

    public final void update() {
        long current = System.currentTimeMillis();
        float delta = current - lastFrameTime;
        lastFrameTime = current;
        updateLogic(delta / 1000.f);
    }

    public void updateLogic(float dt) {
        for (int i = 0; i < rects.length; i++)
            rects[i].updatePosition(dt);
        for (int i = 0; i < rects.length; i++)
            rects[i].checkBoundingBox(WINDOW_WIDTH, WINDOW_HEIGHT);
        for (int i = 0; i < rects.length; i++)
            rects[i].handleCollision();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        renderer.begin();
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            renderer.drawRect(rects[i].xPos, rects[i].yPos, rects[i].width, rects[i].height, rects[i].r, rects[i].g, rects[i].b);
        }
        renderer.end();
    }

    private Rectangle[] generateOODRectangles(int noOfRectangles) {
        Rectangle[] rects = new Rectangle[noOfRectangles];
        for (int i = 0; i < noOfRectangles; i++) {
            float xPos = ((float) Math.random() * (WINDOW_WIDTH - RECTANGLE_WIDTH));
            float yPos = ((float) Math.random() * (WINDOW_HEIGHT - RECTANGLE_HEIGHT));
            float xVel = (((float) Math.random() * 160)) - 80;
            float yVel = (((float) Math.random() * 160)) - 80;
            float r = (float) Math.random();
            float g = (float) Math.random();
            float b = (float) Math.random();

            rects[i] = new Rectangle(xPos, yPos, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, r, g, b, xVel, yVel);
        }
        return rects;
    }
}
