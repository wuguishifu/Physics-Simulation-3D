package com.bramerlabs.physics_simulation_3d.simulations.bodies.soft_bodies;

import com.bramerlabs.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.physics_simulation_3d.simulations.bodies.MassPoint;

public class Spring {

    private MassPoint p1, p2;

    public Spring(MassPoint p1, MassPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void update(float dt) {
        applyForce();
    }

    public void applyForce() {
        p1.applyForce(null);
        p2.applyForce(null);
    }

    public Matrix4f getModel() {
        Vector3f p1 = this.p1.position;
        Vector3f p2 = this.p2.position;
        Vector3f axial = Vector3f.normalize(Vector3f.subtract(p2, p1));
        float length = Vector3f.distance(p1, p2);
        Vector3f scale = new Vector3f(length, 1, 1);

        Vector3f rotationAxis;
        float rotationAngle;

        Vector3f cross = Vector3f.cross(axial, Vector3f.e1);
        if (cross.equals(Vector3f.zero)) {
            if (Vector3f.normalize(axial).equals(Vector3f.e1)) {
                rotationAxis = Vector3f.zero;
                rotationAngle = 0;
            } else {
                rotationAxis = Vector3f.e2;
                rotationAngle = 180;
            }
        } else {
            rotationAxis = Vector3f.normalize(Vector3f.cross(axial, Vector3f.e1));
            rotationAngle = Vector3f.angleBetween(axial, Vector3f.e1);
        }
        return Matrix4f.transform(p1, new Vector3f[]{rotationAxis}, new float[]{rotationAngle}, scale);
    }

}
