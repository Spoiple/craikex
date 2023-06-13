package org.example;


import org.example.loops.*;
import org.example.utils.Renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    final static int WINDOW_WIDTH = 640;
    final static int WINDOW_HEIGHT = 480;
    final static float RECTANGLE_WIDTH = 10;
    final static float RECTANGLE_HEIGHT = 10;
    static int NUMBER_OF_RECTANGLES = 500;

    public static void main(String[] args) {
        // jvm options: -XX:+EnablePrimitiveClasses

        // todo disable when testing
        glfwInit();
        long window = createWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        glfwSwapInterval(0);
        final Renderer renderer = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT, NUMBER_OF_RECTANGLES * 6);
//        final Renderer renderer = null;

//        OODJavaLoop func = new OODJavaLoop(renderer, NUMBER_OF_RECTANGLES, WINDOW_WIDTH, WINDOW_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
//        OODValhallaLoop func = new OODValhallaLoop(renderer, NUMBER_OF_RECTANGLES, WINDOW_WIDTH, WINDOW_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
//        DODJavaLoop func = new DODJavaLoop(renderer, NUMBER_OF_RECTANGLES, WINDOW_WIDTH, WINDOW_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        DODValhallaLoop func = new DODValhallaLoop(renderer, NUMBER_OF_RECTANGLES, WINDOW_WIDTH, WINDOW_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

//        int frames = 0;
//        long start = System.currentTimeMillis();
//        while (true) {
//        while (System.currentTimeMillis() - start < 60000) {

        while (!glfwWindowShouldClose(window)) {
//            frames++;
            func.update();

            // todo disable when testing
            func.render();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
//        long end = System.currentTimeMillis() - start;
//        System.out.println("frames: " + frames + " time: " + end);
        glfwTerminate();
    }

    private static long createWindow(int width, int height) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        long window = glfwCreateWindow(width, height, "Demo", NULL, NULL);
        glfwMakeContextCurrent(window);
        createCapabilities();
        return window;
    }
}
