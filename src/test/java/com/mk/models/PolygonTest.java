package com.mk.models;

import com.mk.models.geometries.Polygon;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PolygonTest {
    private final double DELTA = 1e-3;
    Polygon polygon;
    Polygon polygon2;

    private void createPolygon() {
        List<Vector3D> vectors = Arrays.asList(
                new Vector3D(-1, -1, 0),
                new Vector3D(-1, 1, 0),
                new Vector3D(1, 1, 0),
                new Vector3D(1, -1, 0));
        polygon = new Polygon(vectors);

        vectors = Arrays.asList(
                new Vector3D(-1, -1, -1),
                new Vector3D(-1, 1, -1),
                new Vector3D(1, 1, -1),
                new Vector3D(1, -1, -1));

        polygon2 = new Polygon(vectors);
    }

    @Test
    public void testForCorrectCreation() {
        createPolygon();
    }

    @Test
    public void isInPolygon() {
        createPolygon();

        Vector3D point = new Vector3D(0, 0, 0);
        Assert.assertTrue(polygon.isInPolygon(point));

        point = new Vector3D(3, 3, 0);
        Assert.assertFalse(polygon.isInPolygon(point));
    }

    @Test
    public void distanceToPolygon() {
        createPolygon();

        Vector3D point = new Vector3D(0, 0, 1);
        Assert.assertEquals(1, polygon.distanceToPoint(point), DELTA);

        point = new Vector3D(2, 0, 0);
        Assert.assertEquals(1, polygon.distanceToPoint(point), DELTA);

        point = new Vector3D(2, 2, 0);
        Assert.assertEquals(1.4142, polygon.distanceToPoint(point), DELTA);

        point = new Vector3D(0, 0, -0.5);
        Assert.assertEquals(0.5, polygon2.distanceToPoint(point), DELTA);
    }

    @Test
    public void normalCalculation() {
        List<Vector3D> vectors = Arrays.asList(
                new Vector3D(-1, -1, -1),
                new Vector3D(-1, 1, -1),
                new Vector3D(1, 1, -1),
                new Vector3D(1, -1, -1));

        Polygon polygon = new Polygon(vectors);

        Assert.assertEquals(0, polygon.normal.getX(), DELTA);
        Assert.assertEquals(0, polygon.normal.getY(), DELTA);
        Assert.assertEquals(-1, polygon.normal.getZ(), DELTA);

        vectors = Arrays.asList(
                new Vector3D(-1, -1, -1),
                new Vector3D(-1, 1, -1),
                new Vector3D(1, 1, 1),
                new Vector3D(1, -1, 1));

        polygon = new Polygon(vectors);

        Assert.assertEquals(0.7071, polygon.normal.getX(), DELTA);
        Assert.assertEquals(0, polygon.normal.getY(), DELTA);
        Assert.assertEquals(-0.7071, polygon.normal.getZ(), DELTA);
    }

    @Test
    public void isInPolygonEdgeCase() {
        final List<Vector3D> vertices = Arrays.asList(
                        new Vector3D(0 , 0, 90),
                        new Vector3D(99, 0, 90),
                        new Vector3D(0, 49, 90),
                        new Vector3D(99, 49, 90));

        final Polygon polygon = new Polygon(vertices);
        final Line line = new Line(new Vector3D(10, 10, 0), new Vector3D(10, 10, 1), 0.05);

        Vector3D intersection = polygon.plane.intersection(line);
        Assert.assertTrue(polygon.isInPolygon(intersection));

        //Assert.assertTrue(polygon.isInPolygon(new Vector3D(0, 0, 90)));

        Assert.assertTrue(polygon.isInPolygon(new Vector3D(0, 10, 90)));
        Assert.assertTrue(polygon.isInPolygon(new Vector3D(10, 0, 90)));
        Assert.assertTrue(polygon.isInPolygon(new Vector3D(99, 10, 90)));
    }



}