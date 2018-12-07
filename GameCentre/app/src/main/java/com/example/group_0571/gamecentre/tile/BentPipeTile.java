package com.example.group_0571.gamecentre.tile;

import android.content.Context;

import com.example.group_0571.gamecentre.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A PipeTile representing a bent pipe segment in the pipe game
 */
public class BentPipeTile extends PipeTile {

    /**
     * The current faces of this PipeTile that can be connected to other PipeTiles(have pipes
     * coming out of them), is either "top", "bottom", "left", or "right"
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
     * Create a new BentPipeTile with a random orientation
     *
     * @param context the context from which the StraightPipeTile is being Constructed
     */
    public BentPipeTile(Context context) {
        super(R.drawable.bent_pipe_1, context);
        this.PIPE_TILE_IMAGES = new ArrayList<>(Arrays.asList
                (R.drawable.bent_pipe_1, R.drawable.bent_pipe_2, R.drawable.bent_pipe_3,
                        R.drawable.bent_pipe_4));
        this.currImageDeterminant = (int) (Math.random() * 100);
        this.currImage = PIPE_TILE_IMAGES.get((currImageDeterminant % 4));
        this.background = this.currImage;
        this.linkableFaces = new ArrayList<>();
        this.updateLinkableFaces();
    }

    /**
     * Test constructor for testing getBackground
     *
     * @param context the context from which the StraightPipeTile is being Constructed
     * @param image the image to be displayed on this BentPipeTile
     */
    public BentPipeTile(Context context, int image) {
        super(image, context);
        this.currImage = image;
        updateLinkableFaces();
        PIPE_TILE_IMAGES = new ArrayList<>();
    }

    @Override
    public ArrayList<String> getLinkableFaces() {
        return linkableFaces;
    }

    @Override
    public void rotate() {
        this.currImageDeterminant++;
        this.currImage = this.PIPE_TILE_IMAGES.get((currImageDeterminant % 4));
        updateLinkableFaces();
    }

    @Override
    public void updateLinkableFaces() {
        this.linkableFaces = new ArrayList<>();
        if (this.currImage == R.drawable.bent_pipe_1) {
            this.linkableFaces.add("left");
            this.linkableFaces.add("bottom");
        } else if (this.currImage == R.drawable.bent_pipe_2) {
            this.linkableFaces.add("bottom");
            this.linkableFaces.add("right");
        } else if (this.currImage == R.drawable.bent_pipe_3) {
            this.linkableFaces.add("right");
            this.linkableFaces.add("top");
        } else {
            this.linkableFaces.add("top");
            this.linkableFaces.add("left");
        }
    }

    @Override
    public int getBackground() {
        return this.currImage;
    }
}
