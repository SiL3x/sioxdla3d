package com.mk.configuration.substrates;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.List;

public class Plane790 {

    public List<List<Vector3D>> substrate =
            Arrays.asList(
                    Arrays.asList(
                            new Vector3D(0 , 0, 750),
                            new Vector3D(790, 0, 750),
                            new Vector3D(0, 790, 750)
                    ),
                    Arrays.asList(
                            new Vector3D(790, 0, 750),
                            new Vector3D(790, 790, 750),
                            new Vector3D(0, 790, 750)
                    )
            );


}
