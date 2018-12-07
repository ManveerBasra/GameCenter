package com.example.group_0571.gamecentre.tile;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * A Tile that uses a drawable resource as its background.
 */
public class DrawableTile extends Tile {
    /**
     * The background id to find the tile image.
     */
    int background;

    /**
     * Create a DrawableTile with a background id.
     *
     * @param context      the Context from which the DrawableTile is being constructed
     * @param backgroundId the background id
     */
    public DrawableTile(Context context, int backgroundId) {
        super(backgroundId);
        background = getDrawableResource(context, backgroundId);
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Obtains the drawable resource corresponding to the background id.
     *
     * @param context      the Context from which the DrawableTile is being constructed
     * @param backgroundId the background id
     * @return the drawable resource
     */
    public int getDrawableResource(Context context, int backgroundId) {
        String identifier = "tile_";
        if (backgroundId >= 1 && backgroundId < Tile.BLANK_TILE) {
            identifier += backgroundId;
        } else {
            identifier += "blank";
        }
        return context.getResources().getIdentifier(identifier, "drawable", context.getPackageName());
    }

    @Override
    public View getButton(Context context) {
        Button button = new Button(context);
        button.setBackgroundResource(getBackground());
        return button;
    }
}
