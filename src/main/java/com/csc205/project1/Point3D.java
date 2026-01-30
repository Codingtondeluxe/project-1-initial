package com.csc205.project1;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A value object representing a point in three-dimensional Cartesian space.
 *
 * <p>
 * This class is immutable by design. Once created, the coordinates of a
 * {@code Point3D} instance cannot be modified. All transformation operations
 * (such as rotation or translation) return new {@code Point3D} instances.
 * </p>
 */
public final class Point3D {

    private static final Logger logger = Logger.getLogger(Point3D.class.getName());

    private final double x;
    private final double y;
    private final double z;

    /**
     * Creates a new {@code Point3D} with the given coordinates.
     *
     * <p>
     * This constructor establishes the fundamental state of the object.
     * Since the class is immutable, these values define the point for
     * its entire lifetime.
     * </p>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Point3D(double x, double y, double z) {
        logger.log(Level.INFO, "Creating Point3D with coordinates ({0}, {1}, {2})",
                new Object[]{x, y, z});

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x value
     */
    public double getX() {
        logger.log(Level.INFO, "Accessing x-coordinate: {0}", x);
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y value
     */
    public double getY() {
        logger.log(Level.INFO, "Accessing y-coordinate: {0}", y);
        return y;
    }

    /**
     * Returns the z-coordinate of this point.
     *
     * @return the z value
     */
    public double getZ() {
        logger.log(Level.INFO, "Accessing z-coordinate: {0}", z);
        return z;
    }

    /**
     * Computes the Euclidean distance from this point to another point.
     *
     * <p>
     * This method applies the standard 3D distance formula:
     * </p>
     *
     * <pre>
     * sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)
     * </pre>
     *
     * @param other the point to measure distance to
     * @return the Euclidean distance
     */
    public double distanceTo(Point3D other) {
        if (other == null) {
            logger.log(Level.SEVERE, "distanceTo called with null argument");
            throw new IllegalArgumentException("Other point must not be null");
        }

        logger.log(Level.INFO, "Computing distance from {0} to {1}", new Object[]{this, other});

        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double dz = other.z - this.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Translates this point by the given offsets along each axis.
     *
     * <p>
     * Translation is a fundamental geometric operation that shifts a point
     * without altering its orientation.
     * </p>
     *
     * @param dx offset along the x-axis
     * @param dy offset along the y-axis
     * @param dz offset along the z-axis
     * @return a new translated {@code Point3D}
     */
    public Point3D translate(double dx, double dy, double dz) {
        logger.log(Level.INFO, "Translating point {0} by ({1}, {2}, {3})",
                new Object[]{this, dx, dy, dz});

        return new Point3D(x + dx, y + dy, z + dz);
    }

    /**
     * Rotates this point around the X-axis by the given angle.
     *
     * <p>
     * Rotation is performed using the standard 3D rotation matrix
     * for the X-axis.
     * </p>
     *
     * @param radians rotation angle in radians
     * @return a new rotated {@code Point3D}
     */
    public Point3D rotateAroundX(double radians) {
        logger.log(Level.INFO, "Rotating point {0} around X-axis by {1} radians",
                new Object[]{this, radians});

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double newY = y * cos - z * sin;
        double newZ = y * sin + z * cos;

        return new Point3D(x, newY, newZ);
    }

    /**
     * Rotates this point around the Y-axis by the given angle.
     *
     * @param radians rotation angle in radians
     * @return a new rotated {@code Point3D}
     */
    public Point3D rotateAroundY(double radians) {
        logger.log(Level.INFO, "Rotating point {0} around Y-axis by {1} radians",
                new Object[]{this, radians});

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double newX = x * cos + z * sin;
        double newZ = -x * sin + z * cos;

        return new Point3D(newX, y, newZ);
    }

    /**
     * Rotates this point around the Z-axis by the given angle.
     *
     * @param radians rotation angle in radians
     * @return a new rotated {@code Point3D}
     */
    public Point3D rotateAroundZ(double radians) {
        logger.log(Level.INFO, "Rotating point {0} around Z-axis by {1} radians",
                new Object[]{this, radians});

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;

        return new Point3D(newX, newY, z);
    }

    /**
     * Computes the magnitude (distance from the origin).
     *
     * @return the vector length of this point
     */
    public double magnitude() {
        logger.log(Level.INFO, "Computing magnitude of point {0}", this);
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return Double.compare(point3D.x, x) == 0
                && Double.compare(point3D.y, y) == 0
                && Double.compare(point3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
