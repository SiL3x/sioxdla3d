package com.mk.models;

import com.mk.models.geometries.Polygon;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PolygonTest {
    private final double DELTA = 1e-3;
    Polygon polygon;
    Polygon polygon2;
    Polygon polygon3;

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

        vectors = Arrays.asList(
                new Vector3D(0, 0, 1),
                new Vector3D(1, 0, 0),
                new Vector3D(0, 1, 0));

        polygon3 = new Polygon(vectors);
        //Todo: write isInPolygon test for poly3
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
    }

    @Test void isNotInPolygon() {
        createPolygon();

        Vector3D point = new Vector3D(3, 3, 0);
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

        Assert.assertTrue(polygon.isInPolygon(new Vector3D(0, 10, 90)));
        Assert.assertTrue(polygon.isInPolygon(new Vector3D(10, 0, 90)));
        Assert.assertTrue(polygon.isInPolygon(new Vector3D(99, 10, 90)));
    }


    @Test
    public void isIntersectionInFace() {
        final Line line = new Line(new Vector3D(50, 50, 0), new Vector3D(50, 50, 1), 0.05);

        Polygon face = new Polygon(
                Arrays.asList( // 1
                new Vector3D(0 , 0, 990),
                new Vector3D(300, 0, 990),
                new Vector3D(130, 237, 910),
                new Vector3D(0 , 232, 910)
                )
        );

        Vector3D intersection = face.plane.intersection(line);
    }

    @Test
    public void determineZfromFace() {
        Polygon face = new Polygon(
                Arrays.asList(
                        new Vector3D(83 , 375, 888),
                        new Vector3D(280, 572, 746),
                        new Vector3D(288, 622, 735)
                )
        );

        final Line line = new Line(new Vector3D(705, 997, 0), new Vector3D(705, 997, 1), 0.05);
        Vector3D intersection = face.plane.intersection(line);

        //System.out.println("intersection = " + intersection + "   is in polygon: " + face.isInPolygon(intersection));
    }

    @Test
    public void isIntersectionInPolygon() {
        Polygon face1 = new Polygon(
                Arrays.asList(
                        new Vector3D(100, 999, 950),
                        new Vector3D(200, 999, 850),
                        new Vector3D(100, 0, 950)
                )
        );

        Line line = new Line(new Vector3D(963, 0, 0), new Vector3D(963, 0, 990), 0.05);
        Vector3D intersection = face1.plane.intersection(line);
        Assert.assertFalse(face1.isInPolygon(intersection));

        // Not in polygon
        Polygon face2 = new Polygon(
                Arrays.asList(
                        new Vector3D(250, 789, 750),
                        new Vector3D(350, 789, 650),
                        new Vector3D(350, 0, 650)
                )
        );

        line = new Line(new Vector3D(354, 0, 0), new Vector3D(354, 0, 1), 0.05);
        intersection = face2.plane.intersection(line);
        System.out.println("intersection = " + intersection);
        Assert.assertFalse(face2.isInPolygon(intersection));

        // In polygon
        Polygon face3 = new Polygon(
                Arrays.asList(
                        new Vector3D(350, 0, 650),
                        new Vector3D(550, 0, 650),
                        new Vector3D(350, 789, 650)
                )
        );

        intersection = face3.plane.intersection(line);
        System.out.println("intersection = " + intersection);
        Assert.assertTrue(face3.isInPolygon(intersection));

        // Not in Polygon
        Polygon face4 = new Polygon(
                Arrays.asList(
                        new Vector3D(150, 789, 650),
                        new Vector3D(250, 789, 750),
                        new Vector3D(250, 0, 750)
                )
        );

        intersection = face4.plane.intersection(line);
        System.out.println("intersection = " + intersection);
        Assert.assertFalse(face4.isInPolygon(intersection));

        List<Polygon> polygons = new ArrayList<>();

        List<List<Vector3D>> vertices =
                Arrays.asList(
                        Arrays.asList( // 1
                                new Vector3D(0, 0, 725),
                                new Vector3D(150, 0,650),
                                new Vector3D(0, 789, 725)
                        ),
                        Arrays.asList( // 2
                                new Vector3D(0, 789, 725),
                                new Vector3D(150, 0,650),
                                new Vector3D(150, 789, 650)
                        ),
                        Arrays.asList( // 3
                                new Vector3D(150, 0,650),
                                new Vector3D(250, 0,  750),
                                new Vector3D(150, 789, 650)
                        ),
                        Arrays.asList( // 4
                                new Vector3D(150, 789, 650),
                                new Vector3D(250, 0,  750),
                                new Vector3D(250, 789, 750)
                        ),
                        Arrays.asList( // 5
                                new Vector3D(250, 0,  750),
                                new Vector3D(350, 0, 650),
                                new Vector3D(250, 789, 750)
                        ),
                        Arrays.asList( // 6
                                new Vector3D(250, 789, 750),
                                new Vector3D(350, 0, 650),
                                new Vector3D(350, 789, 650)
                        ),
                        Arrays.asList( // 7
                                new Vector3D(350, 0, 650),
                                new Vector3D(550, 0, 650),
                                new Vector3D(350, 789, 650)
                        ),
                        Arrays.asList( // 8
                                new Vector3D(350, 789, 650),
                                new Vector3D(550, 0, 650),
                                new Vector3D(550, 789, 650)
                        ),
                        Arrays.asList( // 9
                                new Vector3D(550, 0, 650),
                                new Vector3D(670, 394, 500),
                                new Vector3D(550, 789, 650)
                        ),
                        Arrays.asList( // 10
                                new Vector3D(550, 0, 650),
                                new Vector3D(789, 0, 650),
                                new Vector3D(670, 394, 500)
                        ),
                        Arrays.asList( // 11
                                new Vector3D(789, 0, 650),
                                new Vector3D(789, 789, 650),
                                new Vector3D(670, 394, 500)
                        ),
                        Arrays.asList( // 12
                                new Vector3D(550, 789, 650),
                                new Vector3D(670, 394, 500),
                                new Vector3D(789, 789, 650)
                        )
                );

                for (List<Vector3D> vertice : vertices) {
                    polygons.add(new Polygon(vertice));
                }

        for (Polygon polygon : polygons) {
            intersection = polygon.plane.intersection(line);
            System.out.println("intersection = " + intersection + "   isInPolygon = " + polygon.isInPolygon(intersection));
        }
    }
}
