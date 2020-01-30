package com.azoraw.game;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

@Getter
public class Cell {

    private final int x;
    private final int y;

    private EnumSet<Direction> walls = EnumSet.allOf(Direction.class);

    @Setter
    private boolean current;
    @Setter
    private boolean visited;
    @Setter
    private boolean isOnStack;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getDirection(Cell cell) {
        if (this.x < cell.getX()) {
            return Direction.RIGHT;
        }
        if (this.x > cell.getX()) {
            return Direction.LEFT;
        }
        if (this.y < cell.getY()) {
            return Direction.DOWN;
        }
        if (this.y > cell.getY()) {
            return Direction.UP;
        }
        return null;
    }

    public void removeWall(Direction wall) {
        walls.remove(wall);
    }
}
