package com.azoraw.game.finder;

import com.azoraw.game.Cell;
import com.azoraw.game.Direction;
import lombok.Getter;

import java.util.*;

import static com.azoraw.game.LabyrinthGenerator.*;

public class AStar extends Thread {

    private static final int INIT_POSITION_X = 0;
    private static final int INIT_POSITION_Y = 0;
    private final Random random = new Random();

    private final Cell[][] cells;
    @Getter
    private Cell currentCell;
    private Cell goal;

    private Map<Cell, Integer> potentialCells = new HashMap<>();
    private ArrayList<Cell> visitedCells = new ArrayList<>();

    public AStar(Cell[][] cells) {
        this.cells = cells;
        this.goal = cells[GRID_WIDTH - 1][GRID_HEIGHT - 1];
    }

    @Override
    public void run() {
        int g = 0;
        currentCell = cells[INIT_POSITION_X][INIT_POSITION_Y];
        while (!(currentCell.getX() == goal.getX() && currentCell.getY() == goal.getY())) {
            visitedCells.add(currentCell);
            potentialCells.remove(currentCell);
            List<Cell> neighbours = getNeighbours(currentCell);
            if (neighbours.size() == 2) {
                for (Cell neighbour : neighbours) {
                    if (!visitedCells.contains(neighbour)) {
                        neighbour.setVisited(true);
                        currentCell = neighbour;
                    }
                }
                sleep();
                continue;
            }
            for (Cell neighbour : neighbours) {
                if (!potentialCells.containsKey(neighbour) && !visitedCells.contains(neighbour)) {
                    int newG = g + 1;
                    potentialCells.put(neighbour, newG);
                }
            }

            int bestF = Integer.MAX_VALUE;
            for (Cell potentialCell : potentialCells.keySet()) {
                int potentialBestF = potentialCells.get(potentialCell) + calculateDistanceHeuristic(potentialCell);
                if (potentialBestF < bestF) {
                    currentCell = potentialCell;
                    bestF = potentialBestF;
                }
            }
            g = potentialCells.get(currentCell);

            sleep();
        }
    }

    private int calculateDistanceHeuristic(Cell cell) {
        return goal.getX() - cell.getX() + goal.getY() - cell.getY();
    }

    private void sleep() {
        try {
            sleep(SLEEP_MILLISECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Cell> getNeighbours(Cell cell) {
        EnumSet<Direction> possibleDirections = EnumSet.allOf(Direction.class);
        possibleDirections.removeAll(cell.getWalls());

        List<Cell> neighbours = new ArrayList<>();
        possibleDirections.forEach(
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
