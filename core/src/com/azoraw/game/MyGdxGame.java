package com.azoraw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    private static final int SECOND = 1000000000;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    public static final int GRID_HEIGHT = 9;
    public static final int GRID_WIDTH = 10;

    private long lastTime = 0L;
    private Texture redSquare;
    Map<Direction, Texture> wallTextures;
    Cell[][] cells;
    private SpriteBatch batch;

    int currentWidth = 5;
    int currentHeight = 5;

    @Override
    public void create() {
        createTextures();
        Grid grid = new Grid();
        grid.generateGrid();
        cells = grid.getGrid();

        batch = new SpriteBatch();
        createGrid(cells);
    }

    private void createTextures() {
        redSquare = new Texture(Gdx.files.internal("Red.png"));
        wallTextures = new HashMap<>();
        wallTextures.put(Direction.LEFT, new Texture(Gdx.files.internal("LEFT.png")));
        wallTextures.put(Direction.RIGHT, new Texture(Gdx.files.internal("RIGHT.png")));
        wallTextures.put(Direction.DOWN, new Texture(Gdx.files.internal("DOWN.png")));
        wallTextures.put(Direction.UP, new Texture(Gdx.files.internal("UP.png")));
    }

    private void createGrid(Cell[][] cells) {
        batch.begin();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                batch.draw(redSquare, x * RECTANGLE_WIDTH, y * RECTANGLE_HEIGHT);
                Cell cell = cells[x][GRID_HEIGHT - y-1];
                for (Direction wall : cell.getWalls()) {
                    batch.draw(wallTextures.get(wall), x * RECTANGLE_WIDTH, y * RECTANGLE_HEIGHT);
                }

            }
        }

        batch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        createGrid(cells);

    }

    @Override
    public void dispose() {
    }
}
