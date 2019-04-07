package com.mk.configuration.substrates;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.List;

public class Test5_790 implements SubstrateInterface{

    private final List<List<Vector3D>> substrate =
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
            );

    @Override
    public List<List<Vector3D>> getSubstrate() {
        return substrate;
    }
}
