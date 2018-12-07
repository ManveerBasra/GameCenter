package com.example.group_0571.gamecentre.slidingTiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Utility class to download and represent images in Sliding Tiles.
 * Implements the singleton design pattern.
 */
public class SlidingTilesImage {
    /**
     * Constants to represent different types of images that can be used for tiles.
     */
    public static final int DEFAULT_TILES = 0;
    public static final int IMAGE_FROM_GALLERY = 4;
    public static final int IMAGE_FROM_URL = 5;

    /**
     * The singleton instance.
     */
    private static SlidingTilesImage instance = null;

    /**
     * Prevent instantiation of this class from outside this class.
     */
    private SlidingTilesImage() {
    }

    /**
     * Get the singleton instance of this class.
     *
     * @return an instance of SlidingTilesImage
     */
    public static SlidingTilesImage getInstance() {
        if (instance == null) {
            instance = new SlidingTilesImage();
        }
        return instance;
    }

    /**
     * Download an image from a url.
     *
     * @param url the URL that contains the image
     * @throws IOException if there is an error reading the URL
     */
    public Bitmap downloadImage(URL url) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = inputStream.read(buffer);
        while (n != -1) {
            outputStream.write(buffer, 0, n);
            n = inputStream.read(buffer);
        }
        byte[] response = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(response, 0, response.length);
    }

    /**
     * Encode a Bitmap into Base 64.
     *
     * @return the encoded image
     */
    public String encodeImage(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * Decode a Base 64 encoded Bitmap.
     *
     * @param encodedImage the Base 64 encoded Bitmap
     * @return the Bitmap
     */
    Bitmap decodeImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, 0);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
