package com.example.group_0571.gamecentre.tile;

import android.content.Context;

import com.example.group_0571.gamecentre.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A PipeTile representing a straight pipe segment in the pipe game
 */
public class StraightPipeTile extends PipeTile {

    /**
     * The current faces of this PipeTile that can be connected to other PipeTiles(have pipes
     * coming out of them), is either "top", "bottom", "left" or "right"
     */
    private ArrayList<String> linkableFaces;

    /**
     * An ArrayList containing the possible Images of for this PipeTile
     */
    private final ArrayList<Integer> PIPE_TILE_IMAGES;

    /**
     * The integer whose divisibility determines this PipeTile's current image
     */
    private int currImageDeterminant;

    /**
     * The current image displayed by this PipeTile
     */
    private int currImage;

    /**
     * Create a new StraightPipeTile with a random orientation
     * @param context the context from which the StraightPipeTile is being Constructed
     */
    public StraightPipeTile(Context context) {
        super(R.drawable.bent_pipe_1, context);
        this.PIPE_TILE_IMAGES = new ArrayList<>(Arrays.asList
                (R.drawable.vertical_pipe, R.drawable.horizontal_pipe));
        this.currImageDeterminant = (int) (Math.random() * 100);
        this.currImage = PIPE_TILE_IMAGES.get((currImageDeterminant % 2));
        this.background = this.currImage;
        this.linkableFaces = new ArrayList<>();
        this.updateLinkableFaces();
    }

    /**
     * A constructor for testing
     *
     * @param context the context from which the StraightPipeTile is being Constructed
     * @param image the image for this StraightPipeTile
     */
    public StraightPipeTile(Context context, int image) {
        super(image, context);
        this.currImage = image;
        this.PIPE_TILE_IMAGES = new ArrayList<>();
        updateLinkableFaces();
    }

    @Override
    public ArrayList<String> getLinkableFaces() {
        return linkableFaces;
    }

    @Override
    public void rotate() {
        this.currImageDeterminant++;
        this.currImage = this.PIPE_TILE_IMAGES.get((currImageDeterminant % 2));
        updateLinkableFaces();
    }

    @Override
    public void updateLinkableFaces() {
        this.linkableFaces = new ArrayList<>();
        if (this.currImage == R.drawable.horizontal_pipe) {
            this.linkableFaces.add("left");
            this.linkableFaces.add("right");
        } else {
            this.linkableFaces.add("top");
            this.linkableFaces.add("bottom");
        }
    }

    @Override
    public int getBackground() {
        return this.currImage;
    }
}
