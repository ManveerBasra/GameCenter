package com.example.group_0571.gamecentre.utilTests;

import com.example.group_0571.gamecentre.utils.FileUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the FileUtil class.
 */
public class FileUtilTest {
    /**
     * A list of numbers that has already been written to numbers.ser to be used for comparison.
     */
    private List<Integer> numbers;

    /**
     * A handle to the file utility class to read/write data
     */
    private FileUtil fileUtil = FileUtil.getInstance();

    /**
     * Initialize the numbers ArrayList.
     */
    @Before
    public void setUp() {
        numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(5);
    }

    /**
     * Test whether an Object is correctly read from a file.
     */
    @Test
    public void testReadObjectFromFile() {
        try {
            // numbers.ser is stored in the app directory
            FileInputStream fileInputStream = new FileInputStream(new File("numbers.ser"));
            Object obj = fileUtil.readObjectFromFile(fileInputStream);
            assertEquals(numbers, obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find numbers.ser");
        }
    }

    /**
     * Test whether the Object returned is null if the InputStream is null.
     */
    @Test
    public void testReadObjectFromFileNotExists() {
        Object obj = fileUtil.readObjectFromFile(null);
        assertNull(obj);
    }

    /**
     * Test whether an Object is correctly written to a file.
     */
    @Test
    public void testWriteObjectToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("testWrite.ser"));
            fileUtil.writeObjectToFile(fileOutputStream, numbers);
            FileInputStream fileInputStream = new FileInputStream(new File("testWrite.ser"));
            Object obj = fileUtil.readObjectFromFile(fileInputStream);
            assertEquals(numbers, obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testWrite.ser");
        }
    }

    /**
     * Delete testWrite.ser after the tests are done.
     */
    @AfterClass
    public static void deleteTestWriteFile() {
        File file = new File("testWrite.ser");
        if (!file.delete()) {
            fail("Could not delete testWrite.ser");
        }
    }
}
