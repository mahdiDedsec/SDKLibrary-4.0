package com.sdk.sdklibrary4.cli;

import java.awt.*;

public class ConsoleForeground {
    public static final String RESET = "\u001b[0m";
    public static final String BLACK = "\u001b[0;30m";
    public static final String RED = "\u001b[0;31m";
    public static final String GREEN = "\u001b[0;32m";
    public static final String YELLOW = "\u001b[0;33m";
    public static final String BLUE = "\u001b[0;34m";
    public static final String PURPLE = "\u001b[0;35m";
    public static final String CYAN = "\u001b[0;36m";
    public static final String WHITE = "\u001b[0;37m";
    public static final String BLACK_BOLD = "\u001b[1;30m";
    public static final String RED_BOLD = "\u001b[1;31m";
    public static final String GREEN_BOLD = "\u001b[1;32m";
    public static final String YELLOW_BOLD = "\u001b[1;33m";
    public static final String BLUE_BOLD = "\u001b[1;34m";
    public static final String PURPLE_BOLD = "\u001b[1;35m";
    public static final String CYAN_BOLD = "\u001b[1;36m";
    public static final String WHITE_BOLD = "\u001b[1;37m";
    public static final String BLACK_UNDERLINED = "\u001b[4;30m";
    public static final String RED_UNDERLINED = "\u001b[4;31m";
    public static final String GREEN_UNDERLINED = "\u001b[4;32m";
    public static final String YELLOW_UNDERLINED = "\u001b[4;33m";
    public static final String BLUE_UNDERLINED = "\u001b[4;34m";
    public static final String PURPLE_UNDERLINED = "\u001b[4;35m";
    public static final String CYAN_UNDERLINED = "\u001b[4;36m";
    public static final String WHITE_UNDERLINED = "\u001b[4;37m";
    public static final String BLACK_BRIGHT = "\u001b[0;90m";
    public static final String RED_BRIGHT = "\u001b[0;91m";
    public static final String GREEN_BRIGHT = "\u001b[0;92m";
    public static final String YELLOW_BRIGHT = "\u001b[0;93m";
    public static final String BLUE_BRIGHT = "\u001b[0;94m";
    public static final String PURPLE_BRIGHT = "\u001b[0;95m";
    public static final String CYAN_BRIGHT = "\u001b[0;96m";
    public static final String WHITE_BRIGHT = "\u001b[0;97m";
    public static final String BLACK_BOLD_BRIGHT = "\u001b[1;90m";
    public static final String RED_BOLD_BRIGHT = "\u001b[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\u001b[1;92m";
    public static final String YELLOW_BOLD_BRIGHT = "\u001b[1;93m";
    public static final String BLUE_BOLD_BRIGHT = "\u001b[1;94m";
    public static final String PURPLE_BOLD_BRIGHT = "\u001b[1;95m";
    public static final String CYAN_BOLD_BRIGHT = "\u001b[1;96m";
    public static final String WHITE_BOLD_BRIGHT = "\u001b[1;97m";

    public static String RGBToHex(int red, int green, int blue) {
        return String.format("#%02x%02x%02x", red, green, blue);
    }

    public static int[] hexToRGB(String hex) {
        try {
            Color color = new Color(
                    Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                    Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                    Integer.valueOf( hex.substring( 5, 7 ), 16 ) );

            int[] rgb = new int[3];

            rgb[0] = color.getRed();
            rgb[1] = color.getGreen();
            rgb[2] = color.getBlue();

            return rgb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
