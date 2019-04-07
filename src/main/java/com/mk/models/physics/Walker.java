package com.mk.models.physics;

import com.mk.configuration.Config;
import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;

import java.util.concurrent.ThreadLocalRandom;

public class Walker {
    private int border=1;
    private Position position;
    final private Config configuration;
    private int spawnZ;

    private int sector = 0 ;
    int perRow;
    int distance;


    public Walker(Config configuration, int front, int sector) {
        //TODO: Rework the sector spawning
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

    public Walker(Config configuration, int front, int x, int y) {
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
        //TODO: now sectors at the boundaries have a higher probality density

        int minX = (sector % perRow) * distance;
        int maxX = (sector % perRow) * distance + distance;
        int minY = (int) (Math.floor((sector / perRow) * distance));
        int maxY = (int) (Math.floor((sector / perRow) * distance)  + distance);

        if (minX < border) minX = border;
        if (maxX > configuration.getMeshSizeX() - border) maxX = configuration.getMeshSizeX() - border;
        if (minY < border) minY = border;
        if (maxY > configuration.getMeshSizeY() - border) maxY = configuration.getMeshSizeY() - border;

        int randomX = ThreadLocalRandom.current().nextInt(minX, maxX);
        int randomY = ThreadLocalRandom.current().nextInt(minY, maxY);

        this.position = new Position(randomX, randomY, spawnZ);
    }

    public void moveRnd(final int zDrift) {
        position.moveRnd3d(zDrift);
        int meshSize = configuration.getMeshSizeX();

        if (position.getY() < border) position.setY(border);
        if (position.getX() < border) position.setX(border);
        if (position.getX() > meshSize - border) position.setX(meshSize - border);
        if (position.getY() > meshSize - border) position.setY(meshSize - border);
    }

    public void moveRnd() {
        //TODO: clean up overloaded method
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
}
