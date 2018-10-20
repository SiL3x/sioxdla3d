package com.mk.models.geometries;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Edge extends Line {
    final private Vector3D p1;
    final private Vector3D p2;
    private double larger;
    private double smaller;

    public Edge(Vector3D p1, Vector3D p2, double tolerance) throws MathIllegalArgumentException {
        super(p1, p2, tolerance);
        this.p1 = p1;
        this.p2 = p2;

        double abscissaP1 = this.getAbscissa(p1);
        double abscissaP2 = this.getAbscissa(p2);

        if (abscissaP1 > abscissaP2) {
            larger = abscissaP1;
            smaller = abscissaP2;
        } else {
            larger = abscissaP2;
            smaller = abscissaP1;
        }
    }

    public double edgeDistance(Vector3D point) {

        double abscissa = this.getAbscissa(point);

        if (abscissa <= larger && abscissa >= smaller) return this.distance(point);
        else if (abscissa >= larger) return this.pointAt(larger).distance(point);
        else if (abscissa <= smaller) return this.pointAt(smaller).distance(point);
        return 0;
    }

    public boolean intersects(Line line) {
        if (this.intersection(line) == null) return false;
        if (this.edgeDistance(this.intersection(line)) > 1e-6) { //1e-6
            return false;
        }
        return true;
    }

    public boolean isOnEdge(final Vector3D point) {
        if (this.contains(point) &&
                this.getAbscissa(point) <= larger &&
                this.getAbscissa(point) >= smaller) return true;
        else return false;
    }

    public String toString() {
        return (p1 + " -> " + p2);
    }
}
