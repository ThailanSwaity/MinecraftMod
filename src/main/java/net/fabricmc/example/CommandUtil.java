package net.fabricmc.example;

public class CommandUtil {

    public static boolean isNumber(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) return false;
        }
        return true;
    }

}
