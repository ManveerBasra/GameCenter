package com.example.group_0571.gamecentre.tile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A Tile that uses a Bitmap as its background.
 */
public class BitmapTile extends Tile {
    /**
     * The byte array representing the Bitmap.
     * Stored as a byte[] because Bitmap is not serializable.
     */
    private byte[] bitmapBytes;

    /**
     * Create a BitmapTile with an id and bitmap.
     *
     * @param id     the id of this Tile
     * @param bitmap the Bitmap to use as the background
     */
    public BitmapTile(int id, Bitmap bitmap) {
        super(id);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        bitmapBytes = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the bitmap used for the background image.
     *
     * @return the bitmap
     */
    public Bitmap getBackground() {
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    @Override
    public View getButton(Context context) {
        ImageButton imageButton = new ImageButton(context);
        imageButton.setImageBitmap(getBackground());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageButton;
    }
}
