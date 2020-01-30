package com.azoraw.game;

import lombok.Getter;

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
}
