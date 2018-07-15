package com.mk.utils;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static com.mk.utils.MathUtils.ndToVector;
import static com.mk.utils.MathUtils.vectorToNd;

public class MoellerHughesRotation {
    //TODO: figure out why the rotation matrix actually rotates the from to the to...

    final INDArray r; // rotation matrix

    public MoellerHughesRotation(Vector3D f, Vector3D t) {

        f = f.normalize();
        t = t.normalize();

        double c = f.dotProduct(t);
        Vector3D v = f.crossProduct(t);

        if (Math.abs(f.dotProduct(t)) <= 0.99) {
            double h = (1 - c) / v.dotProduct(v);

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
        } else {
            //TODO: check rotation matrix construction
            /*
            System.out.println("Warning: Nearly Parallel!! >> |f*t| = " + Math.abs(f.dotProduct(t)));
            Vector3D u = v.normalize();

            double c1 = 2 / u.dotProduct(u);
            double c2 = 2 / v.dotProduct(v);
            double c3 = 4 * u.dotProduct(v) / (u.dotProduct(u) * v.dotProduct(v));

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
            r = Nd4j.create(
                    new float[]{
                            1, 0, 0,
                            0, 1, 0,
                            0, 0, 1
                    }, new int[]{3, 3});
        }
    }

    public Vector3D rotate(final Vector3D vector3D) {
        return ndToVector(rotate(vectorToNd(vector3D)));
    }

    public INDArray rotate(final INDArray vector) {
        return r.mmul(vector.transpose());
    }
}
