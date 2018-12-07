package com.example.group_0571.gamecentre.tileTests;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.tile.BentPipeTile;
import com.example.group_0571.gamecentre.tile.PipeTile;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class BentPipeTileTest {

    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * a BentPipeTile for use in tests
     */
    private PipeTile bentPipeTile = new BentPipeTile(context);

    /**
     * test the correctness of getBackground()
     */
    @Test
    public void testGetBackGround() {
        PipeTile bentPipe1 = new BentPipeTile(context, R.drawable.bent_pipe_1);
        Assert.assertEquals(bentPipe1.getBackground(), R.drawable.bent_pipe_1);
    }

    /**
     * test the correctness of testUpdateLinkableFaces() in BentPipeTile
     */
    @Test
    public void testUpdateLinkableFaces() {
        if (bentPipeTile.getBackground() == R.drawable.bent_pipe_1) {
            assert (bentPipeTile.getLinkableFaces().contains("left") && bentPipeTile
                    .getLinkableFaces().contains("bottom"));
        } else if (bentPipeTile.getBackground() == R.drawable.bent_pipe_2) {
            assert (bentPipeTile.getLinkableFaces().contains("bottom") && bentPipeTile
                    .getLinkableFaces().contains("right"));
        } else if (bentPipeTile.getBackground() == R.drawable.bent_pipe_3) {
            assert (bentPipeTile.getLinkableFaces().contains("right") && bentPipeTile
                    .getLinkableFaces().contains("top"));
        } else {
            assert (bentPipeTile.getLinkableFaces().contains("top") && bentPipeTile
                    .getLinkableFaces().contains("left"));
        }
    }

    /**
     * test the correctness of getBackground() in BentPipeTile
     */
    @Test
    public void testGetLinkableFaces() {
        PipeTile bentPipe1 = new BentPipeTile(context, R.drawable.bent_pipe_1);
        assert (bentPipe1.getLinkableFaces().contains("left") &
                bentPipe1.getLinkableFaces().contains("bottom"));
        assert (bentPipe1.getLinkableFaces().size() == 2);
    }

    /**
     * test the correctness of getBackground() in BentPipeTile
     */
    @Test
    public void testRotate() {
        int originalImage = bentPipeTile.getBackground();
        bentPipeTile.rotate();
        if (originalImage == R.drawable.bent_pipe_1) {
            assert (bentPipeTile.getBackground() == R.drawable.bent_pipe_2);
        } else if (originalImage == R.drawable.bent_pipe_2) {
            assert (bentPipeTile.getBackground() == R.drawable.bent_pipe_3);
        } else if (originalImage == R.drawable.bent_pipe_3) {
            assert (bentPipeTile.getBackground() == R.drawable.bent_pipe_4);
        } else {
            assert (bentPipeTile.getBackground() == R.drawable.bent_pipe_1);
        }
    }
}
