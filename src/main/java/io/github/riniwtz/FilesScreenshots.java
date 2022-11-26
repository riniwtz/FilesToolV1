package io.github.riniwtz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
public class FilesScreenshots {
    private File dir;
    private BasicFileAttributes basicFileAttributes;
    private FileTime fileTime;
    public FilesScreenshots(File dir) {
        this.dir = dir;
    }

    public void organize() {
        // Scan all files
        // The creation of class folders will apply during scanning
        // Accept files within the schedule
        File[] files = dir.listFiles(file -> {
            // Check for file extension
            if (file.getName().endsWith(".png")) {
                try {
                    // LOG
                    System.out.println("Scanning file: " + file);
                    //
                    basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    fileTime = basicFileAttributes.creationTime();
                    if (!onSchedule(file)) buildOrganizedDirectory(file, "misc", true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        });
    }

    private void buildOrganizedDirectory(File file, String classCode, boolean isCapitalized) throws IOException {
        // Capitalization Tool for Class Folder Name
        String className;
        if (isCapitalized) className = classCode.toUpperCase();
        else className = classCode.toLowerCase();

        // Initializing available directories and available to access folder objects
        File newSubjectFolder = new File(dir + "/" + className);
        File newDateFolder = new File(newSubjectFolder + "/" + getFormattedCreationTime(fileTime, "MMMM d, Y"));
        File newFileLocation = new File(newDateFolder + "/" + file.getName());

        // Apply Concept Map of Folder Creation System
        // Subject Folder
        if (!newSubjectFolder.exists()) {
            if (newSubjectFolder.mkdir()) {
                System.out.println("Created folder: " + newSubjectFolder);
            }
        }

        // Date Folder
        if (!newDateFolder.exists()) {
            if (newDateFolder.mkdir()) {
                System.out.println("Created folder: " + newDateFolder);
                // Moves current file to new file location
                // LOG
                System.out.println("Moving file from: " + file + "\nto " + newFileLocation);
                Files.move(file.toPath(), newFileLocation.toPath());
            }
        } else {
            // Moves current file to new file location
            // LOG
            System.out.println("Moving file from: " + file + "\nto " + newFileLocation);
            Files.move(file.toPath(), newFileLocation.toPath());
        }
    }
    private boolean onSchedule(File file) throws IOException {
        // Get the date and time today
        final DayOfWeek DAY_OF_WEEK_FROM_FILE = DayOfWeek.of(Integer.parseInt(getFormattedCreationTime(fileTime, "e")) - 1);
        final LocalTime TIME_FROM_FILE = LocalTime.parse(getFormattedCreationTime(fileTime, "HH:mm"));

        if (DAY_OF_WEEK_FROM_FILE.equals(DayOfWeek.MONDAY) || DAY_OF_WEEK_FROM_FILE.equals(DayOfWeek.WEDNESDAY)) {
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("07:30")) && TIME_FROM_FILE.isBefore(LocalTime.parse("09:00"))) {
                buildOrganizedDirectory(file, "chm01", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("09:00")) && TIME_FROM_FILE.isBefore(LocalTime.parse("10:30"))) {
                buildOrganizedDirectory(file, "eng01", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("10:30")) && TIME_FROM_FILE.isBefore(LocalTime.parse("12:00"))) {
                buildOrganizedDirectory(file, "lunch", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("12:00")) && TIME_FROM_FILE.isBefore(LocalTime.parse("13:30"))) {
                buildOrganizedDirectory(file, "math01", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("13:30")) && TIME_FROM_FILE.isBefore(LocalTime.parse("15:00"))) {
                buildOrganizedDirectory(file, "pdv01", true);
                return true;
            }
        }

        if (DAY_OF_WEEK_FROM_FILE.equals(DayOfWeek.TUESDAY) || DAY_OF_WEEK_FROM_FILE.equals(DayOfWeek.THURSDAY)) {
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("07:30")) && TIME_FROM_FILE.isBefore(LocalTime.parse("09:00"))) {
                buildOrganizedDirectory(file, "ict01", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("09:00")) && TIME_FROM_FILE.isBefore(LocalTime.parse("10:30"))) {
                buildOrganizedDirectory(file, "pe01", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("12:00")) && TIME_FROM_FILE.isBefore(LocalTime.parse("13:30"))) {
                buildOrganizedDirectory(file, "math04", true);
                return true;
            }
            if (TIME_FROM_FILE.isAfter(LocalTime.parse("13:30")) && TIME_FROM_FILE.isBefore(LocalTime.parse("15:00"))) {
                buildOrganizedDirectory(file, "fil01", true);
                return true;
            }
        }
        return false;
    }

    private String getFormattedCreationTime(FileTime fileTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateTimeFormatter);
    }
}
