package com.mmo.microservice.video.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    public static byte[] readFileFromPath(String path) {
        byte[] fileBytes = null;
        File file = new File(path);
        try {
            fileBytes = new byte[(int) file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileBytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBytes;
    }

    public static void deleteFileFromPath(String filePath) {
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}
