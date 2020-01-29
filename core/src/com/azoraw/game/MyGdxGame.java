package com.azoraw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.Rectangle;

public class MyGdxGame extends ApplicationAdapter {
    private static final int SECOND = 1000000000;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    public static final int GRID_HEIGHT = 5;
    public static final int GRID_WIDTH = 5;

    private long lastTime = 0L;
    private Array<Rectangle> rectangles;
    private Texture redSquare;
    private SpriteBatch batch;

    int currentWidth = 5;
    int currentHeight = 5;

    @Override
    public void create() {
        redSquare = new Texture(Gdx.files.internal("50x50Red.png"));
        batch = new SpriteBatch();
        rectangles = new Array<>();
        createGrid();
    }

    private void createGrid() {
        batch.begin();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                batch.draw(redSquare, x * RECTANGLE_WIDTH , y * RECTANGLE_HEIGHT);
            }
        }
        batch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        createGrid();

    }

    private void addRectangle() {
        Rectangle rect = createRectangle();
        rectangles.add(rect);
        lastTime = TimeUtils.nanoTime();
        currentWidth += RECTANGLE_WIDTH;
    }

    private Rectangle createRectangle() {
        Rectangle rect = new Rectangle();
        rect.x = currentWidth;
        rect.y = currentHeight;
        rect.width = RECTANGLE_WIDTH;
        rect.height = RECTANGLE_HEIGHT;
        return rect;
    }


    @Override
    public void dispose() {
    }
}
