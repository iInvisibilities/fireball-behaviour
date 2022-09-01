package me.invis.fireball.listener;

import me.invis.fireball.Fireball;
import me.invis.fireball.event.ConfigChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.ServerOperator;

public class ConfigChangeListener implements Listener {

    @EventHandler
    private void onConfigChange(ConfigChangeEvent event) {
        Bukkit.getOnlinePlayers().stream()
                .filter(ServerOperator::isOp)
                .forEach(player ->
                        player.sendMessage(
                                new String[] {
                                        ChatColor.STRIKETHROUGH + "-------------------------------------",
                                        ChatColor.GREEN + "Fireball configuration file changed!",
                                        ChatColor.DARK_AQUA + "Changed section: " + ChatColor.RESET + event.getKey(),
                                        ChatColor.DARK_AQUA + "New value: " + ChatColor.RESET + event.getValue(),
                                        ChatColor.STRIKETHROUGH + "-------------------------------------"
                                }));
        Fireball.getSettingsManager().updateValue(event.getKey(), event.getValue());
    }

}
