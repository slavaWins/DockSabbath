package org.example.core;

import org.apache.ant.compress.taskdefs.Unzip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public class IoHelper {


    public static void delete(File foolder) {
        if (foolder.exists()) {
            try {
                Files.walk(foolder.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void copyFile(File sourceFolder, File destinationFolder) {
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            String[] files = sourceFolder.list();
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);

                if (srcFile.isDirectory()) {
                    copyFile(srcFile, destFile);
                } else {
                    try {
                        Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("Ошибка при копировании файла: " + e.getMessage());
                    }
                }
            }
        }
    }

    public static File getFirstFolder(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    public static void rename(File foolder, File to) {
        if (foolder.exists()) {
            try {
                Files.walk(foolder.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unzip(File zipFilePath, File destDir) {
        Unzip unzipper = new Unzip();
        unzipper.setSrc(zipFilePath);
        unzipper.setDest(destDir);
        unzipper.execute();

    }

}