package com.azoraw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    int width = 50;
    int height = 50;
    int space = 60;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                shapeRenderer.rect(x * space, y * space, width, height);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
