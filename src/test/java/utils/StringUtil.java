package utils;

public class StringUtil {

    public static String generateStringOfDigits(long upperBorder) {
        return String.valueOf((long) (Math.random() * (upperBorder - 1)) + 1);
    }

    public static long generateNumber(long upperBorder) {
        return (long) (Math.random() * (upperBorder - 1)) + 1;
    }
}
