package com.mk.models.physics;

import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;
import com.mk.models.physics.Substrate;

import java.util.concurrent.ThreadLocalRandom;

public class Walker {
    private int border=1;
    private Position position;
    private Configuration configuration;
    private int spawnZ;

    private int sector = 0 ;
    int perRow;
    int distance;


    public Walker(Configuration configuration) {
        this.configuration = configuration;
        this.position = configuration.getWalkerStart();
        this.border = configuration.getKernel3D().length/2 +2;
    }

    public Walker(Configuration configuration, int front) {
        perRow = (int) Math.round(Math.sqrt(configuration.getSectorNumber()));
        System.out.println("perRow = " + perRow + " sector no " +configuration.getSectorNumber());
        distance = (int) Math.round(configuration.getMeshSizeX() / perRow);

        this.configuration = configuration;
        this.spawnZ = front - configuration.getSpawnOffset();
        this.border = configuration.getKernel().length/2 +2;
        int randomX = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeX() - border);
        int randomY = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeY() - border);
        this.position = new Position(randomX, randomY, spawnZ);
    }

    public Walker(Configuration configuration, int front, int sector) {
        perRow = (int) Math.round(Math.sqrt(configuration.getSectorNumber()));
        distance = Math.round(configuration.getMeshSizeX() / perRow);

        this.configuration = configuration;
        this.spawnZ = front - configuration.getSpawnOffset();
        this.border = configuration.getKernel3D().length/2 +2;
        int randomX = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeX() - border);
        int randomY = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeY() - border);
        this.position = new Position(randomX, randomY, spawnZ);
        this.sector = sector;
    }

    public Walker(Configuration configuration, int front, int x, int y) {
        this.configuration = configuration;
        this.spawnZ = front - configuration.getSpawnOffset();
        this.border = configuration.getKernel3D().length/2 +2;
        this.position = new Position(x, y, spawnZ);
    }

    public void respawn(Substrate substrate) {
        //TODO: rework respawn for evenly distributed random respawn
        int randomX = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeX() - border);
        int randomY = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSizeY() - border);
        spawnZ = substrate.getValue(randomX, randomY) - configuration.getSpawnOffset();
        this.position = new Position(randomX, randomY, spawnZ);
    }
    public void respawn(int spawnZ) {
        //TODO: rework respawn for evenly distributed random respawn

        //int x = (sector % perRow) * distance + distance / 2;
        //int y = (int) Math.floor((sector / perRow) * distance + distance / 2);
        //System.out.println("sector = " + sector + "  perRow = " + perRow + "  border = " + border + "  distance = " + distance);
        //System.out.println("spawn y-boundaries = rnd(" + (Math.floor((sector / perRow) * distance) + border) + ", " + (Math.floor((sector / perRow) * distance)  + distance - border) + ")");

        int randomX = ThreadLocalRandom.current().nextInt((sector % perRow) * distance + border, (sector % perRow) * distance + distance - border);
        int randomY = ThreadLocalRandom.current().nextInt((int) (Math.floor((sector / perRow) * distance) + border), (int) (Math.floor((sector / perRow) * distance)  + distance - border));

        this.position = new Position(randomX, randomY, spawnZ);
    }

    public void moveRnd(final int zDrift) {
        position.moveRnd3d(zDrift);
        int meshSize = configuration.getMeshSizeX();

        if (position.getY() > (meshSize - border)) position.setY(meshSize - border);
        if (position.getY() < border) position.setY(border);
        if (position.getX() < border) position.setX(border);
        if (position.getX() > meshSize - border) position.setX(meshSize - border);
    }

    public void moveRnd() {
        //int direction = ThreadLocalRandom.current().nextInt(0, 5 + 1);
        //position.move(direction);
        position.moveRnd3d();
        int meshSize = configuration.getMeshSizeX();

        if (position.getY() > (meshSize - border)) position.setY(meshSize - border);
        if (position.getY() < border) position.setY(border);
        if (position.getX() < border) position.setX(border);
        if (position.getX() > meshSize - border) position.setX(meshSize - border);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final int x, final int y, final int z) {
        position = new Position(x, y, z);
    }

    public void nextSector() {
        sector++;
        if (sector >= configuration.getSectorNumber()) {
            sector = sector % configuration.getSectorNumber();
        }
    }

    public int getSector() {
        return sector;
    }
}
