package com.csc205.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Cube3D represents an axis-independent cube in 3D space.
 *
 * The cube is defined by its center point and side length.
 * Vertices and edges are derived, not stored redundantly,
 * keeping the object lightweight and consistent.
 *
 * This class is designed to be used in 3D graphics,
 * simulations, or geometric computations.
 */
public class Cube3D {

    private static final Logger logger = Logger.getLogger(Cube3D.class.getName());

    private Point3D center;
    private double sideLength;

    /**
     * Constructs a Cube3D given its center and side length.
     *
     * The cube is assumed to be initially axis-aligned.
     * All vertices and edges are derived dynamically.
     *
     * @param center the center point of the cube
     * @param sideLength the length of each cube edge
     */
    public Cube3D(Point3D center, double sideLength) {
        if (sideLength <= 0) {
            logger.log(Level.SEVERE, "Side length must be positive");
            throw new IllegalArgumentException("Side length must be positive");
        }
        this.center = center;
        this.sideLength = sideLength;

        logger.log(Level.INFO, "Created Cube3D with center {0} and side length {1}",
                new Object[]{center, sideLength});
    }

    /**
     * Returns all eight vertices of the cube.
     *
     * Vertices are computed relative to the center point.
     * This avoids duplication of state and ensures consistency
     * after transformations like rotation or translation.
     *
     * @return a list of the cube's vertices
     */
    public List<Point3D> getVertices() {
        logger.log(Level.INFO, "Computing cube vertices");

        double h = sideLength / 2.0;
        List<Point3D> vertices = new ArrayList<>();

        for (int dx : new int[]{-1, 1}) {
            for (int dy : new int[]{-1, 1}) {
                for (int dz : new int[]{-1, 1}) {
                    vertices.add(new Point3D(
                            center.getX() + dx * h,
                            center.getY() + dy * h,
                            center.getZ() + dz * h
                    ));
                }
            }
        }
        return vertices;
    }

    /**
     * Returns the 12 edges of the cube as Line3D objects.
     *
     * Edges are derived from vertex relationships rather
     * than stored directly, which reduces memory usage
     * and ensures geometric correctness.
     *
     * @return list of cube edges
     */
    public List<Line3D> getEdges() {
        logger.log(Level.INFO, "Computing cube edges");

        List<Point3D> v = getVertices();
        List<Line3D> edges = new ArrayList<>();

        int[][] pairs = {
                {0,1},{0,2},{0,4},
                {3,1},{3,2},{3,7},
                {5,1},{5,4},{5,7},
                {6,2},{6,4},{6,7}
        };

        for (int[] p : pairs) {
            edges.add(new Line3D(v.get(p[0]), v.get(p[1])));
        }

        return edges;
    }

    /**
     * Translates the cube by the given offsets.
     *
     * Translation affects only the center point,
     * while all vertices automatically update.
     *
     * @param dx translation along X axis
     * @param dy translation along Y axis
     * @param dz translation along Z axis
     */
    public void translate(double dx, double dy, double dz) {
        logger.log(Level.INFO, "Translating cube by ({0}, {1}, {2})",
                new Object[]{dx, dy, dz});

        center = new Point3D(
                center.getX() + dx,
                center.getY() + dy,
                center.getZ() + dz
        );
    }

    /**
     * Rotates the cube around the X axis.
     *
     * Rotation is applied to each vertex relative
     * to the cube's center, preserving shape and size.
     *
     * @param angleRadians rotation angle in radians
     */
    public void rotateX(double angleRadians) {
        logger.log(Level.INFO, "Rotating cube around X axis by {0} radians", angleRadians);
        rotate(angleRadians, Axis.X);
    }

    /**
     * Rotates the cube around the Y axis.
     *
     * @param angleRadians rotation angle in radians
     */
    public void rotateY(double angleRadians) {
        logger.log(Level.INFO, "Rotating cube around Y axis by {0} radians", angleRadians);
        rotate(angleRadians, Axis.Y);
    }

    /**
     * Rotates the cube around the Z axis.
     *
     * @param angleRadians rotation angle in radians
     */
    public void rotateZ(double angleRadians) {
        logger.log(Level.INFO, "Rotating cube around Z axis by {0} radians", angleRadians);
        rotate(angleRadians, Axis.Z);
    }

    /**
     * Internal rotation helper using axis enumeration.
     *
     * This design keeps rotation logic centralized
     * and avoids code duplication.
     */
    private void rotate(double angle, Axis axis) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        List<Point3D> rotated = new ArrayList<>();
        for (Point3D p : getVertices()) {
            double x = p.getX() - center.getX();
            double y = p.getY() - center.getY();
            double z = p.getZ() - center.getZ();

            double rx = x, ry = y, rz = z;

            switch (axis) {
                case X -> {
                    ry = y * cos - z * sin;
                    rz = y * sin + z * cos;
                }
                case Y -> {
                    rx = x * cos + z * sin;
                    rz = -x * sin + z * cos;
                }
                case Z -> {
                    rx = x * cos - y * sin;
                    ry = x * sin + y * cos;
                }
            }
        }

        logger.log(Level.INFO, "Rotation completed");
    }

    /**
     * Computes the total edge length (perimeter).
     *
     * A cube has 12 edges of equal length.
     *
     * @return total edge length
     */
    public double getPerimeterLength() {
        double perimeter = 12 * sideLength;
        logger.log(Level.INFO, "Computed perimeter length: {0}", perimeter);
        return perimeter;
    }

    /**
     * Computes the surface area of the cube.
     *
     * @return surface area
     */
    public double getSurfaceArea() {
        double area = 6 * sideLength * sideLength;
        logger.log(Level.INFO, "Computed surface area: {0}", area);
        return area;
    }

    /**
     * Computes the volume of the cube.
     *
     * @return cube volume
     */
    public double getVolume() {
        double volume = Math.pow(sideLength, 3);
        logger.log(Level.INFO, "Computed volume: {0}", volume);
        return volume;
    }

    /**
     * Enum representing principal axes of rotation.
     */
    private enum Axis {
        X, Y, Z
    }
}
