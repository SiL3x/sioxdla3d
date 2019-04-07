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
    private final String configName; //
    private int meshSizeX; //
    private int meshSizeY; //
    private int meshSizeZ;
    private int meshSize; //
    private int growthRatio; //
    private int spawnOffset; //
    private int stickingProbability;
    private int seedNumber;
    private int sectorNumber;
    private int zDrift;
    private double diffusionLength;
    private int stopHeight;
    private double crystallizationProbability;

    private INDArray kernel3Dnd;
    private float[][][] kernel3D;
    private List<List<Vector3D>> substrate;
    private int[][] kernel;
    public ArrayList<Vector3D> substratePoints;

    private int surfaceStickDistance;
    private int exposure;
    private int moveLength;
    private double stickingDistance;
    private Position walkerStart;
    private List<Position> seedPosition;

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
                            new Vector3D(0 , 0, 110),
                            new Vector3D(99, 0, 110),
                            new Vector3D(0, 49, 160),
                            new Vector3D(99, 49, 160)
                    ),
                    Arrays.asList(
                            new Vector3D(0, 49, 160),
                            new Vector3D(99, 49, 160),
                            new Vector3D(0, 99, 110),
                            new Vector3D(99, 99, 110))
            ));

            setSeedNumber(500);
            setSpawnOffset(10);
            setGrowthRatio(30);
            setSectorNumber(32);
            setStickingProbability(6);
            setZDrift(4);
            setDiffusionLength(10);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 2, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            }
                    };
            setKernel3D(kernel);
        }

        if (name == "test2") {
            setMeshSize(100, 100, 100);

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
            setSectorNumber(64);
            setStickingProbability(3);
            setZDrift(4);
            setDiffusionLength(10);

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

        if (name == "test3") {
            setMeshSize(1000, 1000, 1000);

            setSubstrate(
                    Arrays.asList(
                            Arrays.asList(
                                    new Vector3D(0, 0, 990),
                                    new Vector3D(999, 0, 990),
                                    new Vector3D(0, 999, 990),
                                    new Vector3D(999, 999, 990)
                            )
                    )

            );

            setSeedNumber(2000);
            setSpawnOffset(15);
            setGrowthRatio(100);
            setSectorNumber(256);
            setStickingProbability(1);
            setZDrift(4);
            setDiffusionLength(100);

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


        if (name == "test4") {
            setMeshSize(1000, 1000, 1000);

            setSubstrate(
                    Arrays.asList(
                            Arrays.asList( // 1
                                    new Vector3D(0, 0, 850),
                                    new Vector3D(100, 0, 950),
                                    new Vector3D(100, 999, 950)
                            ),
                            Arrays.asList( // 2
                                    new Vector3D(100, 999, 950),
                                    new Vector3D(0, 999, 850),
                                    new Vector3D(0, 0, 850)
                            ),
                            Arrays.asList( // 3
                                    new Vector3D(100, 999, 950),
                                    new Vector3D(100, 0, 950),
                                    new Vector3D(200, 0, 850)
                            ),
                            Arrays.asList( // 4
                                    new Vector3D(200, 0, 850),
                                    new Vector3D(200, 999, 850),
                                    new Vector3D(100, 999, 950)
                            ),
                            Arrays.asList( // 5
                                    new Vector3D(100, 999, 950),
                                    new Vector3D(100, 0, 950),
                                    new Vector3D(200, 999, 850)
                            ),
                            Arrays.asList( // 6
                                    new Vector3D(200, 999, 850),
                                    new Vector3D(200, 0, 850),
                                    new Vector3D(500, 0, 850)
                            ),
                            Arrays.asList( // 7
                                    new Vector3D(500, 0, 850),
                                    new Vector3D(500, 999, 850),
                                    new Vector3D(200, 999, 850)
                            ),
                            Arrays.asList( // 8
                                    new Vector3D(500, 999, 850),
                                    new Vector3D(999, 999, 950),
                                    new Vector3D(999, 0, 950)
                            ),
                            Arrays.asList( // 9
                                    new Vector3D(999, 0, 950),
                                    new Vector3D(500, 0, 850),
                                    new Vector3D(500, 999, 850)
                            )
                    )
            );

            setSeedNumber(2000);
            setSpawnOffset(15);
            setGrowthRatio(100);
            setSectorNumber(256);
            setStickingProbability(1);
            setZDrift(4);
            setDiffusionLength(25);
            setStopHeight(650);

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

        if (name == "test5") {

            setSubstrate(
                    Arrays.asList(
                            Arrays.asList( // 1
                                    new Vector3D(0, 0, 725),
                                    new Vector3D(150, 0,650),
                                    new Vector3D(0, 789, 725)
                            ),
                            Arrays.asList( // 2
                                    new Vector3D(0, 789, 725),
                                    new Vector3D(150, 0,650),
                                    new Vector3D(150, 789, 650)
                            ),
                            Arrays.asList( // 3
                                    new Vector3D(150, 0,650),
                                    new Vector3D(250, 0,  750),
                                    new Vector3D(150, 789, 650)
                            ),
                            Arrays.asList( // 4
                                    new Vector3D(150, 789, 650),
                                    new Vector3D(250, 0,  750),
                                    new Vector3D(250, 789, 750)
                            ),
                            Arrays.asList( // 5
                                    new Vector3D(250, 0,  750),
                                    new Vector3D(350, 0, 650),
                                    new Vector3D(250, 789, 750)
                            ),
                            Arrays.asList( // 6
                                    new Vector3D(250, 789, 750),
                                    new Vector3D(350, 0, 650),
                                    new Vector3D(350, 789, 650)
                            ),
                            Arrays.asList( // 7
                                    new Vector3D(350, 0, 650),
                                    new Vector3D(550, 0, 650),
                                    new Vector3D(350, 789, 650)
                            ),
                            Arrays.asList( // 8
                                    new Vector3D(350, 789, 650),
                                    new Vector3D(550, 0, 650),
                                    new Vector3D(550, 789, 650)
                            ),
                            Arrays.asList( // 9
                                    new Vector3D(550, 0, 650),
                                    new Vector3D(670, 394, 500),
                                    new Vector3D(550, 789, 650)
                            ),
                            Arrays.asList( // 10
                                    new Vector3D(550, 0, 650),
                                    new Vector3D(789, 0, 650),
                                    new Vector3D(670, 394, 500)
                            ),
                            Arrays.asList( // 11
                                    new Vector3D(789, 0, 650),
                                    new Vector3D(789, 789, 650),
                                    new Vector3D(670, 394, 500)
                            ),
                            Arrays.asList( // 12
                                    new Vector3D(550, 789, 650),
                                    new Vector3D(670, 394, 500),
                                    new Vector3D(789, 789, 650)
                            )
                    )
            );

            setMeshSize(790, 790, 790);
            setSeedNumber(2000);
            setSpawnOffset(100); // 15
            setGrowthRatio(500);
            setSectorNumber(256);
            setStickingProbability(1);
            setZDrift(4); // 4
            setDiffusionLength(10);
            setStopHeight(250);
            setCrystallizationProbability(1e-1);

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

        if (name == "1000_large_test") {
            setMeshSize(1000, 1000, 1000);

            setSubstrate(Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0 , 0, 990),
                            new Vector3D(999, 0, 990),
                            new Vector3D(0, 999, 990)
                    ),
                    Arrays.asList(
                            new Vector3D(0 , 999, 990),
                            new Vector3D(999, 999, 990),
                            new Vector3D(999, 0, 990)
                    )
            ));


            setSeedNumber(5000);
            setSpawnOffset(20);
            setGrowthRatio(500);
            setSectorNumber(64);
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



        if (name == "realistic") {
            setMeshSize(1000, 1000, 1000);

            setSubstrate(Arrays.asList(
                    Arrays.asList( // 1
                            new Vector3D(0, 0, 990),
                            new Vector3D(130, 237, 910),
                            new Vector3D(0, 232, 910)
                    ),
                    Arrays.asList( // 2
                            new Vector3D(0, 0, 990),
                            new Vector3D(300, 0, 990),
                            new Vector3D(130, 237, 910)
                    ),
                    Arrays.asList( // 3
                            new Vector3D(300, 0, 990),
                            new Vector3D(748, 194, 926),
                            new Vector3D(130, 237, 910)
                    ),
                    Arrays.asList( // 4
                            new Vector3D(300, 0, 990),
                            new Vector3D(835, 0, 990),
                            new Vector3D(748, 194, 926)
                    ),
                    Arrays.asList( // 5
                            new Vector3D(835, 0, 990),
                            new Vector3D(891, 148, 960),
                            new Vector3D(748, 194, 926)
                    ),
                    Arrays.asList( // 6
                            new Vector3D(835, 0, 990),
                            new Vector3D(872, 0, 980),
                            new Vector3D(891, 148, 960)
                    ),
                    Arrays.asList( // 7
                            new Vector3D(872, 0, 980),
                            new Vector3D(1000, 0, 990),
                            new Vector3D(891, 148, 960)
                    ),
                    Arrays.asList( // 8
                            new Vector3D(1000, 0, 990),
                            new Vector3D(1000, 164,953),
                            new Vector3D(891, 148, 960)
                    ),
                    Arrays.asList( // 9
                            new Vector3D(0, 232, 910),
                            new Vector3D(83, 375, 888),
                            new Vector3D(0, 375, 866)
                    ),
                    Arrays.asList( // 10
                            new Vector3D(0, 232, 910),
                            new Vector3D(130, 237, 910),
                            new Vector3D(83, 375, 888)
                    ),
                    Arrays.asList( // 11 //TODO: Check for wiered z value
                            new Vector3D(130, 237, 910),
                            new Vector3D(280, 572, 746),
                            new Vector3D(83, 375, 888)
                    ),
                    Arrays.asList( // 12
                            new Vector3D(130, 237, 910),
                            new Vector3D(748, 194, 926),
                            new Vector3D(280, 572, 746)
                    ),
                    Arrays.asList( // 13
                            new Vector3D(748, 194, 926),
                            new Vector3D(764, 636, 810),
                            new Vector3D(280, 572, 746)
                    ),
                    Arrays.asList( // 14
                            new Vector3D(748, 194, 926),
                            new Vector3D(813, 295, 886),
                            new Vector3D(745, 342, 910)
                    ),
                    Arrays.asList( // 15
                            new Vector3D(748, 194, 926),
                            new Vector3D(891, 148, 960),
                            new Vector3D(813, 295, 886)
                    ),
                    Arrays.asList( // 16
                            new Vector3D(891, 148, 960),
                            new Vector3D(1000, 439, 880),
                            new Vector3D(813, 295, 886)
                    ),
                    Arrays.asList( // 17
                            new Vector3D(891, 148, 960),
                            new Vector3D(1000, 164, 953),
                            new Vector3D(1000, 439, 880)
                    ),
                    Arrays.asList( // 18
                            new Vector3D(813, 295, 886),
                            new Vector3D(1000, 439, 880),
                            new Vector3D(745, 342, 910)
                    ),
                    Arrays.asList( // 19
                            new Vector3D(745, 342, 910),
                            new Vector3D(1000, 439, 880),
                            new Vector3D(764, 636, 810)
                    ),
                    Arrays.asList( // 20
                            new Vector3D(0, 375, 866),
                            new Vector3D(83, 375, 888),
                            new Vector3D(0, 484, 956)
                    ),
                    Arrays.asList( // 21 //TODO: Check why z below 700
                            new Vector3D(83, 375, 888),
                            new Vector3D(228, 622, 735),
                            new Vector3D(0, 484, 956)
                    ),
                    Arrays.asList( // 22
                            new Vector3D(83, 375, 888),
                            new Vector3D(280, 572, 746),
                            new Vector3D(228, 622, 735)
                    ),
                    Arrays.asList( // 23
                            new Vector3D(280, 572, 746),
                            new Vector3D(615, 732, 780),
                            new Vector3D(228, 622, 735)
                    ),
                    Arrays.asList( // 24
                            new Vector3D(280, 572, 746),
                            new Vector3D(764, 636, 810),
                            new Vector3D(615, 732, 780)
                    ),
                    Arrays.asList( // 25
                            new Vector3D(764, 636, 810),
                            new Vector3D(782, 748, 793),
                            new Vector3D(615, 732, 780)
                    ),
                    Arrays.asList( // 26
                            new Vector3D(764, 636, 810),
                            new Vector3D(1000, 708, 790),
                            new Vector3D(782, 748, 793)
                    ),
                    Arrays.asList( // 27
                            new Vector3D(1000, 439, 880),
                            new Vector3D(1000, 708, 790),
                            new Vector3D(764, 636, 810)
                    ),
                    Arrays.asList( // 28
                            new Vector3D(0, 484, 956),
                            new Vector3D(209, 719, 775),
                            new Vector3D(0, 767, 725)
                    ),
                    Arrays.asList( // 29
                            new Vector3D(0, 484, 956),
                            new Vector3D(228, 622, 735),
                            new Vector3D(209, 719, 775)
                    ),
                    Arrays.asList( // 30
                            new Vector3D(228, 622, 735),
                            new Vector3D(622, 866, 770),
                            new Vector3D(209, 719, 775)
                    ),
                    Arrays.asList( // 31
                            new Vector3D(228, 622, 735),
                            new Vector3D(615, 732, 780),
                            new Vector3D(622, 866, 770)
                    ),
                    Arrays.asList( // 32
                            new Vector3D(615, 732, 780),
                            new Vector3D(782, 748, 793),
                            new Vector3D(622, 866, 770)
                    ),
                    Arrays.asList( // 33
                            new Vector3D(782, 748, 793),
                            new Vector3D(730, 849, 828),
                            new Vector3D(622, 866, 770)
                    ),
                    Arrays.asList( // 34
                            new Vector3D(782, 748, 793),
                            new Vector3D(810, 831, 819),
                            new Vector3D(730, 849, 828)
                    ),
                    Arrays.asList( // 35
                            new Vector3D(782, 748, 793),
                            new Vector3D(1000, 816, 787),
                            new Vector3D(810, 831, 819)
                    ),
                    Arrays.asList( // 36
                            new Vector3D(782, 748, 793),
                            new Vector3D(1000, 708, 790),
                            new Vector3D(1000, 816, 787)
                    ),
                    Arrays.asList( // 37
                            new Vector3D(0, 767, 725),
                            new Vector3D(209, 719, 775),
                            new Vector3D(116, 787, 718)
                    ),
                    Arrays.asList( // 38
                            new Vector3D(0, 767, 725),
                            new Vector3D(116, 787, 718),
                            new Vector3D(0, 1000, 760)
                    ),
                    Arrays.asList( // 39
                            new Vector3D(116, 787, 718),
                            new Vector3D(364, 1000, 743),
                            new Vector3D(0, 1000, 760)
                    ),
                    Arrays.asList( // 40
                            new Vector3D(116, 787, 718),
                            new Vector3D(209, 719, 775),
                            new Vector3D(364, 1000, 743)
                    ),
                    Arrays.asList( // 41
                            new Vector3D(209, 719, 775),
                            new Vector3D(686, 966, 840),
                            new Vector3D(364, 1000, 743)
                    ),
                    Arrays.asList( // 42
                            new Vector3D(209, 719, 775),
                            new Vector3D(622, 866, 770),
                            new Vector3D(686, 966, 840)
                    ),
                    Arrays.asList( // 43
                            new Vector3D(364, 1000, 743),
                            new Vector3D(686, 966, 840),
                            new Vector3D(694, 1000, 824)
                    ),
                    Arrays.asList( // 44
                            new Vector3D(622, 866, 770),
                            new Vector3D(730, 849, 828),
                            new Vector3D(686, 966, 840)
                    ),
                    Arrays.asList( // 45
                            new Vector3D(730, 849, 828),
                            new Vector3D(810, 831, 819),
                            new Vector3D(686, 966, 840)
                    ),
                    Arrays.asList( // 46
                            new Vector3D(810, 831, 819),
                            new Vector3D(1000, 893, 810),
                            new Vector3D(686, 966, 840)
                    ),
                    Arrays.asList( // 47
                            new Vector3D(686, 966, 840),
                            new Vector3D(1000, 893, 810),
                            new Vector3D(1000, 1000, 790)
                    ),
                    Arrays.asList( // 48
                            new Vector3D(686, 966, 840),
                            new Vector3D(1000, 1000, 790),
                            new Vector3D(694, 1000, 824)
                    ),
                    Arrays.asList( // 49
                            new Vector3D(810, 831, 819),
                            new Vector3D(1000, 816, 787),
                            new Vector3D(1000, 893, 810)
                    )
            ));


            setSeedNumber(500);
            setSpawnOffset(10);
            setGrowthRatio(30);
            setSectorNumber(32);
            setStickingProbability(6);
            setZDrift(4);
            setDiffusionLength(10);

            float[][][] kernel =
                    {
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 2, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 2, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 6, 6},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0}
                            },
                            {
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0},
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

    public int getStopHeight() {
        return stopHeight;
    }

    public void setStopHeight(int stopHeight) {
        this.stopHeight = stopHeight;
    }

    public double getCrystallizationProbability() {
        return crystallizationProbability;
    }

    public void setCrystallizationProbability(double crystallizationProbability) {
        this.crystallizationProbability = crystallizationProbability;
    }
}
