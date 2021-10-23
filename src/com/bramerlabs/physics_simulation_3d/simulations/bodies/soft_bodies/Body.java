package com.bramerlabs.physics_simulation_3d.simulations.bodies.soft_bodies;

import com.bramerlabs.engine3D.graphics.Camera;
import com.bramerlabs.engine3D.graphics.Shader;
import com.bramerlabs.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.math.vector.Vector4f;
import com.bramerlabs.engine3D.objects.Cylinder;
import com.bramerlabs.engine3D.objects.IcoSphere;
import com.bramerlabs.physics_simulation_3d.simulations.bodies.IcoPointGenerator;
import com.bramerlabs.physics_simulation_3d.simulations.bodies.MassPoint;

import java.util.ArrayList;

public class Body {

    private final ArrayList<MassPoint> points;
    private final ArrayList<Spring> springs;
    private final IcoSphere sphere; // used to render the mass points
    private final Cylinder cylinder; // used to render springs

    public Body(float radius) {
        this.points = new ArrayList<>();
        this.springs = new ArrayList<>();

        sphere = new IcoSphere(new Vector3f(0, 0, 0), new Vector4f(0.4f, 0.4f, 0.4f, 1.0f), 1f);
        sphere.createMesh();

        cylinder = new Cylinder(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0),
                new Vector4f(0.5f, 0.7f, 0.7f, 1.0f), 0.05f);
        cylinder.createMesh();

        IcoPointGenerator generator = new IcoPointGenerator();
        for (Vector3f point : generator.getIcoPoints()) {
            this.points.add(new MassPoint(Vector3f.normalize(point, radius)));
        }

        for (int[] connection : generator.getConnections()) {
            this.springs.add(new Spring(points.get(connection[0]), points.get(connection[1])));
        }
    }

    public void update(float dt) {
        for (MassPoint point : points) {
            point.update(dt);
        }
        for (Spring spring : springs) {
            spring.update(dt);
        }
    }

    public void render(Renderer renderer, Camera camera, Shader shader) {
        for (MassPoint point : points) {
            renderer.renderInstancedMesh(sphere, camera, shader, point.getModel());
        }
        for (Spring spring : springs) {
            renderer.renderInstancedMesh(cylinder, camera, shader, spring.getModel());
        }
    }

    public void destroy() {
        sphere.destroy();
    }

}
