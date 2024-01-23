package com.artysh.rubikscube.model;

import com.artysh.rubikscube.enums.Color;
import com.artysh.rubikscube.enums.RotateDirection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Kube {

    private Integer mainKubeSize;

    private Integer x;
    private Integer y;
    private Integer z;
    private Integer originalX;
    private Integer originalY;
    private Integer originalZ;

    List<Color> sides = new ArrayList<>(3);
    List<Color> originalSides = new ArrayList<>(3);

    public Kube(int x, int y, int z, int mainKubeSize) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.originalX = x;
        this.originalY = y;
        this.originalZ = z;
        this.mainKubeSize = mainKubeSize;

        calculateSidesByCoordinates(true);
    }

    public void rotate(RotateDirection direction) {
        if (direction.equals(RotateDirection.X_UP)) {
            int newZ = this.x;
            this.x = Math.abs(this.z - mainKubeSize + 1);
            this.z = newZ;

        } else if (direction.equals(RotateDirection.X_DOWN)) {
            int newX = this.z;
            this.z = Math.abs(this.x - mainKubeSize + 1);
            this.x = newX;

        } else if (direction.equals(RotateDirection.Y_DOWN)) {
            int newY = this.x;
            this.x = Math.abs(this.y - mainKubeSize + 1);
            this.y = newY;

        } else if (direction.equals(RotateDirection.Y_UP)) {
            int newX = this.y;
            this.y = Math.abs(this.x - mainKubeSize + 1);
            this.x = newX;

        } else if (direction.equals(RotateDirection.Z_UP)) {
            int newY = this.z;
            this.z = Math.abs(this.y - mainKubeSize + 1);
            this.y = newY;

        } else if (direction.equals(RotateDirection.Z_DOWN)) {
            int newZ = this.y;
            this.y = Math.abs(this.z - mainKubeSize + 1);
            this.z = newZ;
        }
    }

    public void calculateSidesByCoordinates(boolean isOriginal) {
        List<Color> sides;
        if (isOriginal) {
            sides = this.originalSides;
        } else {
            sides = this.sides;
            sides.clear();
        }
        if (this.x == 0) {
            sides.add(Color.GREEN);
        }
        if (this.y == 0) {
            sides.add(Color.WHITE);
        }
        if (this.z == 0) {
            sides.add(Color.RED);
        }

        if (this.x == this.mainKubeSize - 1) {
            sides.add(Color.BLUE);
        }
        if (this.y == this.mainKubeSize - 1) {
            sides.add(Color.YELLOW);
        }
        if (this.z == this.mainKubeSize - 1) {
            sides.add(Color.ORANGE);
        }

        if (isOriginal) {
            calculateSidesByCoordinates(false);
        }
    }

}
