package com.sdk.sdklibrary4.data.structures;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.ArrayList;

import com.sdk.sdklibrary4.data.types.Strings;

public class NumberList<E extends Number> implements Iterable<E>, Serializable {

    private static final long serialVersionUID = -7372597984829488344L;
    private boolean duplicateValues;
    private Object[] elements;

    public NumberList(boolean duplicateValues) {
        this.duplicateValues = duplicateValues;
        this.elements = new Object[0];
    }

    private double getValue(int index) {
        return Double.parseDouble(elements[index].toString());
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < elements.length && elements[currentIndex] != null;
            }

            @Override
            public E next() {
                return (E) elements[currentIndex++];
            }

            @Override
            public void remove() {
            }
        };
        return it;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return Arrays.equals(((NumberList<E>) obj).toArray(), elements);
    }

    public boolean addAll(NumberList<E> list) {
        try {
            if (Objects.isNull(list)) {
                return false;
            }

            List<Object> items = new ArrayList<Object>();
            for (Object obj : list.toArray()) {
                items.add(obj);
            }

            for (int i = 0; i < elements.length; i++) {
                items.add(elements[i]);
            }

            elements = items.toArray();
            if (!duplicateValues) {
                eraseDuplicates();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    public boolean eraseDuplicates() {
        try {
            elements = new HashSet<>(Arrays.asList(elements)).toArray();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countDuplicates(E value) {
        return Collections.frequency(Arrays.asList(elements), value);
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elements[index];
    }

    public void set(int index, E value) {
        if (!contains(value)) {
            elements[index] = value;
        } else {
            if (duplicateValues) {
                elements[index] = value;
            }
        }
    }

    public Object[] toArray() {
        return elements;
    }

    public boolean isEmpty() {
        return elements.length == 0 ? true : false;
    }

    public boolean clear() {
        try {
            elements = new Object[0];
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int size() {
        return elements.length;
    }

    public boolean contains(E value) {
        return indexOf(value) != -1 ? true : false;
    }

    public NumberList<E> add(E element) {
        Object[] items = new Object[elements.length + 1];
        System.arraycopy(elements, 0, items, 0, elements.length);

        items[items.length - 1] = element;
        elements = items;

        sort();

        if (!duplicateValues) {
            eraseDuplicates();
        }

        return this;
    }

    public boolean remove(E element) {
        try {
            List<Object> items = new ArrayList<>(Arrays.asList(elements));
            items.remove(element);

            elements = items.toArray();
            sort();

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

    public int indexOf(E value) {
        sort();

        int index = Arrays.binarySearch(elements, value);
        return index < 0 ? -1 : index;
    }

    public double sum() {
        double sum = 0;

        for (int i = 0; i < elements.length; i++) {
            sum += Double.parseDouble(elements[i].toString());
        }

        return sum;
    }

    public double[] getEvens() {
        List<Double> evens = new ArrayList<>();

        for (int i = 0; i < elements.length; i++) {
            if (getValue(i) % 2 == 0) {
                evens.add(getValue(i));
            }
        }

        return ArrayUtils.toPrimitive(evens.toArray(Double[]::new));
    }

    public double[] getOdds() {
        List<Double> odds = new ArrayList<>();

        for (int i = 0; i < elements.length; i++) {
            if (getValue(i) % 2 != 0) {
                odds.add(getValue(i));
            }
        }

        return ArrayUtils.toPrimitive(odds.toArray(Double[]::new));
    }

    public String[] toStringArray() {
        return Arrays.stream(elements).map(Object::toString).toArray(String[]::new);
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

    @SuppressWarnings("unchecked")
    public E getBiggestElement() {
        sort();
        return (E) elements[elements.length - 1];
    }

    @SuppressWarnings("unchecked")
    public E getSmallestElement() {
        sort();
        return (E) elements[0];
    }

    @SuppressWarnings("unchecked")
    public int getBiggestElementIndex() {
        return indexOf((E) getBiggestElement());
    }

    public int getSmallestElementIndex() {
        return indexOf((E) getSmallestElement());
    }

    public double[] getArray(double number) {
        List<Double> list = new ArrayList<>();

        for (int i = 0; i < elements.length; i++) {
            if (getValue(i) == number) {
                list.add(getValue(i));
            }
        }

        return ArrayUtils.toPrimitive(list.toArray(Double[]::new));
    }

    public double[] inRangeOf(double number) {
        return null;
    }

}