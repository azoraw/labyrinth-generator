package com.azoraw.game;

import java.util.*;

import static com.azoraw.game.MyGdxGame.GRID_HEIGHT;
import static com.azoraw.game.MyGdxGame.GRID_WIDTH;

public class Grid {

    private static Cell[][] grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
    private static Stack<Cell> stack = new Stack<>();
    private static final Random random = new Random();

    public static void main(String[] args) {
        initGrid();
        initBacktracker();

        while (!stack.isEmpty()) {
            Cell head = stack.pop();
            head.setCurrent(true);
            List<Cell> neighbours = getNotVisitedNeighbours(head);
            if (!neighbours.isEmpty()) {
                stack.push(head);
                Cell chosenNeighbour = getRandomNeighbour(neighbours);
                removeWalls(head, chosenNeighbour);
                chosenNeighbour.setVisited(true);
                stack.push(chosenNeighbour);
            }
        }
    }

    private static void removeWalls(Cell head, Cell chosenNeighbour) {
        Direction direction = head.getDirection(chosenNeighbour);
        head.removeWall(direction);
        chosenNeighbour.removeWall(direction.getOpposite());
    }

    private static Cell getRandomNeighbour(List<Cell> neighbours) {
        return neighbours.get(random.nextInt(neighbours.size()));
    }

    private static List<Cell> getNotVisitedNeighbours(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        List<Cell> neighbours = new ArrayList<>();
        addNeighbour(x - 1, y, neighbours);
        addNeighbour(x + 1, y, neighbours);
        addNeighbour(x, y - 1, neighbours);
        addNeighbour(x, y + 1, neighbours);

        return neighbours;
    }

    private static void addNeighbour(int x, int y, List<Cell> neighbours) {
        if (isInsideGrid(x, y)) {
            Cell neighbour = grid[x][y];
            if (!neighbour.isVisited()) {
                neighbours.add(neighbour);
            }
        }
    }

    private static boolean isInsideGrid(int x, int y) {
        return x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT;
    }

    private static void initBacktracker() {
        Cell initCell = grid[0][0];
        initCell.setVisited(true);
        stack.push(initCell);
    }

    private static void initGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_WIDTH; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }
}
