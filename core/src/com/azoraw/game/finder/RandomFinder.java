package com.azoraw.game.finder;

import com.azoraw.game.Cell;
import com.azoraw.game.Direction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import static com.azoraw.game.LabyrinthGenerator.*;

public class RandomFinder extends Thread {

    private static final int INIT_POSITION_X = 0;
    private static final int INIT_POSITION_Y = 0;
    private final Random random = new Random();

    private final Cell[][] cells;
    @Getter
    private Cell currentCell;

    public RandomFinder(Cell[][] cells) {
        this.cells = cells;
        currentCell = cells[INIT_POSITION_X][INIT_POSITION_Y];
        currentCell.setCurrent(true);
    }

    @Override
    public void run() {
        while (!(currentCell.getX() == GRID_WIDTH - 1 && currentCell.getY() == GRID_HEIGHT - 1)) {
            Cell randomNeighbour = getRandomNeighbour(getNeighbours(currentCell));
            currentCell.setCurrent(false);
            currentCell = randomNeighbour;
            currentCell.setCurrent(true);
            sleep();

        }
    }

    private void sleep() {
        try {
            sleep(SLEEP_MILLISECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Cell> getNeighbours(Cell cell) {
        EnumSet<Direction> possibleDirection = EnumSet.allOf(Direction.class);
        possibleDirection.removeAll(cell.getWalls());

        List<Cell> neighbours = new ArrayList<>();
        possibleDirection.forEach(
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

    private Cell getRandomNeighbour(List<Cell> neighbours) {
        return neighbours.get(random.nextInt(neighbours.size()));
    }
}
