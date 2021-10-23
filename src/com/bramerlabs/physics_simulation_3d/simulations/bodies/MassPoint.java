package com.bramerlabs.physics_simulation_3d.simulations.bodies;

import com.bramerlabs.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.engine3D.math.vector.Vector3f;

public class MassPoint {

    public Vector3f position;
    public Vector3f velocity;
    public Vector3f acceleration;
    public Vector3f scale = new Vector3f(0.2f, 0.2f, 0.2f);
    public Vector3f rotation = new Vector3f(0, 0, 0);

    public float mass;

    public MassPoint() {
        this.position = new Vector3f(0, 0, 0);
        this.velocity = new Vector3f(0, 0, 0);
        this.acceleration = new Vector3f(0, 0, 0);
    }

    public MassPoint(Vector3f position) {
        this.position = position;
        this.velocity = new Vector3f(0, 0, 0);
        this.acceleration = new Vector3f(0, 0, 0);
    }

    public MassPoint(Vector3f position, Vector3f velocity) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = new Vector3f(0, 0, 0);
    }

    public void applyForce(Vector3f force) {

    }

    public void update(float dt) {
        if (!velocity.equals(Vector3f.zero)) {
            position = Vector3f.add(position, Vector3f.normalize(velocity, dt));
        }
    }

    public Matrix4f getModel() {
        return Matrix4f.transform(position, rotation, scale);
    }

}
