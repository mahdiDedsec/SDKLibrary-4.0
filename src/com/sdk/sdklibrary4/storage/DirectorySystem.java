package com.sdk.sdklibrary4.storage;


import com.sdk.sdklibrary4.data.types.Strings;
import com.sdk.sdklibrary4.tools.OperatingSystem;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DirectorySystem {
    private String path;

    public DirectorySystem(String path) {
        this.path = path;

        if (path.equals(".")) {
            this.path = new OperatingSystem().getExecutePath();
        }
    }

    /**
     * Calculate the size of directory
     *
     * @param f File (File object)
     * @return Raw size of directors
     */
    private long calcSize(File f) {
        long length = 0;

        for (File file : Objects.requireNonNull(f.listFiles())) {
            if (file.isFile())
                length += file.length();
            else
                length += calcSize(file);
        }
        return length;
    }

    /**
     * Get current directory file
     *
     * @return The file
     */
    public File getFile() {
        File file = new File(path);
        return file;
    }

    /**
     * Get current directory path
     *
     * @return The path
     */
    public String getPath() {
        return new File(path).getAbsolutePath();
    }

    /**
     * Create new empty directory
     *
     * @return Directory creation
     */
    public boolean create() {
        return new File(path).mkdir();
    }

    /**
     * Delete current directory
     *
     * @return Directory delete status
     */
    public boolean delete() throws IOException {
        FileUtils.deleteDirectory(new File(getPath()));
        return !exist();
    }

    /**
     * Check directory existence
     *
     * @return True if exist and false if not
     */
    public boolean exist() {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    /**
     * Get the directory this directory is in it
     *
     * @return The parent directory directory path
     */
    public String getDirectoryPath() {
        String[] drs = null;
        OperatingSystem system = new OperatingSystem();
        StringBuilder sb = new StringBuilder();
        int max = 0;

        if (system.isWindows()) {
            drs = getPath().split("\\\\");
        } else {
            drs = getPath().split("/");
        }

        max = drs.length - 1;
        for (int i = 0; i < max; i++) {
            if ((i + 1) < max) {
                sb.append(drs[i]);

                if (system.isWindows()) {
                    sb.append("\\");
                } else {
                    sb.append("/");
                }
            } else {
                sb.append(drs[i]);
            }
        }

        return sb.toString();
    }

    /**
     * Get directory name
     *
     * @return Directory name
     */
    public String getName() {
        String[] drs = null;

        if (new OperatingSystem().isWindows()) {
            drs = getPath().split("\\\\");
        } else {
            drs = getPath().split("/");
        }

        return drs[drs.length - 1];
    }

    /**
     * Rename current directory
     *
     * @return Rename status
     */
    public boolean rename(String name) {
        String[] tmp = null;
        StringBuilder build = new StringBuilder();
        String newName = null;

        if (exist()) {
            if (new OperatingSystem().isWindows()) {
                tmp = getPath().split("\\\\");

                for (int i = 0; i < tmp.length - 1; i++) {
                    build.append(tmp[i]).append("\\\\");
                }

                newName = build.toString().concat("\\\\").concat(name);
            } else {
                tmp = getPath().split("/");

                for (int i = 0; i < tmp.length - 1; i++) {
                    build.append(tmp[i]).append("/");
                }

                newName = build.toString().concat("/").concat(name);
            }

            return new File(getPath()).renameTo(new File(newName));
        }

        return false;
    }

    /**
     * Get Human readable size
     *
     * @return The size of directory
     */
    public String getReadableSize() {
        return new Strings().getReadableSize(calcSize(new File(getPath())));
    }

    /**
     * Get raw size of directory
     *
     * @return Raw size of directory
     */
    public long getSize() {
        return calcSize(new File(getPath()));
    }

    /**
     * Get modify date of directory
     *
     * @return Directory modified date
     */
    public String getModifyDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get creation date of directory
     *
     * @return Directory creation date
     */
    public String getCreateDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get access date of directory
     *
     * @return Directory access date
     */
    public String getAccessDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get modify time of directory
     *
     * @return Directory modified time
     */
    public String getModifyTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Get creation time of directory
     *
     * @return Directory creation time
     */
    public String getCreateTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Get access time of directory
     *
     * @return Directory access time
     */
    public String getAccessTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Count directory files
     *
     * @return Number of files
     */
    public int countFiles() throws IOException {
        List<String> fileNames = new ArrayList<>();
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(getPath()));
        for (Path pth : directoryStream) {
            if (new File(pth.toString()).isFile()) {
                fileNames.add(pth.toString());
            }

        }
        return fileNames.size();
    }

    /**
     * Count directory directories
     *
     * @return Number of directories
     */
    public int countDirectories() throws IOException {
        File file = new File(getPath());
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });

        return files.length;
    }

    /**
     * Count all directories include sub directories
     *
     * @return Number of all directories
     */
    public int countSubDirectories() throws IOException {
        Path dir = Paths.get(getPath());
        return (int) Files.walk(dir).parallel().filter(p -> p.toFile().isDirectory()).count() - 1;
    }

    /**
     * Count all files include sub files
     *
     * @return Number of all files
     */
    public int countSubFiles() throws IOException {
        Path dir = Paths.get(getPath());
        return (int) Files.walk(dir).parallel().filter(p -> p.toFile().isFile()).count();
    }

    /**
     * Get directory information in Map format
     *
     * @return All directory information
     */
    public HashMap<String, String> getDirectoryInfo() throws IOException {
        String[] keys = {"name", "size", "ctime", "cdate", "mtime", "mdate", "atime", "adate", "files", "dirs",
                "sfiles", "sdirs", "path", "parent"},
                values = {getName(), getReadableSize(), getCreateTime(), getCreateDate(), getModifyDate(),
                        getModifyDate(), getAccessTime(), getAccessDate(), String.valueOf(countFiles()),
                        String.valueOf(countDirectories()), String.valueOf(countSubFiles()),
                        String.valueOf(countSubDirectories()), getPath(), getDirectoryPath()};

        HashMap<String, String> rValues = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            rValues.put(keys[i], values[i]);
        }

        return rValues;
    }

    /**
     * Get owner of directory
     *
     * @return Directory owner
     */
    public String getOwner() throws IOException {
        return Files.getOwner(getFile().toPath()).toString();
    }

    /**
     * Copy current directory to other location
     *
     * @return True if copy is successful and false if not
     */
    public boolean copy(String destination) throws IOException {
        File s = new File(getPath()), d = new File(destination);
        FileUtils.copyDirectory(s, d);

        return true;
    }

    /**
     * Move current directory to other location
     *
     * @return True if move is successful and false if not
     */
    public boolean move(String destination) throws IOException {
        return copy(destination) && delete();
    }

    /**
     * Get all files from directory and subdirectories
     *
     * @return Lis of files
     */
    public List<File> getAllFiles() throws IOException {
        return Files.walk(Paths.get(getPath()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    public List<File> getAllDirectories() throws IOException {
        return Files.walk(Paths.get(getPath()))
                .filter(Files::isDirectory)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    public List<File> getDirectoryContent() {
        File [] content = new File(getPath()).listFiles();
        List<File> files = new ArrayList<>();

        Collections.addAll(files, content != null ? content : new File[0]);
        return files;
    }

    public List<File> getDirectories() {
        File file = new File(getPath());
        String[] names = new File(getPath()).
                list((file1, s) -> new File(file1, s).isDirectory());

        List<File> files = new ArrayList<>();
        for (String name : names != null ? names : new String[0]) {
            files.add(new File(name));
        }

        return files;
    }

    public List<File> getFiles() {
        File file = new File(getPath());
        String[] names = new File(getPath()).list((file1, s) -> new File(file1, s).isFile());

        List<File> files = new ArrayList<>();
        for (String name : names != null ? names : new String[0]) {
            files.add(new File(name));
        }

        return files;
    }

    /**
     * Count all files lines in a directory
     *
     * @return Lines in files
     */
    public long countFilesLines() throws IOException {
        List<File> files = getAllFiles();
        TextFile file = null;
        long count = 0;

        for (File value : files) {
            file = new TextFile(value.getAbsolutePath());

            if (file.getType().toLowerCase().equals("plain text file")) {
                count += file.countLines();
            }
        }

        return count;
    }
}