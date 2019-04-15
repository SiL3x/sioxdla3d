package com.mk.configuration;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.List;

public class Config {
    public int meshSizeX;
    public int meshSizeY;
    public int meshSizeZ;
    public int meshSize;
    public int growthRatio;
    public int spawnOffset; // 15
    public int stickingProbability;
    public int seedNumber;
    public int sectorNumber;
    public int zDrift; // 4
    public int diffusionLength;
    public int stopHeight;
    public double crystallizationProbability;
    public String kernelName;
    public String substrateName;
    private float[][][] kernel3D;
    private INDArray kernel3Dnd;
    private List<List<Vector3D>> substrate;

    public void initialize() {
        setKernel3D();
        substrate = substrate();
    }

    public float[][][] kernel() { return KernelFactory.get(kernelName).getKernel3D(); }

    public void setKernel3D() {
        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel());
        int[] shape = {kernel().length, kernel().length, kernel().length};
        kernel3Dnd = Nd4j.create(flatKernel, shape, 'c');
        kernel3D = kernel();
    }

    public List<List<Vector3D>> substrate() { return SubstrateFactory.get(substrateName).getSubstrate(); }

    public int getMeshSize() { return meshSize; }

    public int getMeshSizeX() { return meshSizeX; }

    public int getMeshSizeY() { return meshSizeY; }

    public int getMeshSizeZ() { return meshSizeZ; }

    //public double getStickingDistance() { return stickingDistance; }

    public int getGrowthRatio() {
        return growthRatio;
    }

    public int getSpawnOffset() {
        return spawnOffset;
    }

    public int getStickingProbability() { return stickingProbability; }

    public float[][][] getKernel3D() { return kernel3D; }

    public INDArray getKernel3Dnd() { return kernel3Dnd; }

    public int getSeedNumber() { return seedNumber; }

    //public int getSurfaceStickDistance() { return surfaceStickDistance; }

    //public List<List<Vector3D>> getSubstrate() { return substrate; }

    public int getSectorNumber() { return sectorNumber; }

    public int getZdrift() { return zDrift; }

    public double getDiffusionLength() { return diffusionLength; }

    public int getStopHeight() { return stopHeight; }

    public double getCrystallizationProbability() { return crystallizationProbability; }

    public List<List<Vector3D>> getSubstrate() { return substrate; }

    public String toString() {
        return "MeshX: " + meshSizeX + " MeshY: " + meshSizeY;
    }
}
