package com.sdk.sdklibrary4.data.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import com.sdk.sdklibrary4.tools.ExternalTools;

public class Characters {

    public char[] getEmptyArray() {
        return new char[] {};
    }

    public char toLowerCase(char c) {
        return String.valueOf(c).toLowerCase().toCharArray()[0];
    }

    public char toUpperCase(char c) {
        return String.valueOf(c).toUpperCase().toCharArray()[0];
    }

    public boolean isLowerCase(char c) {
        return c == toLowerCase(c) ? true : false;
    }

    public boolean isUpperCase(char c) {
        return c == toUpperCase(c) ? true : false;
    }

    public int countMathces(String input, char key) {
        if (!input.contains(String.valueOf(key))) {
            return -1;
        }

        return new Strings().countMatches(input, String.valueOf(key), "", true);
    }

    public char[] reverse(char[] input) {
        Character[] characters = ExternalTools.toCharacterArray(input);

        Collections.reverse(Arrays.asList(characters));
        return ArrayUtils.toPrimitive(characters);
    }

    public int countMatches(String input, char key) {
        int count = 0;

        if (Objects.isNull(input)) {
            return -1;
        }

        for (char item : input.toCharArray()) {
            if (item == key) {
                count++;
            }
        }

        return count;
    }

    public String arrayToString(char [] array, String split) {
        if (Objects.isNull(array) || Objects.isNull(split)) {
            return new Strings().getEmptyString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i + 1 < array.length) {
                sb.append(split);
            }
        }

        return sb.toString();
    }
}