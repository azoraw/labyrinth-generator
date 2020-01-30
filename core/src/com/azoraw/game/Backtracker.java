package com.azoraw.game;

import lombok.Getter;

import java.util.*;

import static com.azoraw.game.MyGdxGame.GRID_HEIGHT;
import static com.azoraw.game.MyGdxGame.GRID_WIDTH;

public class Backtracker {

    @Getter
    private Cell[][] cells;
    private Stack<Cell> stack = new Stack<>();
    private final Random random = new Random();

    public Backtracker(Cell[][] cells) {
        this.cells = cells;
        initGrid();
        initBacktracker();
    }

    public void nextStep() {
        if (!stack.isEmpty()) {
            Cell head = stack.pop();
            head.setOnStack(false);
            head.setCurrent(true);
            List<Cell> neighbours = getNotVisitedNeighbours(head);
            if (!neighbours.isEmpty()) {
                stack.push(head);
                head.setOnStack(true);
                Cell chosenNeighbour = getRandomNeighbour(neighbours);
                removeWalls(head, chosenNeighbour);
                chosenNeighbour.setVisited(true);
                stack.push(chosenNeighbour);
                chosenNeighbour.setOnStack(true);
            }
        }
    }



    private void removeWalls(Cell head, Cell chosenNeighbour) {
        Direction direction = head.getDirection(chosenNeighbour);
        head.removeWall(direction);
        chosenNeighbour.removeWall(direction.getOpposite());
    }

    private Cell getRandomNeighbour(List<Cell> neighbours) {
        return neighbours.get(random.nextInt(neighbours.size()));
    }

    private List<Cell> getNotVisitedNeighbours(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        List<Cell> neighbours = new ArrayList<>();
        addNeighbour(x - 1, y, neighbours);
        addNeighbour(x + 1, y, neighbours);
        addNeighbour(x, y - 1, neighbours);
        addNeighbour(x, y + 1, neighbours);

        return neighbours;
    }

    private void addNeighbour(int x, int y, List<Cell> neighbours) {
        if (isInsideGrid(x, y)) {
            Cell neighbour = cells[x][y];
            if (!neighbour.isVisited()) {
                neighbours.add(neighbour);
            }
        }
    }

    private boolean isInsideGrid(int x, int y) {
        return x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT;
    }

    private void initBacktracker() {
        Cell initCell = cells[0][0];
        initCell.setVisited(true);
        stack.push(initCell);
    }

    private void initGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }
}
