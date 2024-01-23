package com.artysh.ribikscube;

import com.artysh.ribikscube.enums.RotateDirection;
import com.artysh.ribikscube.model.MainKube;

public class Main {

    public static void main(String[] args) {
        int size = 3;
        MainKube kube = new MainKube(size);

        for (int i = 0; i < 6; i++) {
            kube.rotateKube(size - 1, 0 ,0, RotateDirection.Z_UP);
            kube.rotateKube(0, 0 ,0, RotateDirection.X_DOWN);
            kube.rotateKube(size - 1, 0 ,0, RotateDirection.Z_DOWN);
            kube.rotateKube(0, 0 ,0, RotateDirection.X_UP);
            System.out.println(kube.isCorrect());
        }

    }
}
