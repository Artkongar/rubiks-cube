package com.artysh.rubikscube.core;

import com.artysh.rubikscube.dto.enums.Color;
import com.artysh.rubikscube.dto.enums.RotateDirection;
import lombok.Getter;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class Kube {

    private final MainKube mainKube;
    private Integer x;
    private Integer y;
    private Integer z;
    private final Integer originalX;
    private final Integer originalY;
    private final Integer originalZ;
    // key - MainKube side, value - Kube side (always constant)
    Map<Color, Color> sides = new HashMap<>(3);

    public Kube(int x, int y, int z, MainKube mainKube) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.originalX = x;
        this.originalY = y;
        this.originalZ = z;

        this.mainKube = mainKube;

        Consumer<Color> setColorForCube = (color) -> {
            sides.put(color, color);
            mainKube.sides.get(color).add(this);
        };
        if (x == 0) {
            setColorForCube.accept(Color.GREEN);
        }
        if (x == mainKube.getSize() - 1) {
            setColorForCube.accept(Color.BLUE);
        }
        if (y == 0) {
            setColorForCube.accept(Color.WHITE);
        }
        if (y == mainKube.getSize() - 1) {
            setColorForCube.accept(Color.YELLOW);
        }
        if (z == 0) {
            setColorForCube.accept(Color.RED);
        }
        if (z == mainKube.getSize() - 1) {
            setColorForCube.accept(Color.ORANGE);
        }
    }

    public void rotate(RotateDirection direction) {
        Map<Color, Color> rotateSideRoadMap;
        if (direction.equals(RotateDirection.X_UP)) {
            int newZ = this.x;
            this.x = Math.abs(this.z - mainKube.getSize() + 1);
            this.z = newZ;

            rotateSideRoadMap = Map.of(
                    Color.RED, Color.BLUE,
                    Color.BLUE, Color.ORANGE,
                    Color.ORANGE, Color.GREEN,
                    Color.GREEN, Color.RED
            );

        } else if (direction.equals(RotateDirection.X_DOWN)) {
            int newX = this.z;
            this.z = Math.abs(this.x - mainKube.getSize() + 1);
            this.x = newX;

            rotateSideRoadMap = Map.of(
                    Color.RED, Color.GREEN,
                    Color.GREEN, Color.ORANGE,
                    Color.ORANGE, Color.BLUE,
                    Color.BLUE, Color.RED
            );

        } else if (direction.equals(RotateDirection.Y_DOWN)) {
            int newY = this.x;
            this.x = Math.abs(this.y - mainKube.getSize() + 1);
            this.y = newY;

            rotateSideRoadMap = Map.of(
                    Color.WHITE, Color.BLUE,
                    Color.BLUE, Color.YELLOW,
                    Color.YELLOW, Color.GREEN,
                    Color.GREEN, Color.WHITE
            );

        } else if (direction.equals(RotateDirection.Y_UP)) {
            int newX = this.y;
            this.y = Math.abs(this.x - mainKube.getSize() + 1);
            this.x = newX;

            rotateSideRoadMap = Map.of(
                    Color.WHITE, Color.GREEN,
                    Color.GREEN, Color.YELLOW,
                    Color.YELLOW, Color.BLUE,
                    Color.BLUE, Color.WHITE
            );

        } else if (direction.equals(RotateDirection.Z_UP)) {
            int newY = this.z;
            this.z = Math.abs(this.y - mainKube.getSize() + 1);
            this.y = newY;

            rotateSideRoadMap = Map.of(
                    Color.WHITE, Color.ORANGE,
                    Color.ORANGE, Color.YELLOW,
                    Color.YELLOW, Color.RED,
                    Color.RED, Color.WHITE
            );

        } else {
            int newZ = this.y;
            this.y = Math.abs(this.z - mainKube.getSize() + 1);
            this.z = newZ;

            rotateSideRoadMap = Map.of(
                    Color.WHITE, Color.RED,
                    Color.RED, Color.YELLOW,
                    Color.YELLOW, Color.ORANGE,
                    Color.ORANGE, Color.WHITE
            );
        }
        Map<Color, Color> newSides = sides.entrySet().stream()
                .map(entry -> {
                    Color mainCubeColor = entry.getKey();
                    Color originalColor = entry.getValue();
                    if (rotateSideRoadMap.containsKey(mainCubeColor)) {
                        return Map.entry(rotateSideRoadMap.get(mainCubeColor), originalColor);
                    }
                    return entry;
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        BiConsumer<Map<Color, Color>, Map<Color, Color>> removeFromMainCube = (originalSides, changedSides) -> {
            originalSides.keySet().stream()
                    .filter(key -> !changedSides.containsKey(key))
                    .forEach(key -> {
                        mainKube.sides.get(key).remove(this);
                    });
            changedSides.keySet().stream()
                    .filter(key -> !originalSides.containsKey(key))
                    .forEach(key -> {
                        mainKube.sides.get(key).add(this);
                    });
        };
        removeFromMainCube.accept(sides, newSides);

        sides = newSides;
    }

}
