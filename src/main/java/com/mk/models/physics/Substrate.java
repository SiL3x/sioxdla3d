package com.mk.models.physics;

import com.mk.models.geometries.Polygon;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

public class Substrate {

    public INDArray values; // 2D array
    int meshSize;
    int highestPoint;
    int front;
    int spread;
    int min;
    int max;
    List<Polygon> faces;

    public Substrate(int meshSize) {
        this.meshSize = meshSize;
        front = 0;
        values = Nd4j.zeros(meshSize, meshSize);
    }

    public int getValue(final int x, final int y) {
        return values.getInt(x, y);
    }

    public void createSubstrate(final List<List<Vector3D>> verticesList) {
        faces = new ArrayList<>();

        for (List<Vector3D> corners : verticesList) {
            faces.add(new Polygon(corners));
        }

        //  create values array
        for (int x = 0; x < meshSize; x++) {
            for (int y = 0; y < meshSize; y++) {
                setZValue(x, y);
            }
        }

        max = values.minNumber().intValue();
        min = values.maxNumber().intValue();
        highestPoint = max;
        spread = min - max;

        //  check if all mesh sides are safe, by checking if all values[x, y] are set
        if (max == 0) System.out.println("!!! ERROR: Substrate not completely covered by polygons");
    }

    private void setZValue(final int x, final int y) {

        final Line line = new Line(new Vector3D(x, y, 0), new Vector3D(x, y, 1), 0.05);
        Vector3D intersection;

        for (Polygon polygon : faces) {
            intersection = polygon.plane.intersection(line);

            if (polygon.isInPolygon(intersection)) {
                values.putScalar(x, y, (int) Math.round(intersection.getZ()));
                break;
            }
        }
    }


    public int getHighestPoint() {
        return highestPoint;
    }

    public String toString() {
        String outString = "";
        for (Polygon polygon : faces) {
            outString += polygon + "\n";
        }
        return outString;
    }
}
