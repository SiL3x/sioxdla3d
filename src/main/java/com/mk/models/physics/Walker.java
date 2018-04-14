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
        this.border = configuration.getKernel().length/2 +1;
    }

    public Walker(Configuration configuration, int front) {
        perRow = (int) Math.round(Math.sqrt(configuration.getSectorNumber()));
        System.out.println("perRow = " + perRow + " sector no " +configuration.getSectorNumber());
        distance = (int) Math.round(configuration.getMeshSize() / perRow);

        this.configuration = configuration;
        this.spawnZ = front - configuration.getSpawnOffset();
        this.border = configuration.getKernel().length/2 +1;
        int randomX = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSize() - border);
        int randomY = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSize() - border);
        this.position = new Position(randomX, randomY, spawnZ);
    }

    public Walker(Configuration configuration, int front, int x, int y) {
        this.configuration = configuration;
        this.spawnZ = front - configuration.getSpawnOffset();
        this.border = configuration.getKernel().length/2 +1;
        this.position = new Position(x, y, spawnZ);
    }

    public void respawn(Substrate substrate) {
        //TODO: rework respawn for evenly distributed random respawn
        int randomX = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSize() - border);
        int randomY = ThreadLocalRandom.current().nextInt(border, configuration.getMeshSize() - border);
        spawnZ = substrate.getValue(randomX, randomY) - configuration.getSpawnOffset();
        this.position = new Position(randomX, randomY, spawnZ);
    }
    public void respawn(int spawnZ) {
        //TODO: rework respawn for evenly distributed random respawn

        int x = (sector % perRow) * distance + distance / 2;
        int y = (int) Math.floor((sector / perRow) * distance + distance / 2);

        int randomX = ThreadLocalRandom.current().nextInt((sector % perRow) * distance + border, (sector % perRow) * distance + distance - border);
        int randomY = ThreadLocalRandom.current().nextInt((int) (Math.floor((sector / perRow) * distance) + border), (int) (Math.floor((sector / perRow) * distance)  + distance - border));

        this.position = new Position(randomX, randomY, spawnZ);
    }

    public void moveRnd() {
        int direction = ThreadLocalRandom.current().nextInt(0, 5 + 1);
        position.move(direction);
        int meshSize = configuration.getMeshSize();

        if (position.getY() > (meshSize - border)) position.setY(meshSize - border);
        if (position.getY() < border) position.setY(border);
        if (position.getX() < border) position.setX(border);
        if (position.getX() > meshSize - border) position.setX(meshSize - border);
    }

    public Position getPosition() {
        return position;
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
