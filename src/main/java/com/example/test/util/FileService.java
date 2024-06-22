package com.example.test.util;

import org.apache.tika.Tika;


public class FileService {

    private Tika tika = new Tika();

    public String detectMimeType(byte[] fileData) {
        try {
            return tika.detect(fileData);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isImage(byte[] fileData) {
        String mimeType = detectMimeType(fileData);
        return mimeType != null && mimeType.startsWith("image");
    }

    public boolean isVideo(byte[] fileData) {
        String mimeType = detectMimeType(fileData);
        return mimeType != null && mimeType.startsWith("video");
    }
}
