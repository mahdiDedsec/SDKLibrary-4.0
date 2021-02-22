package com.sdk.sdklibrary4.storage;

import com.sdk.sdklibrary4.storage.FileSystem;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageFile extends FileSystem {

    public ImageFile(String path) throws IOException {
        super(path);
    }

    /**
     * Get image file width
     *
     * @return Width of file
     */
    public int getWidth() throws IOException {
        BufferedImage bimg = ImageIO.read(getFile());
        return bimg.getWidth();
    }

    /**
     * Get image file height
     *
     * @return Height of file
     */
    public int getHeight() throws IOException {
        BufferedImage bimg = ImageIO.read(getFile());
        return bimg.getHeight();
    }
}