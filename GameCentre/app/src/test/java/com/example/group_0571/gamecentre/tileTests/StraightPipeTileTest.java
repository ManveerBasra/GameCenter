package com.example.group_0571.gamecentre.tileTests;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.tile.PipeTile;
import com.example.group_0571.gamecentre.tile.StraightPipeTile;

import org.junit.Assert;
import org.junit.Test;

public class StraightPipeTileTest {
    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * a StraightPipeTile for use in tests
     */
    private PipeTile straightPipeTile = new StraightPipeTile(context);

    /**
     * test the correctness of getBackground()
     */
    @Test
    public void testGetBackGround() {
        PipeTile bentPipe1 = new StraightPipeTile(context, R.drawable.horizontal_pipe);
        Assert.assertEquals(bentPipe1.getBackground(), R.drawable.horizontal_pipe);
    }

    /**
     * test the correctness of testUpdateLinkableFaces() in BentPipeTile
     */
    @Test
    public void testUpdateLinkableFaces() {
        if (straightPipeTile.getBackground() == R.drawable.horizontal_pipe) {
            assert (straightPipeTile.getLinkableFaces().contains("left") && straightPipeTile
                    .getLinkableFaces().contains("right"));
        } else {
            assert (straightPipeTile.getLinkableFaces().contains("bottom") && straightPipeTile
                    .getLinkableFaces().contains("top"));
        }
    }

    /**
     * test the correctness of getBackground() in BentPipeTile
     */
    @Test
    public void testGetLinkableFaces() {
        PipeTile straightPipe1 = new StraightPipeTile(context, R.drawable.horizontal_pipe);
        assert (straightPipe1.getLinkableFaces().contains("left") &
                straightPipe1.getLinkableFaces().contains("right"));
        assert (straightPipe1.getLinkableFaces().size() == 2);
    }

    /**
     * test the correctness of getBackground() in BentPipeTile
     */
    @Test
    public void testRotate() {
        int originalImage = straightPipeTile.getBackground();
        straightPipeTile.rotate();
        if (originalImage == R.drawable.horizontal_pipe) {
            assert (straightPipeTile.getBackground() == R.drawable.vertical_pipe);
        } else {
            assert (straightPipeTile.getBackground() == R.drawable.horizontal_pipe);
        }
    }
}
