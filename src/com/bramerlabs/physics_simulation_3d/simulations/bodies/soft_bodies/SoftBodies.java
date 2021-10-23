package com.bramerlabs.physics_simulation_3d.simulations.bodies.soft_bodies;

import com.bramerlabs.engine3D.graphics.Camera;
import com.bramerlabs.engine3D.graphics.Shader;
import com.bramerlabs.engine3D.graphics.io.window.Input;
import com.bramerlabs.engine3D.graphics.io.window.Window;
import com.bramerlabs.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.math.vector.Vector4f;
import com.bramerlabs.engine3D.objects.IcoSphere;
import com.bramerlabs.physics_simulation_3d.simulations.bodies.MassPoint;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import java.awt.*;
import java.util.ArrayList;

public class SoftBodies implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private Renderer renderer;

    private Body body;

    public static void main(String[] args) {
        new SoftBodies().start();
    }

    public SoftBodies() {
        input = new Input();
        window = new Window(new WindowConstants("SoftBodies", new Color(204, 232, 220)), input);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
    }

    public void start() {
        Thread main = new Thread(this, "Soft Bodies Thread");
        main.start();
    }

    public void run() {
        this.init();
        while (!window.shouldClose()) {
            this.update();
            this.render();
        }
        this.destroy();
    }

    private void init() {
        window.create();
        camera.setFocus(new Vector3f(0, 0, 0));

        shader = new Shader("shaders/colored");
        shader.create();

        renderer = new Renderer(window, new Vector3f(-5, 20, 10));

        body = new Body(7.5f);
    }

    private void update() {
//        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);

        body.update(1f/60f);

        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    private void render() {
        body.render(renderer, camera, shader);
        window.swapBuffers();
    }

    private void destroy() {
        window.destroy();
        shader.destroy();
        body.destroy();
    }

}
