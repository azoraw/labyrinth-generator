package com.azoraw.game;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@Getter
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    static {
        UP.opposite = DOWN;
        RIGHT.opposite = LEFT;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
    }

    private Direction opposite;

    public static Set<Direction> getAllReducedBy(EnumSet<Direction> directions) {
        Set<Direction> reversed = EnumSet.allOf(Direction.class);
        reversed.removeAll(directions);

        return reversed;
    }
}
