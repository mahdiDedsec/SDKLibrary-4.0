package com.sdk.sdklibrary4.storage;

import com.sdk.sdklibrary4.data.structures.StringList;
import com.sdk.sdklibrary4.data.types.Strings;
import com.sdk.sdklibrary4.tools.OperatingSystem;

import ir.sdk.audio.UnsupportedTagException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSystem {
    private String path;
    private File file;

    private final String[] WINEXEC = {"bat", "cmd", "exe", "msi"};
    private final String[] LINEXEC = {"sh", "run"};
    private final String[] MACEXEC = {"app", "dmg"};

    private final String[] AUDIO = {"mp3", "wav", "ogg", "mpa", "aac"};
    private final String[] IMAGE = {"jpg", "jpeg", "bmp", "png", "svg", "fig", "tif", "gif"};
    private final String[] VIDEO = {"mp4", "avi", "mkv", "flv", "3gp", "nsv"};
    private final String[] TEXT = {"txt", "xml", "fxml", "xmlns", "iml"};
    private final String[] DOCUMENT = {"doc", "html", "html", " docx", "odt", "pdf"};

    private final String[] LIBRARY = {"dll", "jar", "so", "pyd"};
    private final String[] ARCHIVE = {"zip", "rar", "7zip", "tar.gz", "gz", "rar4"};

    private final String JAVA_SOURCE = "java";
    private final String CSHARP_SOURCE = "cs";
    private final String C_SOURCE = "c";
    private final String CPLUSPLUS_SOURCE = "cpp";
    private final String PYTHON_SOURCE = "py";
    private final String JAVASCRIPT_SOURCE = "js";
    private final String PHP_SOURCE = "php";
    private final String GOLANG_SOURCE = "go";
    private final String RUBY_SOURCE = "rb";

    private final String C_HEADER = "h";
    private final String CPLUSPLUS_HEADER = "hh";

    public FileSystem(String path) {
        this.path = path;
        this.file = new File(this.path);
    }

    /**
     * Check the existence of file
     *
     * @return True if exist and false if not
     */
    public boolean exist() {
        file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * Delete current file
     *
     * @return File delete status
     */
    public boolean delete() {
        return file.delete();
    }

    /**
     * Create new empty file
     *
     * @return File creation
     */
    public boolean create() throws IOException {
        return file.createNewFile();
    }

    /**
     * Get current file path
     *
     * @return The path
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * Set new path and file
     *
     * @param path New file path (String)
     */
    protected void setPath(String path) throws UnsupportedTagException, IOException {
        this.path = path;
        file = new File(path);
    }

    /**
     * Get current file object
     *
     * @return The file
     */
    public File getFile() {
        return file;
    }

    /**
     * Get file name
     *
     * @return File name
     */
    public String getName() {
        return Paths.get(getPath()).getFileName().toString();
    }

    /**
     * Get raw size of file
     *
     * @return Raw size of file
     */
    public long getSize() {
        return file.length();
    }

    /**
     * Get Human readable size
     *
     * @return The size of file
     */
    public String getReadableSize() {
        return new Strings().getReadableSize(getSize());
    }

    /**
     * Get modify date of file
     *
     * @return File modified date
     */
    public String getModifyDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get creation date of file
     *
     * @return File creation date
     */
    public String getCreateDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get access date of file
     *
     * @return File access date
     */
    public String getAccessDate() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
    }

    /**
     * Get modify time of file
     *
     * @return File modified time
     */
    public String getModifyTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastModifiedTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Get creation time of file
     *
     * @return File creation time
     */
    public String getCreateTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.creationTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Get access time of file
     *
     * @return File access time
     */
    public String getAccessTime() throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();

        return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
    }

    /**
     * Get parent directory name
     *
     * @return Parent directory name
     */
    public String getDirectoryName() {
        return new File(getPath()).getParentFile().getName();
    }

    /**
     * Get parent directory path
     *
     * @return Parent directory path
     */
    public String getDirectoryPath() {
        return new File(getPath()).getParentFile().getParent();
    }

    /**
     * Get the directory this file is in it
     *
     * @return The parent directory file path
     */
    public String getFilePath() {
        OperatingSystem operatingSystem = new OperatingSystem();
        Strings strings = new Strings();
        StringBuilder sb = new StringBuilder();
        String path = getPath();
        String[] s = null;

        try {
            int max = 0;

            if (operatingSystem.isWindows()) {
                if (path.contains("\\")) {
                    s = path.split("\\\\");
                    max = s.length - 1;

                    for (int i = 0; i < max; i++) {
                        if ((i + 1) < max) {
                            sb.append(s[i]).append("\\");
                        } else {
                            sb.append(s[i]);
                        }
                    }
                    return sb.toString();
                }
            } else if (operatingSystem.isLinux() || operatingSystem.isMac() || operatingSystem.isUnix()) {
                if (path.contains("/")) {
                    s = path.split("/");
                    max = s.length - 1;

                    for (int i = 0; i < max; i++) {
                        if ((i + 1) < max) {
                            sb.append(s[i]).append("/");
                        } else {
                            sb.append(s[i]);
                        }
                    }
                    return sb.toString();
                }
            }
            return getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return getPath();
        }
    }

    /**
     * Get owner of file
     *
     * @return File owner
     */
    public String getOwner() throws IOException {
        return Files.getOwner(getFile().toPath()).toString();
    }

    /**
     * Detect type of file by extension
     *
     * @return File type
     */
    public String getType() throws IOException {
        String[][] mimeType = {WINEXEC, LINEXEC, MACEXEC, AUDIO, IMAGE, VIDEO, TEXT, DOCUMENT, LIBRARY, ARCHIVE};
        Strings strings = new Strings();

        if (exist()) {
            String ext = getExtension().toLowerCase();

            for (int i = 0; i < mimeType.length; i++) {
                StringList list = new StringList(true).add(mimeType[i]);
                if (list.contains(ext)) {
                    switch (i) {
                        case 0:
                            return "Windows executable file";

                        case 1:
                            return "Linux executable file(script)";

                        case 2:
                            return "Mac application";

                        case 3:
                            return "Audio file";

                        case 4:
                            return "Image file";

                        case 5:
                            return "Video file";

                        case 6:
                            return "Plain text file";

                        case 7:
                            return "Document file";

                        case 8:
                            return "Library file";

                        case 9:
                            return "Archive file";
                    }
                }

                list.clear();
            }

            if (ext.equals(JAVA_SOURCE)) {
                return "Java source file";
            } else if (ext.equals(CSHARP_SOURCE)) {
                return "cSharp source file";
            } else if (ext.equals(C_SOURCE)) {
                return "C source file";
            } else if (ext.equals(CPLUSPLUS_SOURCE)) {
                return "C++ source file";
            } else if (ext.equals(PYTHON_SOURCE)) {
                return "Python source file";
            } else if (ext.equals(JAVASCRIPT_SOURCE)) {
                return "Javascript source file";
            } else if (ext.equals(PHP_SOURCE)) {
                return "Php source file";
            } else if (ext.equals(GOLANG_SOURCE)) {
                return "GoLang Source file";
            } else if (ext.equals(RUBY_SOURCE)) {
                return "Ruby source file";
            } else if (ext.equals(C_HEADER)) {
                return "C header file";
            } else if (ext.equals(CPLUSPLUS_HEADER)) {
                return "C++ header file";
            }
            return "Unknown type";
        }
        return "No file";
    }

    /**
     * Get base name of file
     *
     * @return File base name
     */
    public String getBaseName() {
        return FilenameUtils.getBaseName(getName());
    }

    /**
     * Get extension of file
     *
     * @return File extension
     */
    public String getExtension() {
        String ext = FilenameUtils.getExtension(getName());
        return ext;
    }

    /**
     * Copy current file to other location
     *
     * @return True if copy is successful and false if not
     */
    public boolean copy(String destination) throws IOException {
        File s = new File(getPath()), d = new File(destination);
        FileUtils.copyFile(s, d);

        return true;
    }

    /**
     * Move current file to other location
     *
     * @return True if move is successful and false if not
     */
    public boolean move(String destination) throws IOException {
        return copy(destination) && delete();
    }

    /**
     * Rename current file
     *
     * @return Rename status
     */
    public boolean rename(String name) throws IOException {
        Path source = Paths.get(getPath());
        Files.move(source, source.resolveSibling(name));
        return true;
    }

    /**
     * Check file is empty or not
     *
     * @return Empty status
     */
    public boolean isEmpty() {
        return getFile().length() == 0;
    }

    public String[] getWinExec() {
        return WINEXEC;
    }

    public String[] getLinExec() {
        return LINEXEC;
    }

    public String[] getMacExec() {
        return MACEXEC;
    }

    public String[] getAudio() {
        return AUDIO;
    }

    public String[] getImage() {
        return IMAGE;
    }

    public String[] getVideo() {
        return VIDEO;
    }

    public String[] getText() {
        return TEXT;
    }

    public String[] getDocument() {
        return DOCUMENT;
    }

    public String[] getLibrary() {
        return LIBRARY;
    }

    public String[] getArchive() {
        return ARCHIVE;
    }

    public String getJavaSource() {
        return JAVA_SOURCE;
    }

    public String getcSharpSource() {
        return CSHARP_SOURCE;
    }

    public String getcSource() {
        return C_SOURCE;
    }

    public String getCplusplusSource() {
        return CPLUSPLUS_SOURCE;
    }

    public String getPythonSource() {
        return PYTHON_SOURCE;
    }

    public String getJavascriptSource() {
        return JAVASCRIPT_SOURCE;
    }

    public String getPhpSource() {
        return PHP_SOURCE;
    }

    public String getGoLangSource() {
        return GOLANG_SOURCE;
    }

    public String getRubySource() {
        return RUBY_SOURCE;
    }

    public String getcHeader() {
        return C_HEADER;
    }

    public String getCplusplusHeader() {
        return CPLUSPLUS_HEADER;
    }
}