package com.sdk.sdklibrary4.storage;

import com.sdk.sdklibrary4.data.structures.StringList;
import com.sdk.sdklibrary4.data.types.Characters;
import com.sdk.sdklibrary4.data.types.Strings;
import com.sdk.sdklibrary4.storage.FileSystem;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextFile extends FileSystem {

    public TextFile(String path) {
        super(path);
    }

    /**
     * Read entire text file
     *
     * @return File content each line in one element
     */
    public ArrayList<String> read() throws IOException {
        RandomAccessFile file = new RandomAccessFile(getPath(), "r");
        ArrayList<String> content = new ArrayList<>();

        String str;
        while ((str = file.readLine()) != null) {
            content.add(str);
            content.add("\n");
        }

        file.close();
        return content;
    }

    public String readToString() throws IOException {
        Strings strings = new Strings();
        return strings.arrayToString(read().toArray(String[]::new), "\n");
    }

    /**
     * Count entire file characters
     *
     * @return Number of characters
     */
    public long countCharacters(boolean newLines) throws IOException {
        FileReader fr = new FileReader(getPath());
        long j = 0, i;

        while ((i = fr.read()) != -1) {
            if (!newLines) {
                if ((char) i != '\n') {
                    j++;
                }
            } else {
                j++;
            }
        }

        return j;
    }

    /**
     * Count entire file words
     *
     * @return Number of words
     */
    public long countWords() throws IOException {
        InputStreamReader input = new InputStreamReader(new FileInputStream(new File(getPath())));
        BufferedReader reader = new BufferedReader(input);

        String line;
        long words = 0;

        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                String[] wrd = line.split("\\s+");
                words += wrd.length;
            }
        }
        return words;
    }

    /**
     * Write data to file
     *
     * @param content File content (String)
     * @return True if successful and false if not
     */
    public boolean write(String content) throws IOException {
        FileWriter wr = new FileWriter(getPath());
        wr.write(content);

        wr.close();
        return true;
    }

    /**
     * Write array data to file
     *
     * @param content  File content (String[])
     * @param nextLine Go to next line after each array element
     * @return True if successful and false if not
     */
    public boolean write(String[] content, boolean nextLine) throws IOException {
        FileWriter wr = new FileWriter(getPath());
        if (content.length == 0) {
            return false;
        }

        for (String s : content) {
            wr.write(s);
            if (nextLine) {
                wr.write("\n");
            }
        }

        wr.close();
        return true;
    }

    /**
     * Search the file for key word
     *
     * @param key   The key (String)
     * @param match If you need to find exact match to key (boolean)
     * @return True if found and false if not
     */
    public boolean search(String key, boolean match) throws IOException {
        ArrayList<String> content = read();
        for (String s : content) {
            if (match) {
                if (s.equals(key)) {
                    return true;
                }
            } else {
                if (s.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clear the entire file
     *
     * @return True if cleared and false if not
     */
    public boolean clear() throws IOException {
        return write(new Strings().getEmptyString());
    }

    /**
     * Count matching keys in the file
     *
     * @param key   The key (String)
     * @param match If you need to find exact match to key (boolean)
     * @return Number of matches found
     */
    public long countMatches(String key, boolean match) throws IOException {
        String content = new StringList(true).add(read().toArray(String[]::new)).getString();
        return new Strings().countMatches(content, key, "\n", match);
    }

    /**
     * Count empty lines in the file
     *
     * @return Number of empty lines
     */
    public int countEmptyLines() throws IOException {
        ArrayList<String> content = read();
        int count = 0;

        for (String line : content) {
            if (line.equals(new Strings().getEmptyString())) {
                count++;
            }
        }

        return count;
    }

    /**
     * Count lines of file
     *
     * @return Number of lines
     */
    public long countLines() throws IOException {
        return Files.readAllLines(Paths.get(getPath())).size();
    }

    /**
     * Delete all keys match in the file
     *
     * @param key The key (String)
     * @return True if deleted and false if not
     */
    public boolean delete(String key) throws IOException {
        StringList list = new StringList(true).add(read().toArray(String[]::new));
        list.remove(key);

        return write(list.getString());
    }

    /**
     * Append text to last of file
     *
     * @param content New content (String)
     * @return True if appended and false if not
     */
    public boolean append(String content) throws IOException {
        try {
            Files.write(Paths.get(getPath()), content.getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads the first line of file
     *
     * @return The first line of file
     */
    public String readFirstLine() throws IOException {
        return readFirstLine(false);
    }

    /**
     * Reads the first line of file
     *
     * @param text Skip all lines until get to first text line
     * @return The first line of file
     */
    public String readFirstLine(boolean text) throws IOException {
        ArrayList<String> content = read();
        if (content.isEmpty()) {
            return new Strings().getEmptyString();
        }

        if (text) {
            for (String s : content) {
                if (!s.isEmpty() && !s.equals("\n")) {
                    return s;
                }
            }
        } else {
            return content.get(0);
        }

        return new Strings().getEmptyString();
    }

    /**
     * Reads the last line of file
     *
     * @return The last line of file
     */
    public String readLastLine() throws IOException {
        return readLastLine(false);
    }

    /**
     * Reads the last line of file
     *
     * @param text Skip all lines until get to last text line
     * @return The last line of file
     */
    public String readLastLine(boolean text) throws IOException {
        ArrayList<String> content = read();
        if (content.isEmpty()) {
            return new Strings().getEmptyString();
        }

        if (text) {
            StringList list = new StringList(true).add(content.toArray(String[]::new));
            list.reverse();

            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isEmpty() && !list.get(i).equals("\n")) {
                    return list.get(i);
                }
            }
        } else {
            return content.get(content.size() - 1);
        }

        return new Strings().getEmptyString();
    }

}