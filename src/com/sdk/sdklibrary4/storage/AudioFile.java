package com.sdk.sdklibrary4.storage;


import com.sdk.sdklibrary4.data.types.Numbers;
import com.sdk.sdklibrary4.storage.FileSystem;
import ir.sdk.audio.ID3v2;
import ir.sdk.audio.InvalidDataException;
import ir.sdk.audio.Mp3File;
import ir.sdk.audio.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

public class AudioFile extends FileSystem {
    private Mp3File mp3File;

    public AudioFile(String path) throws UnsupportedTagException{
        super(path);

        try {
            mp3File = new Mp3File(getFile());
        } catch (InvalidDataException | IOException e) {
        }
    }

    /**
     * Get audio file track name
     *
     * @return Name of track
     */
    public String getTrack() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return id3v2Tag.getTrack();
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file artist name
     *
     * @return Name of artist
     */
    public String getArtist() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return id3v2Tag.getArtist();
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file title name
     *
     * @return Name of title
     */
    public String getTitle() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return id3v2Tag.getTitle();
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file comment
     *
     * @return Audio comment
     */
    public String getComment() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return id3v2Tag.getComment();
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file year created
     *
     * @return Year track created
     */
    public String getYear() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return id3v2Tag.getYear();
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file genre name
     *
     * @return Name of genre
     */
    public String getGenre() {
        if (mp3File.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return String.valueOf(id3v2Tag.getGenre());
        } else {
            return "Not supported.";
        }
    }

    /**
     * Get audio file duration
     *
     * @return Audio duration
     */
    public String getDurationSeconds() {
        return String.valueOf(mp3File.getLengthInSeconds());
    }

    /**
     * Get audio file quality
     *
     * @return Audio quality
     */
    public String getQuality() {
        return String.valueOf(mp3File.getBitrate()).concat(" kbps").concat(this.mp3File.isVbr() ? "(VBR)" : "(CBR)");
    }

    /**
     * Check track for custom tags
     *
     * @return True if exist and false if not
     */
    public String hasCustomTag() {
        return mp3File.hasId3v1Tag() ? "YES" : "NO";
    }

    /**
     * Get audio file sample rate
     *
     * @return Audio file sample rate
     */
    public String getSampleRate() {
        return mp3File.getSampleRate() + "Hz";
    }

    public String getDuration() {
        return new Numbers().secondsToTime(Integer.parseInt(getDurationSeconds()));
    }
}