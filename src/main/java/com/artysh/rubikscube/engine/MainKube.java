package com.artysh.rubikscube.engine;

import com.artysh.rubikscube.enums.RotateDirection;

import java.util.LinkedList;
import java.util.List;

public class MainKube {

    private int size;
    private List<Kube> kubes;

    public MainKube(int size) {
        this.kubes = new LinkedList<>();
        this.size = size;
        init();
    }

    public boolean isCorrect() {
        long mainLubeSize = kubes.stream().parallel()
                .filter(kube -> kube.getX().equals(kube.getOriginalX())
                        && kube.getY().equals(kube.getOriginalY())
                        && kube.getZ().equals(kube.getOriginalZ()))
                .filter(kube -> kube.getX() <= size && kube.getY() <= size && kube.getZ() <= size)
                .filter(kube -> kube.getSides().equals(kube.getOriginalSides()))
                .count();
        return (int) mainLubeSize == Math.pow(size, 3);
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
            kubeToRotate.calculateSidesByCoordinates(false);

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

    private void init() {
        for (int z = 0; z < size; z++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    Kube kube = new Kube(x, y, z, size);
                    kubes.add(kube);
                }
            }
        }
    }

}
