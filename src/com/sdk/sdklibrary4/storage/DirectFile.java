package com.sdk.sdklibrary4.storage;

import ir.sdk.audio.UnsupportedTagException;

import java.io.IOException;

public class DirectFile extends TextFile{

    private AudioFile audioFile;
    private ArchiveFile archiveFile;
    private ImageFile imageFile;

    public DirectFile(String path) throws UnsupportedTagException, IOException {
        super(path);

        audioFile = new AudioFile(this.getPath());
        archiveFile = new ArchiveFile(this.getPath());
        imageFile = new ImageFile(this.getPath());
    }

    public AudioFile getAudioFile() {
        return this.audioFile;
    }

    public ImageFile getImageFile() {
        return this.imageFile;
    }

    public ArchiveFile getArchiveFile() {
        return archiveFile;
    }
}
