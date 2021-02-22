package com.sdk.sdklibrary4.storage;


import com.sdk.sdklibrary4.tools.OperatingSystem;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ArchiveFile extends FileSystem {

    private final byte[] EMPTY_ZIP =
            {80, 75, 05, 06, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00};

    private CompressionLevel level;
    private final CompressionLevel[] COMPRESSION_LEVELS = {CompressionLevel.NORMAL,
            CompressionLevel.FAST, CompressionLevel.FASTEST, CompressionLevel.ULTRA, CompressionLevel.MAXIMUM};

    public ArchiveFile(String path) {
        super(path);
        level = CompressionLevel.NORMAL;
    }

    public boolean generateArchive(String password, String comment, File[] files, boolean delete) {
        try {
            if (files.length == 0) {
                return false;
            }

            if (exist() && !delete()) {
                return false;
            }

            if (!exist()) {
                generateEmptyArchive();
            }

            ZipFile zipFile = new ZipFile(getName());

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionLevel(level);
            parameters.setCompressionMethod(CompressionMethod.DEFLATE);

            if (!password.isEmpty()) {
                parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
                parameters.setCompressionMethod(CompressionMethod.DEFLATE);
                parameters.setEncryptionMethod(EncryptionMethod.AES);

                zipFile.setPassword(password.toCharArray());
                parameters.setEncryptFiles(true);
            } else {
                parameters.setEncryptFiles(false);
            }

            if (!comment.isEmpty()) {
                zipFile.setComment(comment);
            }

            ArrayList<File> filesToAdd = new ArrayList<>();
            for (File file : files) {
                if (new FileSystem(file.getAbsolutePath()).exist()) {
                    filesToAdd.add(file);
                } else {
                    if (new DirectorySystem(file.getAbsolutePath()).exist()) {
                        zipFile.addFolder(file);
                    }
                }
            }

            if (filesToAdd.isEmpty()) {
                return false;
            }

            zipFile.addFiles(filesToAdd, parameters);

            if (delete) {
                for (File f : files) {
                    if (new FileSystem(f.getAbsolutePath()).exist()) {
                        new FileSystem(f.getAbsolutePath()).delete();
                    } else {
                        if (new DirectorySystem(f.getAbsolutePath()).exist()) {
                            new DirectorySystem(f.getAbsolutePath()).delete();
                        }
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generateEmptyArchive() {
        try (FileOutputStream fos = new FileOutputStream(getFile())) {
            fos.write(EMPTY_ZIP, 0, 22);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean extractArchive(String path, String password) {
        try {
            OperatingSystem operatingSystem = new OperatingSystem();
            String pth = null;

            if (path.equals(".")) {
                pth = operatingSystem.getExecutePath();
            } else {
                pth = path;
            }

            ZipFile zipFile = new ZipFile(getName());
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }

            zipFile.extractAll(pth);
            return true;
        } catch (ZipException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CompressionLevel getLevel() {
        return level;
    }

    public void setLevel(CompressionLevel level) {
        this.level = level;
    }

    public CompressionLevel[] getCOMPRESSION_LEVELS() {
        return COMPRESSION_LEVELS;
    }
}