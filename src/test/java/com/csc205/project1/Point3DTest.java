package com.csc205.project1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Point3D class.
 *
 * These tests validate correctness for normal use cases,
 * boundary values, floating-point behavior, and object
 * contract rules such as equality and hashCode.
 *
 * Designed in the style of Spring's getting-started tests:
 * clear intent, readable structure, and focused assertions.
 */
public class Point3DTest {

    private static final double EPSILON = 1e-9;

    /**
     * Verifies that the constructor correctly assigns coordinates.
     */
    @Test
    void constructor_setsCoordinatesCorrectly() {
        Point3D p = new Point3D(1.5, -2.0, 3.25);

        assertEquals(1.5, p.getX(), EPSILON);
        assertEquals(-2.0, p.getY(), EPSILON);
        assertEquals(3.25, p.getZ(), EPSILON);
    }

    /**
     * Ensures that the origin (0,0,0) is represented correctly.
     */
    @Test
    void origin_isHandledCorrectly() {
        Point3D origin = new Point3D(0, 0, 0);

        assertEquals(0.0, origin.getX(), EPSILON);
        assertEquals(0.0, origin.getY(), EPSILON);
        assertEquals(0.0, origin.getZ(), EPSILON);
    }

    /**
     * Tests distance calculation between identical points.
     * Distance should be exactly zero.
     */
    @Test
    void distanceTo_samePoint_isZero() {
        Point3D p = new Point3D(1, 2, 3);

        assertEquals(0.0, p.distanceTo(p), EPSILON);
    }

    /**
     * Tests distance between two points using a known
     * 3-4-12 right triangle in 3D space.
     *
     * sqrt(3^2 + 4^2 + 12^2) = 13
     */
    @Test
    void distanceTo_knownValues_isCorrect() {
        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(3, 4, 12);

        assertEquals(13.0, p1.distanceTo(p2), EPSILON);
    }

    /**
     * Verifies symmetry of distance calculation.
     *
     * distance(a, b) == distance(b, a)
     */
    @Test
    void distanceTo_isSymmetric() {
        Point3D a = new Point3D(1, 2, 3);
        Point3D b = new Point3D(-4, 5, -6);

        assertEquals(
                a.distanceTo(b),
                b.distanceTo(a),
                EPSILON
        );
    }

    /**
     * Tests behavior with negative coordinates.
     */
    @Test
    void negativeCoordinates_areHandledCorrectly() {
        Point3D p1 = new Point3D(-1, -2, -3);
        Point3D p2 = new Point3D(-4, -6, -3);

        assertEquals(5.0, p1.distanceTo(p2), EPSILON);
    }

    /**
     * Ensures equality works for points with identical coordinates.
     */
    @Test
    void equals_sameCoordinates_returnsTrue() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(1, 2, 3);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Ensures inequality for different coordinates.
     */
    @Test
    void equals_differentCoordinates_returnsFalse() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(3, 2, 1);

        assertNotEquals(p1, p2);
    }

    /**
     * Ensures equals handles null safely.
     */
    @Test
    void equals_null_returnsFalse() {
        Point3D p = new Point3D(1, 2, 3);

        assertNotEquals(p, null);
    }

    /**
     * Ensures equals handles different object types safely.
     */
    @Test
    void equals_differentType_returnsFalse() {
        Point3D p = new Point3D(1, 2, 3);

        assertNotEquals(p, "not a point");
    }

    /**
     * Tests floating-point precision edge cases.
     *
     * This guards against naive equality checks on doubles.
     */
    @Test
    void floatingPointPrecision_isHandledCorrectly() {
        Point3D p1 = new Point3D(0.1 + 0.2, 0, 0);
        Point3D p2 = new Point3D(0.3, 0, 0);

        assertEquals(
                p1.getX(),
                p2.getX(),
                EPSILON
        );
    }

    /**
     * Ensures large coordinate values do not overflow
     * or produce NaN results in distance calculations.
     */
    @Test
    void largeValues_doNotOverflow() {
        Point3D p1 = new Point3D(1e150, 0, 0);
        Point3D p2 = new Point3D(0, 0, 0);

        double distance = p1.distanceTo(p2);

        assertTrue(Double.isFinite(distance));
    }
}
