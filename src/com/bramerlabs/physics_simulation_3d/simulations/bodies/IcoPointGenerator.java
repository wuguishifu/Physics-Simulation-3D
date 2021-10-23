package com.bramerlabs.physics_simulation_3d.simulations.bodies;

import com.bramerlabs.engine3D.math.Triangle;
import com.bramerlabs.engine3D.math.vector.Vector3f;

import java.util.ArrayList;

public class IcoPointGenerator {

    private static final float PHI_OVER_TWO = 1.618033988749895f/2.0f;
    private static final Vector3f[] v = new Vector3f[]{
            new Vector3f( 0.5f, 0,  PHI_OVER_TWO),
            new Vector3f( 0.5f, 0, -PHI_OVER_TWO),
            new Vector3f(-0.5f, 0,  PHI_OVER_TWO),
            new Vector3f(-0.5f, 0, -PHI_OVER_TWO),
            new Vector3f( PHI_OVER_TWO,  0.5f, 0),
            new Vector3f( PHI_OVER_TWO, -0.5f, 0),
            new Vector3f(-PHI_OVER_TWO,  0.5f, 0),
            new Vector3f(-PHI_OVER_TWO, -0.5f, 0),
            new Vector3f(0,  PHI_OVER_TWO,  0.5f),
            new Vector3f(0,  PHI_OVER_TWO, -0.5f),
            new Vector3f(0, -PHI_OVER_TWO,  0.5f),
            new Vector3f(0, -PHI_OVER_TWO, -0.5f),
    };

    public static ArrayList<Triangle> generateTriangles(int depth) {
        ArrayList<Triangle> faces = new ArrayList<>();
        faces.addAll(subdivide(v[0], v[2], v[10], depth));
        faces.addAll(subdivide(v[0], v[10], v[5], depth));
        faces.addAll(subdivide(v[0], v[5], v[4], depth));
        faces.addAll(subdivide(v[0], v[4], v[8], depth));
        faces.addAll(subdivide(v[0], v[8], v[2], depth));
        faces.addAll(subdivide(v[3], v[1], v[11], depth));
        faces.addAll(subdivide(v[3], v[11], v[7], depth));
        faces.addAll(subdivide(v[3], v[7], v[6], depth));
        faces.addAll(subdivide(v[3], v[6], v[9], depth));
        faces.addAll(subdivide(v[3], v[9], v[1], depth));
        faces.addAll(subdivide(v[2], v[6], v[7], depth));
        faces.addAll(subdivide(v[2], v[7], v[10], depth));
        faces.addAll(subdivide(v[10], v[7], v[11], depth));
        faces.addAll(subdivide(v[10], v[11], v[5], depth));
        faces.addAll(subdivide(v[5], v[11], v[1], depth));
        faces.addAll(subdivide(v[5], v[1], v[4], depth));
        faces.addAll(subdivide(v[4], v[1], v[9], depth));
        faces.addAll(subdivide(v[4], v[9], v[8], depth));
        faces.addAll(subdivide(v[8], v[9], v[6], depth));
        faces.addAll(subdivide(v[8], v[6], v[2], depth));
        return faces;
    }

    private static ArrayList<Triangle> subdivide(Vector3f v1, Vector3f v2, Vector3f v3, int depth) {
        ArrayList<Triangle> faces = new ArrayList<>();
        if (depth == 0) {
            Vector3f v1p = Vector3f.normalize(v1);
            Vector3f v2p = Vector3f.normalize(v2);
            Vector3f v3p = Vector3f.normalize(v3);
            faces.add(new Triangle(v1p, v2p, v3p));
            return faces;
        }
        Vector3f v12 = Vector3f.normalize(new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z));
        Vector3f v23 = Vector3f.normalize(new Vector3f(v2.x + v3.x, v2.y + v3.y, v2.z + v3.z));
        Vector3f v31 = Vector3f.normalize(new Vector3f(v3.x + v1.x, v3.y + v1.y, v3.z + v1.z));
        faces.addAll(subdivide( v1, v12, v31, depth - 1));
        faces.addAll(subdivide( v2, v23, v12, depth - 1));
        faces.addAll(subdivide( v3, v31, v23, depth - 1));
        faces.addAll(subdivide(v12, v23, v31, depth - 1));
        return faces;
    }

    private ArrayList<Vector3f> icoPoints;
    private ArrayList<int[]> connections;

    public IcoPointGenerator() {
        ArrayList<Triangle> triangles = generateTriangles(2);

        icoPoints = new ArrayList<>();
        for (Triangle triangle : triangles) {
            if (!icoPoints.contains(triangle.v1)) {
                icoPoints.add(triangle.v1);
            }
            if (!icoPoints.contains(triangle.v2)) {
                icoPoints.add(triangle.v2);
            }
            if (!icoPoints.contains(triangle.v3)) {
                icoPoints.add(triangle.v3);
            }
        }

        connections = new ArrayList<>();
        for (Triangle triangle : triangles) {
            connections.add(new int[]{
                    icoPoints.indexOf(triangle.v1),
                    icoPoints.indexOf(triangle.v2)
            });
            connections.add(new int[]{
                    icoPoints.indexOf(triangle.v2),
                    icoPoints.indexOf(triangle.v3)
            });
            connections.add(new int[]{
                    icoPoints.indexOf(triangle.v3),
                    icoPoints.indexOf(triangle.v1)
            });
        }
    }

    public ArrayList<Vector3f> getIcoPoints() {
        return this.icoPoints;
    }

    public ArrayList<int[]> getConnections() {
        return this.connections;
    }

}
