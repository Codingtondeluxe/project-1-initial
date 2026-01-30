package com.csc205.project1;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A value object representing a line segment in three-dimensional space.
 *
 * <p>
 * This class models a line segment defined by two endpoints in 3D space.
 * The class is immutable: once created, the endpoints of the line cannot
 * be modified. All geometric operations return new values rather than
 * mutating internal state.
 * </p>
 */
public final class Line3D {

    private static final Logger logger = Logger.getLogger(Line3D.class.getName());

    private final Point3D start;
    private final Point3D end;

    /**
     * Creates a new {@code Line3D} from the given start and end points.
     *
     * <p>
     * A line is defined by two distinct points. If the points are identical,
     * the resulting line has zero length.
     * </p>
     *
     * @param start the starting point of the line
     * @param end   the ending point of the line
     */
    public Line3D(Point3D start, Point3D end) {
        if (start == null || end == null) {
            logger.log(Level.SEVERE, "Line3D constructor received null point(s)");
            throw new IllegalArgumentException("Start and end points must not be null");
        }

        logger.log(Level.INFO, "Creating Line3D from {0} to {1}", new Object[]{start, end});

        this.start = start;
        this.end = end;
    }

    /**
     * Returns the starting point of this line.
     *
     * @return the start point
     */
    public Point3D getStart() {
        logger.log(Level.INFO, "Accessing start point: {0}", start);
        return start;
    }

    /**
     * Returns the ending point of this line.
     *
     * @return the end point
     */
    public Point3D getEnd() {
        logger.log(Level.INFO, "Accessing end point: {0}", end);
        return end;
    }

    /**
     * Computes the length of the line segment.
     *
     * <p>
     * The length is calculated as the Euclidean distance between the
     * start and end points.
     * </p>
     *
     * @return the length of the line segment
     */
    public double length() {
        logger.log(Level.INFO, "Computing length of line {0}", this);
        return start.distanceTo(end);
    }

    /**
     * Returns the direction vector of the line as a {@code Point3D}.
     *
     * <p>
     * The direction vector represents the displacement from the start
     * point to the end point.
     * </p>
     *
     * @return the direction vector
     */
    public Point3D direction() {
        logger.log(Level.INFO, "Computing direction vector for line {0}", this);

        return new Point3D(
                end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ()
        );
    }

    /**
     * Computes the midpoint of the line segment.
     *
     * <p>
     * The midpoint is the average of the start and end coordinates and
     * is commonly used in geometric algorithms and spatial partitioning.
     * </p>
     *
     * @return the midpoint of the line
     */
    public Point3D midpoint() {
        logger.log(Level.INFO, "Computing midpoint of line {0}", this);

        return new Point3D(
                (start.getX() + end.getX()) / 2.0,
                (start.getY() + end.getY()) / 2.0,
                (start.getZ() + end.getZ()) / 2.0
        );
    }

    /**
     * Computes the shortest distance between this line and another line.
     *
     * <p>
     * This method calculates the minimum distance between two line segments
     * in three-dimensional space using vector projection and cross products.
     * </p>
     *
     * <p>
     * If the lines are parallel, the distance is computed as the distance
     * from one line's start point to the other line.
     * </p>
     *
     * @param other the other line
     * @return the shortest distance between the two lines
     */
    public double shortestDistanceTo(Line3D other) {
        if (other == null) {
            logger.log(Level.SEVERE, "shortestDistanceTo called with null line");
            throw new IllegalArgumentException("Other line must not be null");
        }

        logger.log(Level.INFO, "Computing shortest distance between {0} and {1}",
                new Object[]{this, other});

        Point3D u = this.direction();
        Point3D v = other.direction();
        Point3D w0 = new Point3D(
                this.start.getX() - other.start.getX(),
                this.start.getY() - other.start.getY(),
                this.start.getZ() - other.start.getZ()
        );

        double a = dot(u, u);
        double b = dot(u, v);
        double c = dot(v, v);
        double d = dot(u, w0);
        double e = dot(v, w0);

        double denominator = a * c - b * b;

        // Parallel or nearly parallel lines
        if (Math.abs(denominator) < 1e-10) {
            logger.log(Level.WARNING, "Lines appear to be parallel; using fallback distance");
            return distancePointToLine(this.start, other);
        }

        double sc = (b * e - c * d) / denominator;
        double tc = (a * e - b * d) / denominator;

        Point3D closestPointThis = new Point3D(
                start.getX() + sc * u.getX(),
                start.getY() + sc * u.getY(),
                start.getZ() + sc * u.getZ()
        );

        Point3D closestPointOther = new Point3D(
                other.start.getX() + tc * v.getX(),
                other.start.getY() + tc * v.getY(),
                other.start.getZ() + tc * v.getZ()
        );

        return closestPointThis.distanceTo(closestPointOther);
    }

    /**
     * Computes the shortest distance from a point to a line.
     *
     * @param point the point
     * @param line  the line
     * @return the shortest distance
     */
    private static double distancePointToLine(Point3D point, Line3D line) {
        logger.log(Level.INFO, "Computing distance from point {0} to line {1}",
                new Object[]{point, line});

        Point3D lineDir = line.direction();
        Point3D w = new Point3D(
                point.getX() - line.start.getX(),
                point.getY() - line.start.getY(),
                point.getZ() - line.start.getZ()
        );

        double c1 = dot(w, lineDir);
        double c2 = dot(lineDir, lineDir);

        if (c2 == 0.0) {
            logger.log(Level.WARNING, "Line has zero length; falling back to point distance");
            return point.distanceTo(line.start);
        }

        double b = c1 / c2;

        Point3D projection = new Point3D(
                line.start.getX() + b * lineDir.getX(),
                line.start.getY() + b * lineDir.getY(),
                line.start.getZ() + b * lineDir.getZ()
        );

        return point.distanceTo(projection);
    }

    /**
     * Computes the dot product of two {@code Point3D} vectors.
     *
     * @param a first vector
     * @param b second vector
     * @return the dot product
     */
    private static double dot(Point3D a, Point3D b) {
        return a.getX() * b.getX()
                + a.getY() * b.getY()
                + a.getZ() * b.getZ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line3D)) return false;
        Line3D line3D = (Line3D) o;
        return start.equals(line3D.start) && end.equals(line3D.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Line3D{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
