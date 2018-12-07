package com.example.group_0571.gamecentre.slidingTiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.group_0571.gamecentre.tile.BitmapTile;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.tile.Tile;

/**
 * The class for making a solvable Sliding Tiles board of the specified dimensions
 */
public class SlidingTilesBoardFactory {

    /**
     * The context of the activity using this SlidingTilesBoardFactory
     */
    private Context context;

    /**
     * The PreferenceHandler to handle interactions between SharedPreferences
     */
    private PreferenceHandler preferenceHandler;

    /**
     * Intiialize the context, and saved user preferences for the board
     *
     * @param context           that the board is displayed on
     * @param sharedPreferences the user preferences for the board
     */
    public SlidingTilesBoardFactory(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.preferenceHandler = new PreferenceHandler(sharedPreferences, context);
    }

    /**
     * Create a board of the given dimensions.
     *
     * @param dimension the width and height of the board in tiles
     */
    public SlidingTilesState makeBoard(int dimension) {
        List<Tile> tiles = new ArrayList<>();
        int type = preferenceHandler.getImageType();
        if (type == SlidingTilesImage.DEFAULT_TILES) {
            for (int tileNum = 1; tileNum < dimension * dimension; tileNum++) {
                tiles.add(new DrawableTile(context, tileNum));
            }
            tiles.add(new DrawableTile(context, DrawableTile.BLANK_TILE));
        } else {
            makeImageBoard(type, dimension, tiles);
        }
        do {
            Collections.shuffle(tiles); // shuffle while it is not solvable
        }
        while (!isSolvable(tiles));
        return new SlidingTilesState(dimension, tiles, preferenceHandler.getUndoLimit());
    }

    /**
     * Returns whether or not the board is solvable given a List of Tiles based on number of inversions
     * This method is adopted from:
     * http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     *
     * @param tiles the List of tiles to find inversions of
     * @return true if the current List of tiles represents a solvable board
     */
    public boolean isSolvable(List<Tile> tiles) {
        int inversions = getInversions(tiles);
        // if the board size is even (n^2 even -> n is even)
        if (tiles.size() % 2 == 0) {
            int blankIdRow = (int) Math.sqrt(tiles.size()) - getBlankIdRow(tiles) - 1;
            if (blankIdRow % 2 == 0) {
                return inversions % 2 == 0;
            }
            return inversions % 2 == 1;
        }
        // otherwise board is odd
        return inversions % 2 == 0;
    }

    /**
     * Gets the index of the blank tile's row in List<Tile>
     *
     * @param tiles the tiles List to search for a blank tile. This must exist.
     * @return an integer specifying the row index at which the blank tile is located.
     */
    private int getBlankIdRow(List<Tile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getId() == Tile.BLANK_TILE) {
                return i / ((int) Math.sqrt(tiles.size()));
            }
        }
        throw new NoSuchElementException("Could not find blank tile in Sliding Tiles!");
    }

    /**
     * Obtains the inversions in a List of tiles using a brute force method, O(n^2)
     *
     * @param tiles the List of tiles to obtain number of inversions from
     * @return the number of inversions (int)
     */
    private int getInversions(List<Tile> tiles) {
        int inversions = 0;
        for (int x = 0; x < tiles.size() - 1; x++) {
            for (int y = x + 1; y < tiles.size(); y++) {
                int xId = tiles.get(x).getId();
                int yId = tiles.get(y).getId();
                if (xId != Tile.BLANK_TILE && yId != Tile.BLANK_TILE &&
                        xId > yId) {
                    inversions += 1;
                }
            }
        }
        return inversions;
    }

    /**
     * Create a board of the given dimensions with BitmapTile objects.
     *
     * @param type      the type of image
     * @param dimension the width and height of the board
     * @param tiles     the List of tiles to which Tiles will be added
     */
    private void makeImageBoard(int type, int dimension, List<Tile> tiles) {
        Bitmap image = getImage(type);
        int subImageSide = Math.min(image.getHeight(), image.getWidth()) / dimension;
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                Bitmap subImage = Bitmap.createBitmap(image, x * subImageSide, y
                                * subImageSide,
                        subImageSide, subImageSide);
                tiles.add(new BitmapTile(dimension * y + x + 1, subImage));
            }
        }
        // add the blank tile
        tiles.remove(dimension * dimension - 1);
        tiles.add(new BitmapTile(Tile.BLANK_TILE, BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tile_blank)));
    }

    /**
     * Retrieve an image from file, gallery, or URL
     *
     * @param type the id pertaining to image type
     * @return the image
     */
    private Bitmap getImage(int type) {
        int[] res = {R.drawable.image_one, R.drawable.image_two, R.drawable.image_three};
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_one);
        if (type == SlidingTilesImage.IMAGE_FROM_GALLERY || type == SlidingTilesImage.IMAGE_FROM_URL) {
            String encodedImage = preferenceHandler.getEncodedImage();
            if (!encodedImage.equals(""))
                image = SlidingTilesImage.getInstance().decodeImage(encodedImage);
        } else {
            image = BitmapFactory.decodeResource(context.getResources(), res[type - 1]);
        }
        return image;
    }
}
