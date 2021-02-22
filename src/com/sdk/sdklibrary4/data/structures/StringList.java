package com.sdk.sdklibrary4.data.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.sdk.sdklibrary4.data.types.Strings;
import org.apache.commons.lang3.ArrayUtils;

public class StringList implements Iterable<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private boolean duplicateValues;
    private boolean match;

    private String[] elements;

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < elements.length && elements[currentIndex] != null;
            }

            @Override
            public String next() {
                return elements[currentIndex++];
            }

            @Override
            public void remove() {
            }
        };
        return it;
    }

    public StringList(boolean duplicateValues) {
        this.duplicateValues = duplicateValues;
        this.match = true;

        this.elements = new String[0];
    }

    @Override
    public boolean equals(Object obj) {
        try {
            if (Objects.isNull(obj)) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            return Arrays.equals(((StringList) obj).toArray(), elements);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
        }

        return sb.toString();
    }

    public boolean addAll(StringList list) {
        try {
            List<String> items = new ArrayList<>();

            for (int i = 0; i < elements.length; i++) {
                items.add(elements[i]);
            }

            for (String str : list.toArray()) {
                items.add(str);
            }

            elements = items.toArray(String[]::new);
            if (!duplicateValues) {
                eraseDuplicates();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDuplicateValues() {
        return duplicateValues;
    }

    public int countDuplicates(String key) {
        return Collections.frequency(Arrays.asList(elements), key);
    }

    public boolean eraseDuplicates() {
        try {
            elements = new HashSet<>(Arrays.asList(elements)).toArray(String[]::new);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String get(int index) {
        return elements[index];
    }

    public void set(int index, String value) {
        if (!contains(value)) {
            elements[index] = value;
        } else {
            if (duplicateValues) {
                elements[index] = value;
            }
        }
    }

    public String[] toArray() {
        return elements;
    }

    public boolean isEmpty() {
        return elements.length == 0 ? true : false;
    }

    public boolean clear() {
        try {
            elements = new String[0];
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int size() {
        return elements.length;
    }

    public boolean contains(String key) {
        return indexOf(key) != -1;
    }

    public StringList add(Object value) {
        String[] items = new String[elements.length + 1];
        System.arraycopy(elements, 0, items, 0, elements.length);

        items[items.length - 1] = value.toString();
        elements = items;

        if (!duplicateValues) {
            eraseDuplicates();
        }

        return this;
    }

    public StringList add(String[] values) {
        if (Objects.isNull(values)) {
            return this;
        }

        elements = ArrayUtils.addAll(elements, values);

        if (!duplicateValues) {
            eraseDuplicates();
        }

        return this;
    }

    public boolean remove(String element) {
        try {
            if (!contains(element)) {
                return false;
            }

            List<String> items = new ArrayList<>(Arrays.asList(elements));
            items.remove(element);

            elements = items.toArray(String[]::new);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int index) {
        return remove(get(index));
    }

    public void sort() {
        Arrays.sort(elements);
    }

    public int indexOf(String value) {
        for (int i = 0; i < elements.length; i++) {
            if (match) {
                if (elements[i].equals(value)) {
                    return i;
                }
            } else {
                if (elements[i].contains(value)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public void reverse() {
        Collections.reverse(Arrays.asList(elements));
    }

    public boolean writeToFile(String path) {
        return false;
    }

    public boolean readFromFile(String path) {
        return false;
    }

    public String getBiggestElement() {
        return Arrays.asList(elements).stream().max(Comparator.comparingInt(String::length)).get();
    }

    public String getSmallestElement() {
        return Arrays.asList(elements).stream().min(Comparator.comparingInt(String::length)).get();
    }

    public int getBiggestElementIndex() {
        return indexOf(getBiggestElement());
    }

    public int getSmallestElementIndex() {
        return indexOf(getSmallestElement());
    }

    public boolean toLowerCase() {
        try {
            for (int i = 0; i < elements.length; i++) {
                elements[i] = elements[i].toLowerCase();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean toUpperCase() {
        try {
            for (int i = 0; i < elements.length; i++) {
                elements[i] = elements[i].toUpperCase();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printList(boolean number, String starter) {
        for (int i = 0; i < elements.length; i++) {
            if (!new Strings().isNullOrEmpty(starter)) {
                System.out.print(starter);
            }

            if (number) {
                System.out.print("[" + (i + 1) + "] ");
            }

            System.out.println(elements[i]);
        }
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}