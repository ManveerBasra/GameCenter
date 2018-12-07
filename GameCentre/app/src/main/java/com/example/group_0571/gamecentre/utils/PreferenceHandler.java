package com.example.group_0571.gamecentre.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesImage;

/**
 * Class to handle interactions with SharedPreferences
 */
public class PreferenceHandler {

    /**
     * Constant keys for saving/loading objects from preferences
     */
    private final String UNDO_LIMIT_KEY = "undo_limit";
    private final String IMAGE_TYPE_KEY = "image type";
    private final String ENCODED_KEY = "encoded image";
    private final String IMAGE_URL_KEY = "image url";

    /**
     * Reference to SharedPreferences object.
     */
    private SharedPreferences preferences;

    /**
     * Reference to relevant context.
     */
    private Context context;

    /**
     * Basic constructor, implements dependency injection
     *
     * @param preferences SharedPreferences obj used to handle user preferences
     * @param context     App context used to get resources
     */
    public PreferenceHandler(SharedPreferences preferences, Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    /**
     * Gets the saved undo limit from SharedPreferences
     *
     * @return int undo limit
     */
    public int getUndoLimit() {
        int defaultUndoLimit = context.getResources().getInteger(R.integer.undo_limit);
        return preferences.getInt(UNDO_LIMIT_KEY, defaultUndoLimit);
    }

    /**
     * Save the undo limit in SharedPreferences.
     *
     * @param newUndoLimit the new undo limit to save
     */
    public void setUndoLimit(int newUndoLimit) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(UNDO_LIMIT_KEY, newUndoLimit);
        editor.apply();
    }

    /**
     * Gets the saved Image Type from SharedPreferences
     *
     * @return int image type based on final values in SlidingTilesImage.class
     */
    public int getImageType() {
        int defaultImageType = SlidingTilesImage.DEFAULT_TILES;
        return preferences.getInt(IMAGE_TYPE_KEY, defaultImageType);
    }

    /**
     * Set the Image Type in SharedPreferences.
     *
     * @param newImageType int image type based on final values in SlidingTilesImage.class
     */
    public void setImageType(int newImageType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(IMAGE_TYPE_KEY, newImageType);
        editor.apply();
    }

    /**
     * Set the Encoded Image in SharedPreferences with just the encoded image.
     *
     * @param encodedImage String representation of image in Base 64
     */
    public void setEncodedImage(String encodedImage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ENCODED_KEY, encodedImage);
        editor.apply();
    }

    /**
     * Set the Encoded Image and Image URL in SharedPreferences.
     *
     * @param encodedImage String representation of image in Base 64
     * @param url          String of online URL of image to be saved
     */
    public void setEncodedImage(String encodedImage, String url) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ENCODED_KEY, encodedImage);
        editor.putString(IMAGE_URL_KEY, url);
        editor.apply();
    }

    /**
     * Gets the saved Image URL from SharedPreferences
     *
     * @return String of saved image's online URL
     */
    public String getImageURL() {
        String defaultImageURL = "";
        return preferences.getString(IMAGE_URL_KEY, defaultImageURL);
    }

    /**
     * Gets the saved Encoded Image from SharedPreferences
     *
     * @return String of saved representation of image in Base 64
     */
    public String getEncodedImage() {
        String defaultEncodedImage = "";
        return preferences.getString(ENCODED_KEY, defaultEncodedImage);
    }
}
