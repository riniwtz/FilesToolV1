package io.github.riniwtz;

import java.io.File;

public class FilesRefactor {
    private File dir;
    private String text;
    public FilesRefactor(File dir, String text) {
        this.dir = dir;
        this.text = text;
    }

    public void changeExtension() {
        FilesExtractor filesExtractor = new FilesExtractor(dir);
        filesExtractor.listFiles();
    }
}
