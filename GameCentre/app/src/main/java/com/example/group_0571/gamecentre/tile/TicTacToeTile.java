package com.example.group_0571.gamecentre.tile;

import android.content.Context;

/**
 * A Tile in a TicTacToe game.
 */
public class TicTacToeTile extends DrawableTile {
    /**
     * The value of the tile.
     */
    private int value;

    /**
     * Drawable resource ids for tiles used in TicTacToe.
     */
    private int[] resources = new int[3];

    /**
     * Initialize a TicTacToeTile with a Context and an id.
     *
     * @param context the Context from which the TicTacToeTile is being constructed
     * @param id      the background id
     */
    public TicTacToeTile(Context context, int id) {
        super(context, id);
        value = id;
        background = getDrawableResource(context, id);
        resources[0] = getDrawableResource(context, TicTacToeTile.BLANK_TILE);
        resources[1] = getDrawableResource(context, 1);
        resources[2] = getDrawableResource(context, 2);
    }

    /**
     * Obtains the drawable resource corresponding to the background id.
     *
     * @param context      the Context from which the DrawableTile is being constructed
     * @param backgroundId the background id
     * @return the drawable resource
     */
    @Override
    public int getDrawableResource(Context context, int backgroundId) {
        String identifier = "tile_";
        if (backgroundId == 1) {
            identifier += "x";
        } else if (backgroundId == 2) {
            identifier += "o";
        } else {
            identifier += "blank";
        }
        return context.getResources().getIdentifier(identifier, "drawable", context.getPackageName());
    }

    /**
     * Get the value of the value variable indicating the player.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of the value variable and change the background of the tile.
     *
     * @param value new value of value variable
     */
    public void setValue(int value) {
        this.value = value;
        if (value == 1 || value == 2) {
            background = resources[value];
        } else {
            background = resources[0];
        }
    }
}
