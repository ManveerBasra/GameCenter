package com.example.group_0571.gamecentre;

import android.content.res.Resources;
import android.test.mock.MockContext;
import android.test.mock.MockResources;

/**
 * A MockContext with dummy resources used for testing.
 */
public class GamecentreMockContext extends MockContext {
    /**
     * The package name that is returned.
     */
    public static final String PACKAGE_NAME = "";

    /**
     * The resource identifier that is returned when getting a resource.
     */
    public static final int IDENTIFIER = 1;

    /**
     * The integer value that is returned when getting an integer resource.
     */
    public static final int INTEGER = 0;

    @Override
    public String getPackageName() {
        return PACKAGE_NAME;
    }

    @Override
    public Resources getResources() {
        return new GamecentreMockResources();
    }

    /**
     * A MockResources with dummy return values used for testing.
     */
    private class GamecentreMockResources extends MockResources {
        @Override
        public int getIdentifier(String name, String defType, String defPackage) {
            return IDENTIFIER;
        }

        @Override
        public int getInteger(int id) {
            return INTEGER;
        }
    }
}
