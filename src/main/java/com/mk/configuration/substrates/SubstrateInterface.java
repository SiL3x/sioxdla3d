package com.mk.configuration.substrates;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.List;

public interface SubstrateInterface {
     List<List<Vector3D>> substrate =
            Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0, 0, 0),
                            new Vector3D(0, 0, 0),
                            new Vector3D(0, 0, 0)
                    )
            );

    List<List<Vector3D>> getSubstrate();
}
