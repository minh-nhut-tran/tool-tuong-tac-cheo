package com.example.selenium.common;

import com.example.selenium.constants.CurrentDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileHandler {

    public static File createFile(File file) throws IOException {
        if (file.exists()) {
            if (!file.delete()) return null;
        }
        if (file.createNewFile()) return file;
        return null;
    }


    public static boolean checkFileExist(String fileName) {
        File directory = new File(CurrentDirectory.currentDirectoryFacebook);
        if (!directory.exists()) return true;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && file.getName().equals(fileName)) {
                return false;
            }
        }
        return true;
    }


    public static String readFile(File fileName) {
        if (!fileName.exists()) return null;
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] c = new byte[fis.available()];
            int bytesRead;
            while ((bytesRead = fis.read(c)) != -1) {
                result = new String(c, 0, bytesRead);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List<File> getAllFileOnDirectory(String pathDirectory){
        File directory = new File(pathDirectory);
        if(!directory.exists()) return null;
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
    }
}
