package com.example.group_0571.gamecentre.tile;

import android.content.Context;

import com.example.group_0571.gamecentre.R;

import java.util.ArrayList;

/**
 * A PipeTile representing a a starting or finishing pipe
 */
public class StartFinishPipeTile extends PipeTile {

    /**
     * Determines which face other pipes can link to, is either "top" or "bottom"
     */
    private String linkableFace;

    /**
     * Initialize a new StartFinishPipeTile
     *
     * @param isStartPipe true if this StartFinishPipe is a starting pipe, false if it is a
     *                    finishing pipe
     */
    public StartFinishPipeTile(Context context, boolean isStartPipe) {
        super(R.drawable.vertical_pipe_finish, context);
        if (!isStartPipe) {
            this.linkableFace = "top";
        } else {
            this.linkableFace = "bottom";
        }
    }

    @Override
    public ArrayList<String> getLinkableFaces() {
        ArrayList<String> linkableFaces = new ArrayList<>();
        linkableFaces.add(this.linkableFace);
        return linkableFaces;
    }

    @Override
    public void rotate() {
    }

    @Override
    public void updateLinkableFaces() {
    }

    @Override
    public int getBackground() {
        return R.drawable.vertical_pipe_finish;
    }
}
