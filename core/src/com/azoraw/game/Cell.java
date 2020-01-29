package com.azoraw.game;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

public class Cell {

    @Getter
    private final int x;
    @Getter
    private final int y;

    @Getter
    @Setter
    private boolean current;
    @Getter
    @Setter
    private boolean visited;

    private EnumSet<Direction> walls = EnumSet.allOf(Direction.class);

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

    @Override
    public String toString() {
        String result = "";
        if (walls.contains(Direction.LEFT)) {
            result += "|";
        } else {
            result += " ";
        }
        if (walls.contains(Direction.UP) && walls.contains(Direction.DOWN)) {
            result += "=";
        } else if (walls.contains(Direction.UP)) {
            result += "-";
        } else if (walls.contains(Direction.DOWN)) {
            result += "_";
        } else {
            result += " ";
        }
        if (walls.contains(Direction.RIGHT)) {
            result += "|";
        } else {
            result +=" ";
        }
        return result;
    }
}
