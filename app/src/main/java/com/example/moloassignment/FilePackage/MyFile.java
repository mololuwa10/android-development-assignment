package com.example.moloassignment.FilePackage;

import java.io.File;
import java.io.Serializable;

public class MyFile implements Serializable {

    private String content;
    private String filename;
    File file;

    public MyFile(File file, String filename, String content) {
        this.file = file;
        this.filename = filename;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File toFile() {
        return new File(file.getPath(), content);
    }
}
