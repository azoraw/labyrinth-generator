package com.azoraw.game;

import java.util.*;

import static com.azoraw.game.LabyrinthGenerator.*;

public class Backtracker extends Thread {

    private static final int INIT_POSITION_X = 0;
    private static final int INIT_POSITION_Y = 0;
    private final Stack<Cell> stack = new Stack<>();
    private final Random random = new Random();

    private final Cell[][] cells;

    public Backtracker(Cell[][] cells) {
        this.cells = cells;
        initGrid();
        initBacktracker();
    }

    @Override
    public void run() {
        while (!stack.isEmpty()) {
            nextStep();
            sleep();
        }
    }

    private void nextStep() {
        Cell head = popFromStack();
        List<Cell> neighbours = getNotVisitedNeighbours(head);
        if (hasNeighbour(neighbours)) {
            goToNeighbour(head, neighbours);
        }
    }

    private void sleep() {
        try {
            sleep(SLEEP_MILLISECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initBacktracker() {
        Cell initCell = cells[INIT_POSITION_X][INIT_POSITION_Y];
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

    private boolean hasNeighbour(List<Cell> neighbours) {
        return !neighbours.isEmpty();
    }

    private void goToNeighbour(Cell head, List<Cell> neighbours) {
        pushToStack(head);
        Cell chosenNeighbour = getRandomNeighbour(neighbours);
        removeWalls(head, chosenNeighbour);
        chosenNeighbour.setVisited(true);
        pushToStack(chosenNeighbour);
    }

    private void pushToStack(Cell cell) {
        stack.push(cell);
        cell.setOnStack(true);
    }

    private Cell popFromStack() {
        Cell cell = stack.pop();
        cell.setOnStack(false);
        cell.setCurrent(true);
        return cell;
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

}
