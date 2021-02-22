package com.sdk.sdklibrary4.data.types;

public class Numbers {

    private String toClockTime(int number) {
        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public long generateRandomNumber(long min, long max) {
        return (long) ((Math.random() * (max - min)) + min);
    }

    public String secondsToTime(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        String h = toClockTime(hours), m = toClockTime(minutes), s = toClockTime(seconds);
        StringBuilder sb = new StringBuilder();

        if (!h.equals("00")) {
            sb.append(h).append(" : ");
        }

        if (!m.equals("00")) {
            sb.append(m).append(" : ");
        }

        if (!s.equals("00")) {
            sb.append(s);
        } else {
            return "0";
        }

        return sb.toString();
    }

    public boolean isPerfect(int number) {
        int sum = 1;

        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                if (i * i != number)
                    sum = sum + i + number / i;
                else
                    sum = sum + i;
            }
        }

        if (sum == number && number != 1)
            return true;

        return false;
    }

    public boolean isPrime(int number) {
        return false;
    }

    public boolean isOdd(int number) {
        return number % 2 != 0 ? true : false;
    }

    public boolean isEven(int number) {
        return number % 2 == 0 ? true : false;
    }
}