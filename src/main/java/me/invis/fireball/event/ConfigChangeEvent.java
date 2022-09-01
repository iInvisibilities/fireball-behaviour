package me.invis.fireball.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ConfigChangeEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    private final String key;
    private final Object value;

    public ConfigChangeEvent(String key, Object value) {
        super();

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
