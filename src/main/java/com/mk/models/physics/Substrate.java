package com.mk.models.physics;

import com.mk.models.geometries.Polygon;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Substrate {

    public INDArray values; // 2D array
    private INDArray substrateArray;
    int meshSizeX;
    int meshSizeY;
    int highestPoint;
    int front;
    int spread;

    List<List<Vector3D>> orientationMap;

    int min;
    int max;

    List<Polygon> faces;

    public Substrate(final int meshSize) {
       this(meshSize, meshSize);
    }

    public Substrate(final int meshSizeX, final int meshSizeY) {
        this.meshSizeX = meshSizeX;
        this.meshSizeY = meshSizeY;
        front = 0;
        values = Nd4j.zeros(meshSizeX, meshSizeX);
    }

    public int getValue(final int x, final int y) {
        return values.getInt(x, y);
    }

    public int getValueWithFront(final int x, final int y) {
        return values.getInt(x, y) - (min - front);
    }

    public void createSubstrate(final List<List<Vector3D>> verticesList) {
        createFacesList(verticesList);
        setZvalues();

        max = values.minNumber().intValue();
        min = values.maxNumber().intValue();
        front = min;
        highestPoint = max;
        spread = min - max;

        System.out.println("front = " + front + "  min = " + min + "  max = " + max + "  spread = " + spread);

        substrateArray = createSubstrateArray();

        //calculateOrientationMapUnsmoothed();
        calculateOrientationMapSquaredSmooth();

        //  check if all mesh sides are safe, by checking if all values[x, y] are set
        if (max == 0) System.out.println("!!! ERROR: Substrate not completely covered by polygons");
    }

    public void setZvalues() {
        //  create values array
        for (int x = 0; x < meshSizeX; x++) {
            for (int y = 0; y < meshSizeY; y++) {
                setZValue(x, y);
                //if (getValue(x, y) < 50) System.out.println("x, y = " + x + ", " + y + "   z = " + getValue(x, y));
            }
        }
    }

    public void createFacesList(List<List<Vector3D>> verticesList) {
        faces = new ArrayList<>();

        for (List<Vector3D> corners : verticesList) {
            faces.add(new Polygon(corners));
        }
    }

    public INDArray createSubstrateArray() {
        INDArray outArray = Nd4j.zeros(meshSizeX, meshSizeY, spread + 1);

        for (int x = 0; x < meshSizeX; x++) {
            for (int y = 0; y < meshSizeY; y++) {
                outArray.putScalar(x, y, getValue(x, y) - min + spread, 1);
            }
        }
        return outArray;
    }

    public void calculateOrientationMap() {
        orientationMap = new ArrayList<>();

        for (int x = 0; x < meshSizeX; x++) {
            List<Vector3D> orientationsY = new ArrayList<>();

            for (int y = 0; y < meshSizeY; y++) {
                double distanceSum = 0;
                Vector3D orientation = new Vector3D(0, 0, 0);
                final Vector3D point = new Vector3D(x, y, this.getValue(x, y) - 1);

                for (Polygon face : faces) {
                    distanceSum += face.distanceToPoint(point);
                }

                if (faces.size() == 1) {
                    orientation = orientation.add(faces.get(0).normal);
                } else {
                    for (Polygon face : faces) {
                        orientation = orientation
                                .add(face.normal.scalarMultiply((distanceSum - face.distanceToPoint(point)) / distanceSum)); // removed 1- face.distance...
                    }
                }
                orientationsY.add(orientation);
            }
            orientationMap.add(orientationsY);
        }
    }

    public void calculateOrientationMapSquaredSmooth() {
        orientationMap = new ArrayList<>();

        for (int x = 0; x < meshSizeX; x++) {
            List<Vector3D> orientationsY = new ArrayList<>();

            for (int y = 0; y < meshSizeY; y++) {
                double distanceSum = 0;
                Vector3D orientation = new Vector3D(0, 0, 0);
                final Vector3D point = new Vector3D(x, y, this.getValue(x, y) - 1);

                for (Polygon face : faces) {
                    distanceSum += face.distanceToPoint(point) * face.distanceToPoint(point);
                }

                if (faces.size() == 1) {
                    orientation = orientation.add(faces.get(0).normal);
                } else {
                    for (Polygon face : faces) {
                        orientation = orientation
                                .add(face.normal.scalarMultiply((distanceSum - (face.distanceToPoint(point) * face.distanceToPoint(point))) / distanceSum)); // removed 1- face.distance...
                    }
                }
                orientationsY.add(orientation);
            }
            orientationMap.add(orientationsY);
        }
    }


    public void calculateOrientationMapUnsmoothed() {
        orientationMap = new ArrayList<>();
        Vector3D orientation = new Vector3D(0, 0, 0);

        for (int x = 0; x < meshSizeX; x++) {
            List<Vector3D> orientationsY = new ArrayList<>();

            for (int y = 0; y < meshSizeY; y++) {
                Vector3D point = new Vector3D(x, y, this.getValue(x, y));

                if (faces.size() == 1) {
                    orientation = faces.get(0).normal;
                } else {
                    double distance = 9999;
                    for (Polygon face : faces) {
                        if (face.distanceToPoint(point) < distance) {
                            distance = face.distanceToPoint(point);
                            orientation = face.normal;
                        }
                    }
                }
                orientationsY.add(orientation);
            }
            orientationMap.add(orientationsY);
        }
    }

    private void setZValue(final int x, final int y) {
        final Line line = new Line(new Vector3D(x, y, 0), new Vector3D(x, y, 1), 0.05);
        ArrayList<Integer> intersections = new ArrayList<>();

        Vector3D intersection;
        for (Polygon polygon : faces) {
            intersection = polygon.plane.intersection(line);

            if (polygon.isInPolygon(intersection)) intersections.add((int) Math.round(intersection.getZ()));
        }
        values.putScalar(x, y, Collections.max(intersections));
    }

    public Vector3D getOrientation(final int x, final int y) {
        return orientationMap.get(x).get(y);
    }

    public String toString() {
        String outString = "";
        for (Polygon polygon : faces) {
            outString += polygon + "\n";
        }
        return outString;
    }

    public int getFront() {
        return front;
    }
 
    public void setFront(int front) {
        this.front = front;
    }

    public int getSpread() {
        return spread;
    }

    public INDArray getSubstrateArray() {
        return substrateArray;
    }
}
