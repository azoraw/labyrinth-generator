package com.azoraw.game.finder;

import com.azoraw.game.Cell;
import com.azoraw.game.Direction;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.azoraw.game.LabyrinthGenerator.*;

@RequiredArgsConstructor
public abstract class PathFinder extends Thread {

    protected final Cell[][] cells;

    protected void sleep() {
        try {
            sleep(SLEEP_MILLISECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        Direction.getAllReducedBy(cell.getWalls())
                .forEach(
                        direction -> {
                            int x = cell.getX();
                            int y = cell.getY();
                            switch (direction) {
                                case UP:
                                    y--;
                                    break;
                                case RIGHT:
                                    x++;
                                    break;
                                case DOWN:
                                    y++;
                                    break;
                                case LEFT:
                                    x--;
                                    break;
                            }
                            if (isInsideGrid(x, y)) {
                                neighbours.add(cells[x][y]);
                            }
                        }
                );
        return neighbours;
    }

    private boolean isInsideGrid(int x, int y) {
        return x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT;
    }

}
