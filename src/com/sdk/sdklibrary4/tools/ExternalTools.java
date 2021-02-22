package com.sdk.sdklibrary4.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.sdklibrary4.storage.TextFile;
import org.apache.commons.lang3.ArrayUtils;
import org.w3c.dom.Text;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

public class ExternalTools {

    public static Integer[] toIntegerArray(int[] array) {
        Integer[] numbers = new Integer[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Float[] toFloatArray(float[] array) {
        Float[] numbers = new Float[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Double[] toDoubleArray(double[] array) {
        Double[] numbers = new Double[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Long[] toLongArray(long[] array) {
        Long[] numbers = new Long[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Short[] toShortArray(short[] array) {
        Short[] numbers = new Short[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Byte[] toByteArray(byte[] array) {
        Byte[] numbers = new Byte[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Boolean[] toBooleanArray(boolean[] array) {
        Boolean[] numbers = new Boolean[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static Character[] toCharacterArray(char[] array) {
        Character[] numbers = new Character[array.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = array[i];
        }

        return numbers;
    }

    public static String[] toStringArray(Object[] array) {
        String[] tmp = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[i] = array[i].toString();
        }

        return tmp;
    }

    public static String[] toStringArray(Number[] numbers) {
        String[] tmp = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            tmp[i] = String.valueOf(numbers[i]);
        }

        return tmp;
    }

    public static boolean toJsonFile(Object object, String path, boolean append, boolean nextLine) {
        try {
            if (!path.endsWith(".json")) {
                path = path.concat(".json");
            }

            ObjectMapper mapper = new ObjectMapper();
            TextFile file = new TextFile(path);

            if (append) {
                if (!file.exist()) {
                    file.create();
                }

                if (nextLine) {
                    file.append("\n");
                }

                file.append(mapper.writeValueAsString(object));
                return file.append("\n");
            }

            return file.write(mapper.writeValueAsString(object));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean toXmlFile(Object object, String path, boolean append, boolean nextLine) {
        try {
            if (!path.endsWith(".xml"))  {
                path = path.concat(".xml");
            }

            TextFile textFile = new TextFile(path);

            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(object, sw);

            if (append) {
                if (!textFile.exist()) {
                    textFile.create();
                }

                if (nextLine) {
                    textFile.append("\n");
                }

                textFile.append(sw.toString());
                return textFile.append("\n");
            }

            return textFile.write(sw.toString());
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean compileSourceCode(String path) {
        try {
            return ToolProvider.getSystemJavaCompiler().
                    run(null, null, null, path) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}