package me.invis.fireball.runnable;

import me.invis.fireball.event.ConfigChangeEvent;
import me.invis.fireball.manager.constant.semiconstant.SettingsManager;
import me.invis.fireball.util.collection.MapUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class LiveConfigChecker extends BukkitRunnable {

    private final Consumer<String> log;
    private final Consumer<Void> reloadConfig;
    private final SettingsManager settingsManager;
    private final Function<Void, ConfigurationSection> settingsSection;

    public LiveConfigChecker(
            Consumer<String> log,
            Consumer<Void> reloadConfig,
            SettingsManager settingsManager,
            Function<Void, ConfigurationSection> settingsSection) {
        this.log = log;
        this.reloadConfig = reloadConfig;
        this.settingsManager = settingsManager;
        this.settingsSection = settingsSection;
    }

    private void log(String string) {
        log.accept(string);
    }

    @Override
    public void run() {
        reloadConfig.accept(null);
        ConfigurationSection settingsSection = this.settingsSection.apply(null);

        if(!settingsSection.getBoolean("LIVE-SETTINGS")) {
            log("");
            log("Live settings disabled.");
            log("If you want to enable it, set \"LIVE-SETTINGS\" to true in config.yml");
            log("And restart the server.");
            log("");
            cancel();
            return;
        }

        Map<String, Object> milkedNewSettings = MapUtil.distinct(settingsSection.getValues(true), settingsManager.getSettings());
        if(!milkedNewSettings.isEmpty()) {
            milkedNewSettings.forEach((key, value) -> Bukkit.getPluginManager().callEvent(new ConfigChangeEvent(key, value)));
        }
    }
}
