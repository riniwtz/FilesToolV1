package io.github.riniwtz;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FilesGrouper {

    public FilesGrouper() {
        /*
        * 1. Instantiate FilesExtractor directory in constructor
        * 2. Check if files exists
        * 3. If it exists then create a folder named "Grouped Pictures" (Apply principles of folder making)
        * 4. Move all files from listFiles() method of FilesExtractor object to Grouped Pictures directory
        * */

        /*
        * Folder Making Principle Concept
        * 1. Check if folder exists
        * 2. If folder exist then proceed and ignore else create a folder
        *
        * Always check if folder exists to prevent duplication of folder making.
        * But this is already solved by just doing mkdir() method from File class.
        * */

        // Initialize directory fields
        File folder = new File("\\Users\\rini\\Desktop");
        File newFolder = new File(folder + "\\General");

        // Initialize filesExtractor object with directory
        FilesExtractor filesExtractor = new FilesExtractor(folder);

        // Make folder automatically
        boolean hasNewFolder = newFolder.mkdir();

        // Transfer all scanned files (png) to new folder
        Stream<Path> a = filesExtractor.listFiles().stream().filter(path -> path.endsWith(".png"));

        System.out.println(a);
    }



}
