package com.azoraw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

import static com.azoraw.game.LabyrinthGenerator.CELL_HEIGHT;
import static com.azoraw.game.LabyrinthGenerator.CELL_WIDTH;

public class TextureFactory {

    public static Map<Direction, Texture> createWallTextures() {
        Map<Direction, Texture> wallTextures = new HashMap<>();

        wallTextures.put(Direction.LEFT, new Texture(createScaledCell(Gdx.files.internal("LEFT.png"))));
        wallTextures.put(Direction.RIGHT, new Texture(createScaledCell(Gdx.files.internal("RIGHT.png"))));
        wallTextures.put(Direction.DOWN, new Texture(createScaledCell(Gdx.files.internal("DOWN.png"))));
        wallTextures.put(Direction.UP, new Texture(createScaledCell(Gdx.files.internal("UP.png"))));

        return wallTextures;
    }

    public static Map<Color, Texture> createBackgroundTextures() {
        Map<Color, Texture> backgroundTextures = new HashMap<>();

        backgroundTextures.put(Color.RED, new Texture(createScaledCell(Gdx.files.internal("Red.png"))));
        backgroundTextures.put(Color.GREEN, new Texture(createScaledCell(Gdx.files.internal("Green.png"))));
        backgroundTextures.put(Color.BLUE, new Texture(createScaledCell(Gdx.files.internal("Blue.png"))));
        backgroundTextures.put(Color.WHITE, new Texture(createScaledCell2(Gdx.files.internal("White.png"))));

        return backgroundTextures;
    }

    private static Pixmap createScaledCell2(FileHandle internal) {
        Pixmap pixmap = new Pixmap(internal);
        Pixmap scaledPixmap = new Pixmap(CELL_WIDTH, CELL_HEIGHT, pixmap.getFormat());
        scaledPixmap.drawPixmap(pixmap,
                2, 2, pixmap.getWidth(), pixmap.getHeight(),
                2, 2, scaledPixmap.getWidth()/2, scaledPixmap.getHeight()/2
        );
        return scaledPixmap;
    }

    private static Pixmap createScaledCell(FileHandle file) {
        Pixmap pixmap = new Pixmap(file);
        Pixmap scaledPixmap = new Pixmap(CELL_WIDTH, CELL_HEIGHT, pixmap.getFormat());
        scaledPixmap.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight()
        );
        return scaledPixmap;
    }
}
