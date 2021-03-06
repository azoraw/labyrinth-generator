package com.azoraw.game.finder;

import com.azoraw.game.Cell;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.azoraw.game.LabyrinthGenerator.GRID_HEIGHT;
import static com.azoraw.game.LabyrinthGenerator.GRID_WIDTH;

public class AStarAlg extends PathFinder {

    private static final int INIT_POSITION_X = 0;
    private static final int INIT_POSITION_Y = 0;
    private final Cell goal;

    private Map<Cell, Integer> potentialCells = new HashMap<>();
    private List<Cell> visitedCells = new ArrayList<>();

    @Getter
    private Cell currentCell;

    public AStarAlg(Cell[][] cells) {
        super(cells);
        this.goal = cells[GRID_WIDTH - 1][GRID_HEIGHT - 1];
        currentCell = cells[INIT_POSITION_X][INIT_POSITION_Y];
    }

    @Override
    public void run() {
        int distanceFromBeginning = 0;
        while (isCurrentCellNotDestination()) {

            visitedCells.add(currentCell);
            potentialCells.remove(currentCell);
            List<Cell> neighbours = getNeighbours(currentCell);

            if (isNotCrossroad(currentCell)) {
                followPath(neighbours);
                continue;
            }

            addPotentialCells(distanceFromBeginning, neighbours);
            goToNextCell();
            distanceFromBeginning = potentialCells.get(currentCell);
            sleep();
        }
    }

    private void goToNextCell() {
        int bestF = Integer.MAX_VALUE;
        for (Cell potentialCell : potentialCells.keySet()) {
            int potentialBestF = potentialCells.get(potentialCell) + calculateDistanceHeuristic(potentialCell);
            if (potentialBestF < bestF) {
                currentCell = potentialCell;
                bestF = potentialBestF;
            }
        }
    }

    private void addPotentialCells(int g, List<Cell> neighbours) {
        for (Cell neighbour : neighbours) {
            if (!potentialCells.containsKey(neighbour) && !visitedCells.contains(neighbour)) {
                potentialCells.put(neighbour, g + 1);
            }
        }
    }

    private boolean isCurrentCellNotDestination() {
        return !(currentCell.getX() == goal.getX() && currentCell.getY() == goal.getY());
    }

    private boolean isNotCrossroad(Cell cell) {
        return cell.getWalls().size() == 2;
    }

    private void followPath(List<Cell> neighbours) {
        for (Cell neighbour : neighbours) {
            if (!visitedCells.contains(neighbour)) {
                currentCell = neighbour;
            }
        }
        sleep();
    }

    private int calculateDistanceHeuristic(Cell cell) {
        return goal.getX() - cell.getX() + goal.getY() - cell.getY();
    }

}
