package io.github.riniwtz;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

public class FileVisitor extends SimpleFileVisitor<Path> {

    private LinkedList<Path> pathList = new LinkedList<>();
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile() && !file.endsWith(".DS_Store")) {
            pathList.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    public LinkedList<Path> getFiles() {
        return pathList;
    }
}
