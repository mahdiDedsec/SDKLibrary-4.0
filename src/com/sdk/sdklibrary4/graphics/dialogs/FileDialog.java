package com.sdk.sdklibrary4.graphics.dialogs;

import com.sdk.sdklibrary4.graphics.AWTSwingUI;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.Arrays;

public class FileDialog extends AWTSwingUI {

    private String title;
    private String startPath;

    private FileNameExtensionFilter[] filters;
    private JFileChooser fileChooser;

    public FileDialog(String title, String startPath) {
        super();

        this.startPath = startPath;
        this.title = title;
        this.fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(title);
        if (!startPath.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPath() {
        return startPath;
    }

    public void setStartPath(String startPath) {
        this.startPath = startPath;
    }

    public FileNameExtensionFilter[] getFilters() {
        return filters;
    }

    public void setFilters(FileNameExtensionFilter[] filters) {
        this.filters = filters;
    }

    public File showSaveDialog() {
        if (!Objects.isNull(filters)) {
            Objects.requireNonNull(fileChooser);
            Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
        }

        return fileChooser.showSaveDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }

    public File showOpenDialog() {
        if (!Objects.isNull(filters)) {
            Objects.requireNonNull(fileChooser);
            Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
        }

        return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }

    public File selectDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (!startPath.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(startPath));
        }

        return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
    }
}
