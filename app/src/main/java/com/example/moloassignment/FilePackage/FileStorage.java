package com.example.moloassignment.FilePackage;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileStorage {
    private Context fileContext;

    public FileStorage(Context context) {
        fileContext = context;
    }

    public boolean createFile(String fileName, String content) {
        try {
            File file = new File(fileContext.getFilesDir(), fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<File> getAllFiles() {
        ArrayList<File> listOfFiles = new ArrayList<>();
        File[] files = fileContext.getFilesDir().listFiles();
        if (files != null) {
            listOfFiles.addAll(Arrays.asList(files));
        }
        return listOfFiles;
    }
}
