package com.artysh.rubikscube.core;

import com.artysh.rubikscube.dto.Coordinates;
import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.enums.Color;
import com.artysh.rubikscube.dto.enums.RotateDirection;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainKube {

    private int size;
    private List<Kube> kubes;

    protected Map<Color, List<Kube>> sides = Arrays.stream(Color.values())
            .collect(
                    Collectors.toMap(
                            color -> color, color -> new ArrayList<>()
                    )
            );

    public MainKube(int size) {
        this.kubes = new LinkedList<>();
        this.size = size;
        init();
    }

    public boolean isCorrect() {
        Map<Color, List<CubeColorDto>> cubeColors = getAllColoredSide();
        return cubeColors.values().stream()
                .allMatch(cubes -> {
                    Color sideColor = cubes.get(0).getColor();
                    return cubes.stream()
                            .allMatch(cube -> cube.getColor().equals(sideColor));
                });
    }

    public void rotateKube(int x, int y, int z, RotateDirection direction) {
        int[] kubesToRotate = new int[(int) Math.pow(size, 2)];
        for (int index = 0; index < size * size; index++) {
            int finalIndex;
            if (RotateDirection.X_UP.equals(direction) || RotateDirection.X_DOWN.equals(direction)) {
                // all kubes by X and Z
                int yIndex = size * y;
                finalIndex = index + (index / size) * (size - 1) * size + yIndex;
            } else if (RotateDirection.Y_UP.equals(direction) || RotateDirection.Y_DOWN.equals(direction)) {
                // all kubes by Y and X
                int zIndex = z * size * size;
                finalIndex = zIndex + index;
            } else {
                // all kubes by Y and Z
                finalIndex = index * size + x;
            }
            Kube kubeToRotate = kubes.get(finalIndex);
            kubeToRotate.rotate(direction);

            kubesToRotate[index] = finalIndex;
        }

        for (int index : kubesToRotate) {
            Kube rotatedKube = kubes.get(index);
            int newIndex = rotatedKube.getZ() * (int) Math.pow(size, 2) + rotatedKube.getY() * size + rotatedKube.getX();
            Kube kubeToReplace = kubes.get(newIndex);
            kubes.set(index, kubeToReplace);
            kubes.set(newIndex, rotatedKube);
        }
    }

    public List<CubeColorDto> getColoredSide(Color side) {
        List<Kube> cubes = sides.get(side);
        List<CubeColorDto> sideCubesColors = cubes.stream()
                .sorted(new CubesForSideComparator(side))
                .map(kube -> {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(kube.getX());
                    coordinates.setY(kube.getY());
                    coordinates.setZ(kube.getZ());

                    CubeColorDto cubeColor = CubeColorDto.builder()
                            .color(kube.getSides().get(side))
                            .coordinates(coordinates)
                            .build();
                    return cubeColor;
                }).collect(Collectors.toList());

        return sideCubesColors;
    }

    public Map<Color, List<CubeColorDto>> getAllColoredSide() {
        return Arrays.stream(Color.values())
                .collect(
                        Collectors.toMap(color -> color, this::getColoredSide)
                );
    }

    public int getSize() {
        return this.size;
    }

    private void init() {
        for (int z = 0; z < size; z++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    Kube kube = new Kube(x, y, z, this);
                    kubes.add(kube);
                }
            }
        }
    }

    private class CubesForSideComparator implements Comparator<Kube> {

        private Color side;

        private CubesForSideComparator(Color side) {
            this.side = side;
        }

        @Override
        public int compare(Kube cube1, Kube cube2) {
            Function<Integer, Integer> calculateRevertedValue = (coordinate) -> (size - 1) - coordinate;

            int revertedX1 = calculateRevertedValue.apply(cube1.getX());
            int revertedZ1 = calculateRevertedValue.apply(cube1.getZ());

            int revertedX2 = calculateRevertedValue.apply(cube2.getX());
            int revertedZ2 = calculateRevertedValue.apply(cube2.getZ());

            if (Color.RED.equals(side)) {
                return (cube1.getY() * 10 + cube1.getX()) - (cube2.getY() * 10 + cube2.getX());
            } else if (Color.ORANGE.equals(side)) {
                return (cube1.getY() * 10 + revertedX1) - (cube2.getY() * 10 + revertedX2);
            } else if (Color.GREEN.equals(side)) {
                return (cube1.getY() * 10 + revertedZ1) - (cube2.getY() * 10 + revertedZ2);
            } else if (Color.BLUE.equals(side)) {
                return (cube1.getY() * 10 + cube1.getZ()) - (cube2.getY() * 10 + cube2.getZ());
            } else if (Color.WHITE.equals(side)) {
                return (revertedZ1 * 10 + cube1.getX()) - (revertedZ2 * 10 + cube2.getX());
            } else {
                return (cube1.getZ() * 10 + cube1.getX()) - (cube2.getZ() * 10 + cube2.getX());
            }
        }

    }

}
