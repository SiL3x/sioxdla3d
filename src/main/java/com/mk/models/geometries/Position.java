package com.mk.models.geometries;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.concurrent.ThreadLocalRandom;

public class Position {
    private Vector3D vector3D;

    public Position(int x, int y, int z) {
        vector3D = new Vector3D(x, y, z);
    }

    public void move(int direction) {
        if (direction == 0) vector3D = vector3D.add(new Vector3D(1, 0, 0));
        if (direction == 1) vector3D = vector3D.add(new Vector3D(0, 1, 0));
        if (direction == 2) vector3D = vector3D.add(new Vector3D(-1, 0, 0));
        if (direction == 3) vector3D = vector3D.add(new Vector3D(0, -1, 0));
        if (direction == 4) vector3D = vector3D.add(new Vector3D(0, 0, 1));
        if (direction == 5) vector3D = vector3D.add(new Vector3D(0, 0, -1));
    }

    public void moveRnd3d(final double zDrift) {
        int[] nextMove = {0, 0, 0};

        int rnd;
        for (int i = 0; i < 3; i++) {
            rnd = ThreadLocalRandom.current().nextInt(0, 5 + 1);

            if (rnd == 0) nextMove[i] = -1;
            if (rnd == 1) nextMove[i] = 0;
            if (rnd == 2) nextMove[i] = 1;
        }

        nextMove[2] = drift(nextMove[2], zDrift);

        vector3D = vector3D.add(new Vector3D(nextMove[0], nextMove[1], nextMove[2]));
    }

    public void moveRnd3d() {
        int[] nextMove = {0, 0, 0};

        int rnd;
        for (int i = 0; i < 3; i++) {
            rnd = ThreadLocalRandom.current().nextInt(0, 5 + 1);

            if (rnd == 0) nextMove[i] = -1;
            if (rnd == 1) nextMove[i] = 0;
            if (rnd == 2) nextMove[i] = 1;
        }
        vector3D = vector3D.add(new Vector3D(nextMove[0], nextMove[1], nextMove[2]));
    }

    private int drift(int i, final double drift) {
        if (ThreadLocalRandom.current().nextInt(0, 99 + 1) < drift) {
                i = 1;
            }
        return i;
    }

    public void setX(int xNew) {
        vector3D = new Vector3D(xNew, vector3D.getY(), vector3D.getZ());
    }

    public void setY(int yNew) {
        vector3D = new Vector3D(vector3D.getX(), yNew, vector3D.getZ());
    }

    public void setZ(int zNew) {
        vector3D = new Vector3D(vector3D.getX(), vector3D.getY(), zNew);
    }

    public int getX() {
        return (int) vector3D.getX();
    }

    public int getY() {
        return (int) vector3D.getY();
    }

    public int getZ() {
        return (int) vector3D.getZ();
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public String toString() {
        return vector3D.toString();
    }
}
