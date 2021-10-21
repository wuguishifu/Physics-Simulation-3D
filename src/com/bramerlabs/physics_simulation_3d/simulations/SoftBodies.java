package com.bramerlabs.physics_simulation_3d.simulations;

import com.bramerlabs.engine3D.graphics.Camera;
import com.bramerlabs.engine3D.graphics.Material;
import com.bramerlabs.engine3D.graphics.Shader;
import com.bramerlabs.engine3D.graphics.io.window.Input;
import com.bramerlabs.engine3D.graphics.io.window.Window;
import com.bramerlabs.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.math.vector.Vector4f;
import com.bramerlabs.engine3D.objects.Cube;
import com.bramerlabs.engine3D.objects.ObjectLoader;
import com.bramerlabs.engine3D.objects.RenderObject;
import org.lwjgl.opengl.GL46;

import java.awt.*;

public class SoftBodies implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private Renderer renderer;

    private Cube cube;

    public static void main(String[] args) {
        new SoftBodies().start();
    }

    public SoftBodies() {
        input = new Input();
        window = new Window(new WindowConstants("SoftBodies", new Color(204, 232, 220)), input);
        camera = new Camera(Vector3f.zero, Vector3f.zero, input);
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
        camera.setFocus(Vector3f.zero);

        shader = new Shader("shaders/colored");
        shader.create();

        renderer = new Renderer(window, new Vector3f(-5, 20, 10));

        cube = new Cube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
        cube.createMesh();
    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    private void render() {
        renderer.renderMesh(cube, camera, shader);
        window.swapBuffers();
    }

    private void destroy() {
        window.destroy();
        shader.destroy();
    }

}
