package com.example.group_0571.gamecentre.pipeGame;

import android.content.Context;

import com.example.group_0571.gamecentre.tile.BentPipeTile;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;
import com.example.group_0571.gamecentre.tile.StraightPipeTile;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class PipeStateFactory {

    /**
     * The context of the activity using this SlidingTilesBoardFactory
     */
    private Context context;

    /**
     * Create a new PipeStateFactory with a context
     *
     * @param context a context for making the tile buttons
     */
    public PipeStateFactory(Context context) {
        this.context = context;
    }

    /**
     * Construct a new PipeState of the given dimensions
     *
     * @param dimensions the length and width in PipeTiles of the PipeState
     * @return a new PipeState
     */
    public PipeState makeBoard(int dimensions) {
        List<Tile> tileList = new ArrayList<>(dimensions * dimensions);
        for (int i = 0; i != dimensions; i++) {
            tileList.add(new StartFinishPipeTile(context, true));
        }
        for (int x = 0; x != dimensions * (dimensions - 2); x++) {
            int tileDeterminant = (int) (Math.random() * 100);
            if (tileDeterminant % 2 == 0) {
                tileList.add(new BentPipeTile(context));
            } else {
                tileList.add(new StraightPipeTile(context));
            }
        }
        for (int y = 0; y != dimensions; y++) {
            tileList.add(new StartFinishPipeTile(context, false));
        }
        return new PipeState(dimensions, tileList);
    }
}
