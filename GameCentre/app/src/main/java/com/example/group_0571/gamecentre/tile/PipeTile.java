package com.example.group_0571.gamecentre.tile;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A tile representing a segment of pipe in the pipe game
 */
public abstract class PipeTile extends DrawableTile {

    /**
     * The current image displayed by this PipeTile
     */
    private int currImage;

    PipeTile(int backgroundId, Context context) {
        super(context, backgroundId);
        currImage = backgroundId;
    }

    @Override
    public View getButton(Context context) {
        Button pipeButton = new Button(context);
        pipeButton.setBackgroundResource(currImage);
        return pipeButton;
    }

    /**
     * Rotate this PipeTile on the board counterclockwise
     */
    public abstract void rotate();

    /**
     * Set linkableFaces to reflect the faces that have pipes leaving on the image
     */
    public abstract void updateLinkableFaces();

    /**
     * Get  the background image for this PipeTile
     *
     * @return the int representing this tiles's background
     */
    public abstract int getBackground();

    /**
     * Get the array of linkable linkable faces for this pipe
     *
     * @return a String array of linkable faces, containing possible values "top", "bottom", "left"
     * or "right".
     */
    public abstract ArrayList<String> getLinkableFaces();
}
