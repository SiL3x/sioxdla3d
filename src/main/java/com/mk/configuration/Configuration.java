package com.mk.configuration;

import com.mk.models.geometries.Position;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {
    private final String configName;
    private int meshSizeX;
    private int meshSizeY;
    private int meshResolution;
    private int meshSize;
    private List<Position> seedPosition;
    private Position walkerStart;
    private int moveLength;
    private double stickingDistance;

    private int growthRatio;

    private int spawnOffset;
    private int stickingProbability;
    private int exposure;
    private int[][] kernel;
    public ArrayList<Vector3D> substratePoints;
    private int seedNumber;
    private int surfaceStickDistance;
    private List<List<Vector3D>> substrate;
    private int sectorNumber;
    private float[][][] kernel3D;


    private INDArray kernel3Dnd;
    private int zDrift;
    private int meshSizeZ;
    private double diffusionLength;

    public Configuration(String configName) {
        this.configName = configName;
        substratePoints = new ArrayList<Vector3D>();
        load(configName);
    }

    public void setMeshSize(int i) {
        meshSizeX = i;
        meshSizeY = i;
        meshSize = i;
    }

    public void setMeshSize(final int x, final int y, final int z) {
        meshSizeX = x;
        meshSizeY = y;
        meshSizeZ = z;
    }

    public void load(String name) {
        //TODO: implement loading config json from name

        if (name == "test") {
            setMeshSize(100, 100, 170);

            setSubstrate(Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0 , 0, 130),
                            new Vector3D(99, 0, 130),
                            new Vector3D(0, 49, 160),
                            new Vector3D(99, 49, 160)
                    ),
                    Arrays.asList(
                            new Vector3D(0, 49, 160),
                            new Vector3D(99, 49, 160),
                            new Vector3D(0, 99, 130),
                            new Vector3D(99, 99, 130))
            ));

            setSeedNumber(500);
            setSpawnOffset(30);
            setGrowthRatio(30);
            setSectorNumber(32);
            setStickingProbability(7);
            setZDrift(4);
            setDiffusionLength(10);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 0, 4, 4},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 1, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            }
                    };
            setKernel3D(kernel);
        }

        if (name == "test2") {
            setMeshSize(100);

            setSubstrate(
                    Arrays.asList(
                            Arrays.asList(
                                    new Vector3D(0, 0, 70),
                                    new Vector3D(99, 0, 70),
                                    new Vector3D(0, 32, 90),
                                    new Vector3D(99, 32, 90)
                            ),
                            Arrays.asList(
                                    new Vector3D(0, 32, 90),
                                    new Vector3D(99, 32, 90),
                                    new Vector3D(0, 65, 70),
                                    new Vector3D(99, 65, 70)
                            ),
                            Arrays.asList(
                                    new Vector3D(0, 65, 70),
                                    new Vector3D(99, 65, 70),
                                    new Vector3D(0, 99, 90),
                                    new Vector3D(99, 99, 90)
                            )
                    )
            );

            setSeedNumber(100);
            setSpawnOffset(15);
            setGrowthRatio(20);
            setSectorNumber(16);
            setStickingProbability(3);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 6, 4},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 1, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            }
                    };
            setKernel3D(kernel);
        }

        if (name == "large_test") {
            setMeshSize(150);

            setSubstrate(Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0 , 0, 125),
                            new Vector3D(149, 0, 125),
                            new Vector3D(0, 24, 110),
                            new Vector3D(149, 24, 110)
                    ),
                    Arrays.asList(
                            new Vector3D(0 , 24, 110),
                            new Vector3D(149, 24, 110),
                            new Vector3D(0, 74, 140),
                            new Vector3D(149, 74, 140)
                    ),
                    Arrays.asList(
                            new Vector3D(0, 74, 140),
                            new Vector3D(149, 74, 140),
                            new Vector3D(0, 124, 110),
                            new Vector3D(149, 124, 110)
                    ),
                    Arrays.asList(
                            new Vector3D(0 , 124, 110),
                            new Vector3D(149, 124, 110),
                            new Vector3D(0, 149, 125),
                            new Vector3D(149, 149, 125)
                    )
            ));


            setSeedNumber(100);
            setSpawnOffset(10);
            setGrowthRatio(45);
            setSectorNumber(16);
            setStickingProbability(2);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 4, 2},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            }
                    };
            setKernel3D(kernel);
        }

        if (name == "very_large_test") {
            setMeshSize(300);

            setSubstrate(Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0 , 0, 216),
                            new Vector3D(299, 0, 216),
                            new Vector3D(0, 74, 290),
                            new Vector3D(299, 74, 290)
                    ),
                    Arrays.asList(
                            new Vector3D(0 , 74, 290),
                            new Vector3D(299, 74, 290),
                            new Vector3D(0, 149, 216),
                            new Vector3D(299, 149, 216)
                    ),
                    Arrays.asList(
                            new Vector3D(0, 149, 216),
                            new Vector3D(299, 149, 216),
                            new Vector3D(0, 224, 290),
                            new Vector3D(299, 224, 290)
                    ),
                    Arrays.asList(
                            new Vector3D(0 , 224, 290),
                            new Vector3D(299, 224, 290),
                            new Vector3D(0, 299, 216),
                            new Vector3D(299, 299, 216)
                    )
            ));


            setSeedNumber(900);
            setSpawnOffset(10);
            setGrowthRatio(180);
            setSectorNumber(32);
            setStickingProbability(2);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 4, 2},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 1, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            }
                    };
            setKernel3D(kernel);
        }

        System.out.println("    - configuration loaded");
    }

    private void setDiffusionLength(int diffusionLength) {
        this.diffusionLength = diffusionLength;
    }

    private void setZDrift(int i) {
        zDrift = i;
    }


    public void setMeshResolution(int i) {
        meshResolution = 1;
    }

    public int getMeshSize() {
        return meshSize;
    }

    public int getMeshSizeX() {
        return meshSizeX;
    }

    public int getMeshSizeY() {
        return meshSizeY;
    }

    public int getMeshSizeZ() {
        return meshSizeZ;
    }

    public List<Position> getSeedPosition() {
        return seedPosition;
    }

    public void setSeedPosition(List<Position> seedPosition) {
        this.seedPosition = seedPosition;
    }

    public Position getWalkerStart() {
        return walkerStart;
    }

    public void setWalkerStart(Position walkerStart) {
        this.walkerStart = walkerStart;
    }

    public int getMoveLength() {
        return moveLength;
    }

    public double getStickingDistance() {
        return stickingDistance;
    }

    public void setStickingDistance(double distance) {
        this.stickingDistance = distance;
    }

    public void setMoveLength(int moveLength) {
        this.moveLength = moveLength;
    }

    public int getGrowthRatio() {
        return growthRatio;
    }

    public void setGrowthRatio(int growthRatio) {
        this.growthRatio = growthRatio;
    }

    public int getSpawnOffset() {
        return spawnOffset;
    }

    public void setSpawnOffset(int spawnOffset) {
        this.spawnOffset = spawnOffset;
    }

    public int getStickingProbability() {
        return stickingProbability;
    }

    public void setStickingProbability(int stickingProbability) {
        this.stickingProbability = stickingProbability;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public void setKernel(int[][] kernel) {
        this.kernel = kernel;
    }

    public void setKernel3D(float[][][] kernel) {
        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        int[] shape = {kernel.length, kernel.length, kernel.length};
        this.kernel3Dnd = Nd4j.create(flatKernel, shape, 'c');
        this.kernel3D = kernel;
    }

    public int[][] getKernel() {
        return kernel;
    }

    public float[][][] getKernel3D() {
        return kernel3D;
    }

    public INDArray getKernel3Dnd() {
        return kernel3Dnd;
    }

    public void setSeedNumber(int seedNumber) {
        this.seedNumber = seedNumber;
    }

    public int getSeedNumber() {
        return seedNumber;
    }

    public int getSurfaceStickDistance() {
        return surfaceStickDistance;
    }

    public void setSurfaceStickDistance(int surfaceStickDistance) {
        this.surfaceStickDistance = surfaceStickDistance;
    }

    public void setSubstrate(List<List<Vector3D>> substrateVertices) {
        this.substrate = substrateVertices;
    }

    public List<List<Vector3D>> getSubstrate() {
        return substrate;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(int sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    public int getZdrift() {
        return zDrift;
    }

    public double getDiffusionLength() {
        return diffusionLength;
    }
}
