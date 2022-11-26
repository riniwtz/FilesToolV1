package io.github.riniwtz;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FilesExtractor filesExtractor = new FilesExtractor(new File("/Users/rini/Documents"), new File("/Users/rini/Downloads"));
        filesExtractor.extract(".mov", false);
    }
}