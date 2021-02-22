package com.sdk.sdklibrary4.tools;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import com.sdk.sdklibrary4.data.types.Strings;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperatingSystem {
    private String osName;
    private String home;
    private String path;
    private String username;

    private FileSystemView fsv;

    public OperatingSystem() {
        this.osName = System.getProperty("os.name").toLowerCase();

        this.home = System.getProperty("user.home");
        this.path = System.getProperty("user.dir");
        this.username = System.getProperty("user.name");

        this.fsv = FileSystemView.getFileSystemView();
    }

    public boolean isWindows() {
        return osName.contains("win");
    }

    public boolean isMac() {
        return osName.contains("mac");
    }

    public boolean isLinux() {
        return osName.contains("linux");
    }

    public boolean isUnix() {
        return (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
    }

    public int detectOS() {
        if (isWindows()) {
            return 1;
        }

        if (isMac()) {
            return 2;
        }

        if (isLinux()) {
            return 3;
        }

        if (isUnix()) {
            return 4;
        }

        return -1;
    }

    public String getOSName() {
        if (isWindows()) {
            return "Microsoft Windows";
        } else if (isMac()) {
            return "Mac OS";
        } else if (isLinux()) {
            return "GNU Linux";
        } else if (isUnix()) {
            return "Unix";
        } else {
            return "Unknown";
        }
    }

    public String getHomeUser() {
        return home;
    }

    public String getExecutePath() {
        return path;
    }

    public String getUsername() {
        return username;
    }

    public File[] getSystemPartitionsAsFile() {
        return File.listRoots();
    }

    public String[] getSystemPartitionsInfo(String mode) {
        File[] files = getSystemPartitionsAsFile();

        if (files != null) {
            String[] names = new String[files.length];

            for (int i = 0; i < files.length; i++) {
                switch (mode.toLowerCase()) {
                    case "letter":
                        names[i] = files[i].toString();
                        break;

                    case "type":
                        names[i] = fsv.getSystemTypeDescription(files[i]);
                        break;

                    case "total":
                        names[i] = new Strings().getReadableSize(files[i].getTotalSpace());
                        break;

                    case "free":
                        names[i] = new Strings().getReadableSize(files[i].getFreeSpace());
                        break;

                    default:
                        return new Strings().getEmptyArray();
                }

            }
            return names;
        }
        return new Strings().getEmptyArray();
    }

    public String getSystemTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH : mm : ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getSystemDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getSystemTimeDate() {
        return getSystemDate() + " - " + getSystemTime();
    }

    public void copyTextClipboard(String text) {
        try {
            StringSelection stringSelection = new StringSelection(text);
            java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFileExplorer(String path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(path));
        }
    }

    public boolean takeScreenshot(String path) {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(path));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}