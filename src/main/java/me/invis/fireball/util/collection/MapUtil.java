package me.invis.fireball.util.collection;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static <K, V> Map<K, V> distinct(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> result = new HashMap<>();

        map1.forEach((key, value) -> {
            if(!map2.get(key).equals(value)) {
                if(!(value instanceof ConfigurationSection)) result.put(key, value);
            }
        });

        return result;
    }

}
