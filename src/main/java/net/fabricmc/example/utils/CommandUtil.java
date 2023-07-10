package net.fabricmc.example.utils;

public class CommandUtil {

    public static boolean isNumber(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        try {
            double test = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
