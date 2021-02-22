package com.sdk.sdklibrary4.network;

import com.sdk.sdklibrary4.data.types.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Internet {
    private String url;

    private final int CONNECTION_TIMEOUT;
    private final int READ_TIMEOUT;

    public Internet(String url) {
        this.url = url;
        this.CONNECTION_TIMEOUT = 5000;
        this.READ_TIMEOUT = 5000;
    }

    public boolean isInternetConnected() {
        try {
            URL url = new URL(this.url);
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IOException var3) {
            return false;
        }
    }

    public long getSize() throws IOException {
        try {
            final HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("HEAD");
            final String lengthHeaderField = urlConnection.getHeaderField("content-length");
            Long result = lengthHeaderField == null ? null : Long.parseLong(lengthHeaderField);
            return result == null || result < 0L ? -1L : result;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return -1;
        }
    }

    public String getReadableSize() throws IOException {
        return new Strings().getReadableSize(getSize());
    }

    public String getName() throws MalformedURLException {
        return FilenameUtils.getName((new URL(this.url)).getPath());
    }

    public String getBaseName() throws MalformedURLException {
        return FilenameUtils.getBaseName((new URL(this.url)).getPath());
    }

    public String getExtension() throws MalformedURLException {
        return FilenameUtils.getExtension((new URL(this.url)).getPath());
    }

    public boolean downloadFile() {
        try {
            FileUtils.copyURLToFile(new URL(this.url), new File(this.getName()), this.CONNECTION_TIMEOUT, this.READ_TIMEOUT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void ping(int count, boolean lineNumber) throws IOException {
        HttpURLConnection connection = null;

        for(int i = 0; i < count; ++i) {
            URL u = new URL(this.url);
            connection = (HttpURLConnection)u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            if (lineNumber) {
                System.out.printf("%d. ", i + 1);
            }

            System.out.println("Url address \"".concat(this.url).
                    concat("\"").concat(" return code: ".concat(String.valueOf(code))));
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
