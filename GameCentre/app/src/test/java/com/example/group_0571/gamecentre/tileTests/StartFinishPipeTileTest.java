package com.example.group_0571.gamecentre.tileTests;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.tile.PipeTile;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Used to test StartFinishPipeTile
 */
public class StartFinishPipeTileTest {

    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * PipeTile to be tested
     */
    private PipeTile pipeTile;

    /**
     * Correctly setup PipeTile
     */
    @Before
    public void setUp(){
        pipeTile = new StartFinishPipeTile(context, true);
    }

    /**
     * Ensure getLinkableFaces returns the correct list
     */
    @Test
    public void testGetLinkableFaces() {
        List<String> links = new ArrayList<>();
        links.add("bottom");
        assertEquals(links, pipeTile.getLinkableFaces());
    }

    /**
     * Ensure rotate() doesn't change anything.
     */
    @Test
    public void rotate() {
        List<String> links = new ArrayList<>();
        links.add("bottom");
        assertEquals(links, pipeTile.getLinkableFaces());
        pipeTile.rotate();
        assertEquals(links, pipeTile.getLinkableFaces());
    }

    /**
     * Ensure updateLinkableFaces doesn't change linkableFace
     */
    @Test
    public void testUpdateLinkableFaces() {
        List<String> links = new ArrayList<>();
        links.add("bottom");
        assertEquals(links, pipeTile.getLinkableFaces());
        pipeTile.updateLinkableFaces();
        assertEquals(links, pipeTile.getLinkableFaces());
    }
}