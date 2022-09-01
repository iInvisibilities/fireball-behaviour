package me.invis.fireball.manager.constant;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CooldownManager {

    private final Plugin plugin;
    private final FileConfiguration delayConfig;
    private final List<Player> cooldowns;

    public CooldownManager(Plugin plugin, FileConfiguration delayConfig, List<Player> cooldowns) {
        this.plugin = plugin;
        this.delayConfig = delayConfig;
        this.cooldowns = cooldowns;
    }
    private Plugin getPlugin() {
        return plugin;
    }
    private FileConfiguration getDelayConfig() {
        return delayConfig;
    }
    private List<Player> getCooldowns() {
        return cooldowns;
    }

    public static boolean isEnabled(FileConfiguration delayConfig) {
        return delayConfig.getBoolean("DELAY.ENABLED");
    }

    private double getDelay() {
        return getDelayConfig().getDouble("DELAY.AMOUNT");
    }

    private String getString() {
        return getDelayConfig().getString("DELAY.STRING.VALUE");
    }

    private boolean checkPlayer(Player player) {
        return getCooldowns().contains(player);
    }

    private boolean stringEnabled() {
        return getDelayConfig().getBoolean("DELAY.STRING.ENABLED");
    }

    public boolean handlePlayer(Player player) {
        boolean isInCooldown = checkPlayer(player);
        if(isInCooldown && stringEnabled()) {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', getString().replace("$delay", Double.toString(getDelay())))
            );
        }

        return !isInCooldown;
    }

    public void addPlayer(Player player) {
        getCooldowns().add(player);

        new BukkitRunnable() {

            @Override
            public void run() {
                getCooldowns().remove(player);
            }
        }.runTaskLater(getPlugin(), (long) (getDelay() * 20));
    }


}
