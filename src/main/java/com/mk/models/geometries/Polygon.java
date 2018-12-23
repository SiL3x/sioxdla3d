package com.mk.models.geometries;

import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.*;

public class Polygon {
    List<Vector3D> vectors;

    public Plane plane;
    public List<Edge> edges;
    public Vector3D normal;

    public Polygon(List<Vector3D> inVectors) {
        vectors = inVectors;
        edges = calculateBorders(vectors);
        calculatePlane();
    }

    private List<Edge> calculateBorders(final List<Vector3D> vectors) {
        List<Edge> borders = new ArrayList<>();
        List<Vector3D> sortedVectors = new LinkedList<>(vectors);

        Vector3D first = sortedVectors.get(0);

        for (int i = 0; i < (vectors.size() - 1); i++) {
            Vector3D vector = sortedVectors.get(0);
            Collections.sort(sortedVectors, (a, b) -> a.distance(vector) < b.distance(vector) ? -1 : a.distance(vector) == b.distance(vector) ? 0 : 1);
            borders.add(new Edge(sortedVectors.get(0), sortedVectors.get(1), 0.05));
            sortedVectors.remove(0);
        }
        borders.add(new Edge(sortedVectors.get(0), first, 0.05));

        return borders;
    }

    public String toString() {
        String out = "\n";
        for (Edge edge : edges) {
            out += edge.toString() + "\n";
        }
        return out;
    }

    private void calculatePlane() {
        plane = new Plane(vectors.get(0), vectors.get(1), vectors.get(2), 0.05);
        normal = plane.getNormal();
    }

    public double distanceToPoint(Vector3D vector3D) {
        //  1. Calculate point distance to plane and the nearest point on plane
        final Vector3D intersection = plane.intersection(new Line(normal, normal.add(vector3D), 0.05));

        //  2. Check if point is inside the polygon
        //      2.1 if Yes: return result
        if (isInPolygon(intersection)) return Math.abs(plane.getOffset(vector3D));

        //  3. Calculate distances to all edges and return shortest
        double minDistance = 1e99;
        for (Edge edge : edges) {
            if (Math.abs(edge.edgeDistance(vector3D)) <= minDistance) minDistance = Math.abs(edge.edgeDistance(vector3D));
        }
        return minDistance;
    }

    public boolean isInPolygon(Vector3D vector3D) {
        if (vector3D == null) return false;

        Line lineToCheck = calculateLineToCheck(vector3D);
        Double abscissa = lineToCheck.getAbscissa(vector3D);

        int crossCount = 0;
        List<Vector3D> intersections =  new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.isOnEdge(vector3D)) return true;

            if (edge.intersects(lineToCheck)) {
                Double intersectionAbscissa = lineToCheck.getAbscissa(lineToCheck.intersection(edge));
                Vector3D intersectionOnEdge = lineToCheck.intersection(edge);

                if (intersectionAbscissa > abscissa && !listContains(intersections, intersectionOnEdge)) {
                    intersections.add(intersectionOnEdge);
                    crossCount++;
                }
            }
        }
        if (crossCount % 2 == 0) return false;
        else return true;

    }

    private Line calculateLineToCheck(Vector3D vector3D) {
        Vector3D edge1 = vectors.get(1).subtract(vectors.get(0));
        Vector3D edge2 = vectors.get(2).subtract(vectors.get(0));
        return new Line(vector3D, vector3D.add(edge1).add(edge2), 0.05);
    }

    private boolean listContains(final List<Vector3D> list, final Vector3D value) {
        for (Vector3D valueInList : list) {
            if (valueInList.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
