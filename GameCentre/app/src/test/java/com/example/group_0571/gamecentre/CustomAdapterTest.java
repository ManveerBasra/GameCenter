package com.example.group_0571.gamecentre;

import android.view.View;

import com.example.group_0571.gamecentre.mvcGameModel.CustomAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the CustomAdapter class.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomAdapterTest {
    /**
     * The CustomAdapter used for testing.
     */
    private CustomAdapter customAdapter;

    /**
     * The List of Views passed to the CustomAdapter.
     */
    private List<View> views;

    /**
     * Mock Views used for the List of Views.
     */
    @Mock
    private View view1, view2;

    /**
     * The column width and height used for testing.
     */
    private static final int DIMENSION = 1;

    /**
     * Initialize the CustomAdapter and List of Views.
     */
    @Before
    public void setUp() {
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        customAdapter = new CustomAdapter(views, DIMENSION, DIMENSION);
    }

    /**
     * Test whether the correct count is returned.
     */
    @Test
    public void testGetCount() {
        assertEquals(views.size(), customAdapter.getCount());
    }

    /**
     * Test whether the correct item is returned.
     */
    @Test
    public void testGetItem() {
        assertEquals(view1, customAdapter.getItem(0));
    }

    /**
     * Test whether IndexOutOfBoundsException is thrown when the position is out of bounds.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetItemOutOfBounds() {
        customAdapter.getItem(views.size());
        fail("IndexOutOfBoundsException not thrown");
    }

    /**
     * Test whether the correct item id is returned.
     */
    @Test
    public void testGetItemId() {
        assertEquals(1, customAdapter.getItemId(1));
    }

    /**
     * Test whether the correct view is returned when convertView is null.
     */
    @Test
    public void testGetViewConvertViewNull() {
        assertEquals(view1, customAdapter.getView(0, null, null));
    }

    /**
     * Test whether IndexOutOfBoundsException is thrown when the position is out of bounds.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetViewOutOfBounds() {
        customAdapter.getView(views.size(), null, null);
        fail("IndexOutOfBoundsException not thrown");
    }

    /**
     * Test whether the correct view is returned when convertView is not null.
     */
    @Test
    public void testGetViewConvertViewNotNull() {
        assertEquals(view1, customAdapter.getView(1, view1, null));
    }
}
