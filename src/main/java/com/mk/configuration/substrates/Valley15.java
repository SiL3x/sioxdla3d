package com.mk.configuration.substrates;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.List;

public class Valley15 implements SubstrateInterface {

    private final List<List<Vector3D>> substrate =
            Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0, 0, 630),
                            new Vector3D(245, 0, 630),
                            new Vector3D(0, 789, 630)
                    ),
                    Arrays.asList(
                            new Vector3D(245, 0, 630),
                            new Vector3D(245, 789, 630),
                            new Vector3D(0, 789, 630)
                    ),
                    Arrays.asList(
                            new Vector3D(245, 0, 630),
                            new Vector3D(395, 0, 670),
                            new Vector3D(245, 789, 630)
                    ),
                    Arrays.asList(
                            new Vector3D(395, 0, 670),
                            new Vector3D(395, 789, 670),
                            new Vector3D(245, 789, 630)
                    ),
                    Arrays.asList(
                            new Vector3D(395, 0, 670),
                            new Vector3D(545, 0, 630),
                            new Vector3D(395, 789, 670)
                    ),
                    Arrays.asList(
                            new Vector3D(545, 0, 630),
                            new Vector3D(545, 789, 630),
                            new Vector3D(395, 789, 670)
                    ),
                    Arrays.asList(
                            new Vector3D(545, 0, 630),
                            new Vector3D(789, 0, 630),
                            new Vector3D(545, 789, 630)
                    ),
                    Arrays.asList(
                            new Vector3D(789, 0, 630),
                            new Vector3D(789, 789, 630),
                            new Vector3D(545, 789, 630)
                    )
            );

    public List<List<Vector3D>> getSubstrate() {
        return substrate;
    }
}
