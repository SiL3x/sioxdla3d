package com.mk.models;

import com.mk.models.geometries.Edge;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class EdgeTest {
    private final double DELTA = 1e-3;
    private double TOLERANCE = 0.005;

    private final Edge edge = new Edge(
                                    new Vector3D(-1, 0 , 0),
                                    new Vector3D(1, 0, 0), TOLERANCE);

    @Test
    public void DistanceWithAbscissaOnEdge() {
        Vector3D point = new Vector3D(0, 0, 1);

        Assert.assertEquals(1, edge.distance(point), DELTA);
    }

    @Test
    public void DistanceWithAbscissaNotOnEdge() {
        Vector3D point = new Vector3D(2, 0, 1);
        Assert.assertEquals(1.4142, edge.edgeDistance(point), DELTA);
    }

    @Test
    public void DistanceToPointOnEdge() {
        Vector3D point = new Vector3D(0, 0, 0);
        Assert.assertEquals(0, edge.edgeDistance(point), DELTA);
    }

    @Test
    public void IntersectsWithEdge() {
        Line line = new Line(
                new Vector3D(0, 1, 0),
                new Vector3D(0, -1, 0), TOLERANCE);

        Assert.assertTrue(edge.intersects(line));

        line = new Line(
                new Vector3D(1, 1, 0),
                new Vector3D(-1, 1, 0), TOLERANCE);

        Assert.assertFalse(edge.intersects(line));

        line = new Line(
                new Vector3D(2, 1, 0),
                new Vector3D(2, -1, 0), TOLERANCE);

        Assert.assertFalse(edge.intersects(line));
    }

    @Test
    public void PointOnEdge() {
        Vector3D point = new Vector3D(0, 0, 0);
        Assert.assertTrue(edge.isOnEdge(point));

        point = new Vector3D(0, 0, 1);
        Assert.assertFalse(edge.isOnEdge(point));
    }
}