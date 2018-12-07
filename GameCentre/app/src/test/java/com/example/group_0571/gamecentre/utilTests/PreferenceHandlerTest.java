package com.example.group_0571.gamecentre.utilTests;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceHandlerTest {

    /**
     * PreferenceHandler object to be tested
     */
    private PreferenceHandler preferenceHandler;

    /**
     * Mock context to be used to get resources
     */
    private Context context = new GamecentreMockContext();

    /**
     * Mockito mocked preferences and editor
     */
    @Mock
    private SharedPreferences preferences;
    @Mock
    private SharedPreferences.Editor preferenceEditor;

    /**
     * Correctly setup SharedPreferences that returns editor, and
     * setup PreferenceHandler to be tested
     */
    @Before
    public void setUp() {
        Mockito.when(preferences.edit()).thenReturn(preferenceEditor);

        preferenceHandler = new PreferenceHandler(preferences, context);
    }

    /**
     * Test whether getUndoLimit() correctly gets and returns the undo limit from SharedPreferences
     */
    @Test
    public void testGetUndoLimit() {
        Mockito.when(preferences.getInt(eq("undo_limit"), anyInt())).thenReturn(0);
        assertEquals(0, preferenceHandler.getUndoLimit());
    }

    /**
     * Test whether getImageType() correctly gets and returns the image type from SharedPreferences
     */
    @Test
    public void testGetImageType() {
        Mockito.when(preferences.getInt(eq("image type"), anyInt())).thenReturn(0);
        assertEquals(0, preferenceHandler.getImageType());
    }

    /**
     * Test whether getImageURL() correctly gets and returns the image url from SharedPreferences
     */
    @Test
    public void testGetImageURL() {
        Mockito.when(preferences.getString(eq("image url"), anyString())).thenReturn("imageUrl");
        assertEquals("imageUrl", preferenceHandler.getImageURL());
    }

    /**
     * Test whether getEncodedImage() correctly gets and returns the encoded image from SharedPreferences
     */
    @Test
    public void testGetEncodedImage() {
        Mockito.when(preferences.getString(eq("encoded image"), anyString())).thenReturn("encodedImage");
        assertEquals("encodedImage", preferenceHandler.getEncodedImage());
    }

    /**
     * Test whether setUndoLimit() correctly sets the undo limit in SharedPreferences
     */
    @Test
    public void testSetUndoLimit() {
        preferenceHandler.setUndoLimit(4);
        Mockito.verify(preferenceEditor, times(1)).putInt(eq("undo_limit"), eq(4));
    }

    /**
     * Test whether setImageType() correctly sets the image type in SharedPreferences
     */
    @Test
    public void testSetImageType() {
        preferenceHandler.setImageType(-1);
        Mockito.verify(preferenceEditor, times(1)).putInt(eq("image type"), eq(-1));
    }

    /**
     * Test whether setEncodedImage() with only encoded image parameter correctly sets the
     * encoded image in SharedPreferences
     */
    @Test
    public void testSetEncodedImageNoUrl() {
        preferenceHandler.setEncodedImage("encodedImage");
        Mockito.verify(preferenceEditor, times(1)).putString(eq("encoded image"), eq("encodedImage"));
    }

    /**
     * Test whether setEncodedImage() with encoded image and image url parameter correctly sets the
     * encoded image and image url in SharedPreferences
     */
    @Test
    public void testSetEncodedImageWithUrl() {
        preferenceHandler.setEncodedImage("encodedImage", "imageUrl");
        Mockito.verify(preferenceEditor, times(1)).putString(eq("encoded image"), eq("encodedImage"));
        Mockito.verify(preferenceEditor, times(1)).putString(eq("image url"), eq("imageUrl"));
    }
}