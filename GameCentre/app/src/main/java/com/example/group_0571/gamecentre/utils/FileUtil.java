package com.example.group_0571.gamecentre.utils;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Utility class to read and write Objects to a file.
 * Implements the Singleton design pattern.
 */
public class FileUtil {
    /**
     * The single instance of FileUtil.
     */
    private static FileUtil instance = null;

    /**
     * Prevent instantiation of this class from outside this class.
     */
    private FileUtil() {
    }

    /**
     * Get the singleton instance of FileUtil.
     *
     * @return an instance of FileUtil
     */
    public static FileUtil getInstance() {
        if (instance == null) {
            instance = new FileUtil();
        }
        return instance;
    }

    /**
     * Reads an Object from file
     *
     * @param inputStream an InputStream for the file path
     * @return the Object
     */
    public Object readObjectFromFile(InputStream inputStream) {
        Object obj = null;
        try {
            if (inputStream != null) {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                obj = objectInputStream.readObject();
                inputStream.close();
                objectInputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("read object from file", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("read object from file", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("read object from file", "File contained unexpected data type: " + e.toString());
        }
        return obj;
    }

    /**
     * Writes an Object to a file.
     *
     * @param outputStream the OutputStream for the file path
     * @param objToWrite   the Object to write
     */
    public void writeObjectToFile(OutputStream outputStream, Object objToWrite) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(objToWrite);
            outputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("write object to file", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("write object to file", "Can not read file: " + e.toString());
        }
    }
}
