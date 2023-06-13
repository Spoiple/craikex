package org.example.utils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final int vao;
    private final int shaderProgram;
    private final int vbo;
    //    private FloatBuffer vertices;
    private boolean drawing = false;
    private int numVertices = 0;
    private ByteBuffer verticesByteBuffer;

    public Renderer(int width, int height, int vertexBufferSize) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

//        vertices = MemoryUtil.memAllocFloat(4096);
//        verticesByteBuffer = ByteBuffer.allocateDirect(4096 * 4);
        verticesByteBuffer = ByteBuffer.allocateDirect(vertexBufferSize * 20);

//        long size = (long) vertices.capacity() * Float.BYTES;
        long size = verticesByteBuffer.capacity();
        glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

        int vertexShader = createVertexShader();
        int fragmentShader = createFragmentShader();
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glBindFragDataLocation(shaderProgram, 0, "fragColor");
        glLinkProgram(shaderProgram);
        int status = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
        }
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glUseProgram(shaderProgram);

        int floatSize = 4;

        int posAttrib = glGetAttribLocation(shaderProgram, "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 5 * floatSize, 0);

        int colAttrib = glGetAttribLocation(shaderProgram, "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 5 * floatSize, 2 * floatSize);


        int uniModel = glGetUniformLocation(shaderProgram, "model");
        Matrix4f model = new Matrix4f();
        glUniformMatrix4fv(uniModel, false, model.getBuffer());

        int uniView = glGetUniformLocation(shaderProgram, "view");
        Matrix4f view = new Matrix4f();
        glUniformMatrix4fv(uniView, false, view.getBuffer());

//        long window = GLFW.glfwGetCurrentContext();
//        int width, height;
//        try (MemoryStack stack = MemoryStack.stackPush()) {
//            IntBuffer widthBuffer = stack.mallocInt(1);
//            IntBuffer heightBuffer = stack.mallocInt(1);
//            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
//            width = widthBuffer.get();
//            height = heightBuffer.get();
//        }

        int uniProjection = glGetUniformLocation(shaderProgram, "projection");
//        float ratio = width / height;
//        Matrix4f projection = Matrix4f.orthographic(-ratio, ratio, -1f, 1f, -1f, 1f);
        Matrix4f projection = Matrix4f.orthographic(0f, width, height, 0f, -1f, 1f);
        glUniformMatrix4fv(uniProjection, false, projection.getBuffer());

    }

    public void begin() {
        if (drawing) throw new RuntimeException("Begin() has already been called");
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        verticesByteBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, verticesByteBuffer.capacity(), verticesByteBuffer);
        drawing = true;
        numVertices = 0;
    }

    public void drawRect(float x, float y, float width, float height, float r, float g, float b) {
        if (verticesByteBuffer.remaining() < 4 * 5 * 6) {
            flush();
            verticesByteBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, verticesByteBuffer.capacity(), verticesByteBuffer);
        }
        verticesByteBuffer.putFloat(x).putFloat(y).putFloat(r).putFloat(g).putFloat(b);
        verticesByteBuffer.putFloat(x + width).putFloat(y).putFloat(r).putFloat(g).putFloat(b);
        verticesByteBuffer.putFloat(x + width).putFloat(y + height).putFloat(r).putFloat(g).putFloat(b);

        verticesByteBuffer.putFloat(x).putFloat(y).putFloat(r).putFloat(g).putFloat(b);
        verticesByteBuffer.putFloat(x + width).putFloat(y + height).putFloat(r).putFloat(g).putFloat(b);
        verticesByteBuffer.putFloat(x).putFloat(y + height).putFloat(r).putFloat(g).putFloat(b);

        numVertices += 6;
    }

    public void end() {
        if (!drawing) throw new RuntimeException("Begin() needs to be called before end()");
//        glUnmapBuffer(GL_ARRAY_BUFFER);
        drawing = false;
        flush();
    }

    private void flush() {
        if (numVertices == 0) return;
//        verticesByteBuffer.flip();
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindVertexArray(vao);
        glUseProgram(shaderProgram);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glDrawArrays(GL_TRIANGLES, 0, numVertices);
        verticesByteBuffer.clear();
        numVertices = 0;
    }

    private int createVertexShader() {
        String shader = "#version 330 core\n" +
                "\n" +
                "in vec2 position;\n" +
                "in vec3 color;\n" +
                "\n" +
                "out vec3 vertexColor;\n" +
                "\n" +
                "uniform mat4 model;\n" +
                "uniform mat4 view;\n" +
                "uniform mat4 projection;\n" +
                "\n" +
                "void main() {\n" +
                "    vertexColor = color;\n" +
                "    mat4 mvp = projection * view * model;\n" +
                "    gl_Position = mvp * vec4(position, 0.0, 1.0);\n" +
                "}";

        return createShader(shader, GL_VERTEX_SHADER);
    }

    private int createFragmentShader() {
        String shader = "#version 330 core\n" +
                "\n" +
                "in vec3 vertexColor;\n" +
                "\n" +
                "out vec4 fragColor;\n" +
                "\n" +
                "void main() {\n" +
                "    fragColor = vec4(vertexColor, 1.0);\n" +
                "}";

        return createShader(shader, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shader, int glFragmentShader) {
        int fragmentShader = glCreateShader(glFragmentShader);
        glShaderSource(fragmentShader, shader);
        glCompileShader(fragmentShader);

        int status = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(fragmentShader));
        }

        return fragmentShader;
    }
}
