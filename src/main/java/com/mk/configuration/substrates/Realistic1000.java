package com.mk.configuration.substrates;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.List;

public class Realistic1000 implements SubstrateInterface{

    private final List<List<Vector3D>> substrate =
            Arrays.asList(
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
                            new Vector3D(1000, 164, 953),
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
            );

    @Override
    public List<List<Vector3D>> getSubstrate() {
        return substrate;
    }
}
