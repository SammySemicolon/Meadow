package com.smellysleepy.meadow.common.worldgen.structure.grove;

import net.minecraft.core.*;

public interface MeadowGroveShape {

    MeadowGroveShape CIRCLE = (center, point, size) -> center.distSqr(point);
    MeadowGroveShape HEART = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double z = (point.getZ() - center.getZ());
        double a = Math.pow(x * x + z * z - 1, 3);
        double b = x * x * z * z * z;
        return Math.pow(a - b, 2);
    };
    MeadowGroveShape HALF_MOON = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double distToOuterCircle = Math.pow(Math.sqrt(x * x + y * y) - size * 0.5, 2);
        double distToInnerCircle = Math.pow(Math.sqrt((x + size * 0.25) * (x + size * 0.25) + y * y) - (size * 0.3), 2);

        return Math.min(distToOuterCircle, -distToInnerCircle);
    };
    MeadowGroveShape YIN_YANG = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double v = (x + size * 0.25) * (x + size * 0.25);
        double topHalf = Math.pow(Math.sqrt(v + y * y) - size * 0.25, 2);
        double v1 = (x - size * 0.25) * (x - size * 0.25);
        double bottomHalf = Math.pow(Math.sqrt(v1 + y * y) - size * 0.25, 2);

        double smallDotTop = Math.pow(Math.sqrt(v + (y - size * 0.25) * (y - size * 0.25)) - size * 0.08, 2);
        double smallDotBottom = Math.pow(Math.sqrt(v1 + (y + size * 0.25) * (y + size * 0.25)) - size * 0.08, 2);

        return Math.min(Math.min(topHalf, bottomHalf), Math.min(smallDotTop, smallDotBottom));
    };
    MeadowGroveShape WAVY_STAR = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double angle = Math.atan2(y, x);
        double distToCenter = Math.sqrt(x * x + y * y);

        double r = size * (1 + 0.3 * Math.sin(6 * angle));

        return Math.pow(distToCenter - r, 2);
    };
    MeadowGroveShape WEIRD_TRIANGLE = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double angle = Math.atan2(y, x);
        double distToEdge = Math.abs(Math.cos(angle) * Math.sin(3 * angle));

        return Math.pow(distToEdge * size - Math.sqrt(x * x + y * y), 2);
    };
    MeadowGroveShape FLOWER = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double angle = Math.atan2(y, x);
        double distToCenter = Math.sqrt(x * x + y * y);

        double r = size * (1 + 0.5 * Math.sin(8 * angle));

        return Math.pow(distToCenter - r, 2);
    };
    MeadowGroveShape SPIKES = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double angle = Math.atan2(y, x);
        double distToCenter = Math.sqrt(x * x + y * y);

        double r = size * (1 + 0.4 * Math.sin(5 * angle));

        return Math.pow(distToCenter - r, 2);
    };
    MeadowGroveShape SPIRAL = (center, point, size) -> {
        double x = (point.getX() - center.getX());
        double y = (point.getZ() - center.getZ());

        double angle = Math.atan2(y, x);
        double distToCenter = Math.sqrt(x * x + y * y);

        double r = size * angle / (2 * Math.PI);

        return Math.pow(distToCenter - r, 2);
    };

    double distSqr(Vec3i center, Vec3i point, double size);

}