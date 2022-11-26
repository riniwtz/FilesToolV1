package io.github.riniwtz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class FilesExtractor extends SimpleFileVisitor<Path> {
    private File currentDirectory;
    private File targetDirectory;
    private LinkedList<Path> files;

    public FilesExtractor(File currentDirectory) {
        this.currentDirectory = currentDirectory;

        try {
            FileVisitor fileVisitor = new FileVisitor();
            Files.walkFileTree(currentDirectory.toPath(), fileVisitor);
            files = fileVisitor.getFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FilesExtractor(File currentDirectory, File targetDirectory) {
        this.currentDirectory = currentDirectory;
        this.targetDirectory = targetDirectory;

        try {
            FileVisitor fileVisitor = new FileVisitor();
            Files.walkFileTree(currentDirectory.toPath(), fileVisitor);
            files = fileVisitor.getFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extract() {
        for (Path p : files) {
            String iteratedFile = p.toString().substring(p.toString().lastIndexOf('/'));
            try {
                Files.move(p, Path.of(targetDirectory.toPath() + iteratedFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Moved: " + p + "\nto: " + iteratedFile);
        }
    }

    public void extract(String str, boolean isRegex) {
        for (Path p : files) {
            if (isRegex) {
                if (Pattern.compile(str).matcher(p.toString()).find()) {
                    String iteratedFile = p.toString().substring(p.toString().lastIndexOf('/'));
                    try {
                        Files.move(p, Path.of(targetDirectory.toPath() + iteratedFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Moved: " + p + "\nto: " + iteratedFile);
                }
            } else {
                if (p.toString().endsWith(str)) {
                    String iteratedFile = p.toString().substring(p.toString().lastIndexOf('/'));
                    try {
                        Files.move(p, Path.of(targetDirectory.toPath() + iteratedFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Moved: " + p + "\nto: " + iteratedFile);
                }
            }
        }
    }

    public int count() {
        return files.size();
    }

    public boolean exists() {
        return count() > 0;
    }

    public LinkedList<Path> listFiles() {
        return files;
    }
}
