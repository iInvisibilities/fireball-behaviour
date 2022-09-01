package me.invis.fireball.util.string;

public class StringUtil {

    public static String prettify(String string) {
        return
                (string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase()).replaceAll("_", " ");
    }

}
