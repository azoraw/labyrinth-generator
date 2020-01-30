package com.azoraw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {

    private static final int CELL_WIDTH = 10;
    private static final int CELL_HEIGHT = 10;
    public static final int GRID_HEIGHT = 40;
    public static final int GRID_WIDTH = 40;

    private Map<Direction, Texture> wallTextures;
    private Map<Color, Texture> backgroundTextures;
    private Cell[][] cells;
    private Backtracker backtracker;
    private SpriteBatch batch;

    @Override
    public void create() {
        createTextures();
        cells = new Cell[GRID_WIDTH][GRID_HEIGHT];
        backtracker = new Backtracker(cells);
        batch = new SpriteBatch();
        drawGrid();
    }

    @Override
    public void render() {
        clearScreen();

        backtracker.nextStep();
        drawGrid();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void createTextures() {
        backgroundTextures = new HashMap<>();
        backgroundTextures.put(Color.RED, new Texture(createScaledCell(Gdx.files.internal("Red.png"))));
        backgroundTextures.put(Color.GREEN, new Texture(createScaledCell(Gdx.files.internal("Green.png"))));
        backgroundTextures.put(Color.BLUE, new Texture(createScaledCell(Gdx.files.internal("Blue.png"))));

        wallTextures = new HashMap<>();
        wallTextures.put(Direction.LEFT, new Texture(createScaledCell(Gdx.files.internal("LEFT.png"))));
        wallTextures.put(Direction.RIGHT, new Texture(createScaledCell(Gdx.files.internal("RIGHT.png"))));
        wallTextures.put(Direction.DOWN, new Texture(createScaledCell(Gdx.files.internal("DOWN.png"))));
        wallTextures.put(Direction.UP, new Texture(createScaledCell(Gdx.files.internal("UP.png"))));
    }

    private Pixmap createScaledCell(FileHandle file) {
        Pixmap pixmap = new Pixmap(file);
        Pixmap scaledPixmap = new Pixmap(CELL_WIDTH, CELL_HEIGHT, pixmap.getFormat());
        scaledPixmap.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight()
        );
        return scaledPixmap;
    }

    private void drawGrid() {
        batch.begin();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                Cell cell = cells[x][GRID_HEIGHT - y - 1];
                Texture backgroundColor = getBackgroundTexture(cell);
                batch.draw(backgroundColor, x * CELL_WIDTH, y * CELL_HEIGHT);
                drawWalls(x, y, cell);
            }
        }
        batch.end();
    }

    private Texture getBackgroundTexture(Cell cell) {
        Color backgroundColor = getBackgroundColor(cell);
        return backgroundTextures.get(backgroundColor);
    }

    private Color getBackgroundColor(Cell cell) {
        Color backgroundColor;
        if (cell.isVisited()) {
            if (cell.isOnStack()) {
                backgroundColor = Color.BLUE;
            } else {
                backgroundColor = Color.GREEN;
            }
        } else {
            backgroundColor = Color.RED;
        }
        return backgroundColor;
    }

    private void drawWalls(int x, int y, Cell cell) {
        for (Direction wall : cell.getWalls()) {
            batch.draw(wallTextures.get(wall), x * CELL_WIDTH, y * CELL_HEIGHT);
        }
    }

}
