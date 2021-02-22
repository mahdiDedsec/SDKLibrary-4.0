package com.sdk.sdklibrary4.data.types;

import java.lang.reflect.Array;
import java.util.Objects;

import javax.management.monitor.StringMonitorMBean;

import org.apache.commons.lang3.StringUtils;

public class Strings {
    public String getReadableSize(double size) {
        int i = 0;
        double tmp = size;

        if (size <= 0) {
            return String.valueOf(size);
        } else if (size < 1024) {
            return size + " B";
        }

        while (tmp > 1024) {
            tmp /= 1024;
            i++;
        }

        int dotPos = String.valueOf(tmp).indexOf(".");
        String real = String.valueOf(tmp).substring(0, dotPos);

        if ((dotPos + 3) > String.valueOf(tmp).length()) {
            real = real.concat(String.valueOf(tmp).substring(dotPos));
        } else {
            real = real.concat(String.valueOf(tmp).substring(dotPos, dotPos + 3));
        }

        switch (i) {
            case 1:
                return real + " KB";
            case 2:
                return real + " MB";
            case 3:
                return real + " GB";
            case 4:
                return real + " TB";

            default:
                return null;
        }
    }

    public boolean isNullOrEmpty(String input) {
        if (Objects.isNull(input)) {
            return true;
        }

        if (input.isEmpty()) {
            return true;
        }

        return false;
    }

    public String[] getEmptyArray() {
        return new String[]{};
    }

    public String getEmptyString() {
        return new String();
    }

    public String replace(String input, String oldW, String newW) {
        if (isNullOrEmpty(input)) {
            return getEmptyString();
        }

        return input.replaceAll(oldW, newW);
    }

    public int countMatches(String input, String key, String seperator, boolean match) {
        if (isNullOrEmpty(input)) {
            return -1;
        }

        if (match) {
            String[] array = input.split(seperator);
            int count = 0;

            for (String s : array) {
                if (s.equals(key)) {
                    count++;
                }
            }

            return count;
        }

        return StringUtils.countMatches(input, key);
    }

    public String reverse(String input) {
        if (isNullOrEmpty(input)) {
            return getEmptyString();
        }

        return new StringBuilder(input).reverse().toString();
    }

    public void clearStringBuilder(StringBuilder sb) {
        sb.setLength(0);
    }

    public boolean isNumber(String input) {
        char[] array = input.toCharArray();

        for (char item : array) {
            if (Character.isAlphabetic(item)) {
                return false;
            }
        }

        return true;
    }

    public boolean isText(String input) {
        char[] array = input.toCharArray();

        for (char item : array) {
            if (Character.isDigit(item)) {
                return false;
            }
        }

        return true;
    }

    public String arrayToString(String[] array, String split) {
        if (Objects.isNull(array)) {
            return getEmptyString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (!Objects.isNull(split)) {
                sb.append(split);

                if (i + 1 < array.length) {
                    sb.append(split);
                }
            }
        }

        return sb.toString();
    }

    public StringBuilder toStringBuilder(String input) {
        return new StringBuilder(input);
    }

    public StringBuilder arrayToStringBuilder(String[] array, String split) {
        return toStringBuilder(arrayToString(array, split));
    }

    public String[] removeElement(String[] array, int index, boolean exception) {
        if (Objects.isNull(array)) {
            return getEmptyArray();
        }

        if (index >= array.length) {
            if (exception) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                index = array.length;
            }
        } else {
            if (index < 0 || index >= array.length) {
                return array;
            }
        }

        String[] anotherArray = new String[array.length - 1];
        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }

            anotherArray[k++] = array[i];
        }

        return anotherArray;
    }

    public String[] removeElement(String[] array, String key) {
        if (Objects.isNull(array) || Objects.isNull(key)) {
            return getEmptyArray();
        }

        if (array.length > 0) {
            int index = -1;

            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(key)) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                String[] copy = (String[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);

                if (copy.length > 0) {
                    System.arraycopy(array, 0, copy, 0, index);
                    System.arraycopy(array, index + 1, copy, index, copy.length - index);
                }
                return copy;
            }
        }
        return array;
    }

}