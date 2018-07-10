package com.mk.utils;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static com.mk.utils.MathUtils.ndToVector;
import static com.mk.utils.MathUtils.vectorToNd;

public class MoellerHughesRotation {

    private final Vector3D u;
    private final Vector3D v;
    private final double c1;
    private final double c2;
    private final double c3;
    final INDArray r; // rotation matrix
    private final double c;
    private final double h;

    public MoellerHughesRotation(final Vector3D f, final Vector3D t) {

        this.c = f.dotProduct(t);
        this.v = f.crossProduct(t);
        this.h = (1 - c) / v.dotProduct(v);

        float r11 = (float) (c + h * v.getX() * v.getX());
        float r12 = (float) (h * v.getX() * v.getY() - v.getZ());
        float r13 = (float) (h * v.getX() * v.getZ() + v.getY());

        float r21 = (float) (h * v.getX() * v.getY() + v.getZ());
        float r22 = (float) (c + h * v.getY() * v.getY());
        float r23 = (float) (h * v.getY() * v.getZ() - v.getX());

        float r31 = (float) (h * v.getX() * v.getZ() - v.getY());
        float r32 = (float) (h * v.getY() * v.getZ() + v.getX());
        float r33 = (float) (c + h * v.getZ() * v.getZ());

        r = Nd4j.create(
                new float[]{
                        r11, r12, r13,
                        r21, r22, r23,
                        r31, r32, r33
                }, new int[]{3, 3});


        // If to f and t are too parallel
        this.u = v.normalize();

        System.out.println("v = " + v);
        System.out.println("u = " + u);

        this.c1 = 2 / u.dotProduct(u);
        this.c2 = 2 / v.dotProduct(v);
        this.c3 = 4 * u.dotProduct(v) / (u.dotProduct(u) * v.dotProduct(v));
        /*
        float r11 = (float) (1 - c1*u.getX()*u.getX() - c2*v.getX()*v.getX() + c3*v.getX()*u.getX());
        float r12 = (float) (0 - c1*u.getX()*u.getY() - c2*v.getX()*v.getY() + c3*v.getX()*u.getY());
        float r13 = (float) (0 - c1*u.getX()*u.getZ() - c2*v.getX()*v.getZ() + c3*v.getX()*u.getZ());

        float r21 = (float) (0 - c1*u.getY()*u.getX() - c2*v.getY()*v.getX() + c3*v.getY()*u.getX());
        float r22 = (float) (1 - c1*u.getY()*u.getY() - c2*v.getY()*v.getY() + c3*v.getY()*u.getY());
        float r23 = (float) (0 - c1*u.getY()*u.getZ() - c2*v.getY()*v.getZ() + c3*v.getY()*u.getZ());

        float r31 = (float) (0 - c1*u.getZ()*u.getX() - c2*v.getZ()*v.getX() + c3*v.getZ()*u.getX());
        float r32 = (float) (0 - c1*u.getZ()*u.getY() - c2*v.getZ()*v.getY() + c3*v.getZ()*u.getY());
        float r33 = (float) (1 - c1*u.getZ()*u.getZ() - c2*v.getZ()*v.getZ() + c3*v.getZ()*u.getZ());

        r = Nd4j.create(
                new float[]{
                r11, r12, r13,
                r21, r22, r23,
                r31, r32, r33
                }, new int[]{3, 3});

        */

        System.out.println("r = \n" + r);
    }

    public Vector3D rotate(final Vector3D vector3D) {
        return ndToVector(rotate(vectorToNd(vector3D)));
    }

    public INDArray rotate(final INDArray vector) {
        return vector.mmul(r);
    }
}
