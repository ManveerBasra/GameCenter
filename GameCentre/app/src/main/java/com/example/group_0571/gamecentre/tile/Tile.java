package com.example.group_0571.gamecentre.tile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.Serializable;

/**
 * A Tile in a grid.
 */
public abstract class Tile implements Comparable<Tile>, Serializable {
    /**
     * Represents the Sliding Tiles blank tile id
     */
    public static final int BLANK_TILE = 25;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Create a Tile with an id.
     *
     * @param id the id
     */
    Tile(int id) {
        this.id = id;
    }

    /**
     * Create a new Tile
     */
    protected Tile() {
    }

    /**
     * Getter for tile id
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the Button that represents this Tile.
     *
     * @param context the Context to display Button in
     * @return the Button
     */
    public abstract View getButton(Context context);

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
