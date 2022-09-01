package me.invis.fireball;

import me.invis.fireball.command.TrailsCommand;
import me.invis.fireball.listener.ConfigChangeListener;
import me.invis.fireball.listener.PlayerShootListener;
import me.invis.fireball.manager.FireballManager;
import me.invis.fireball.manager.TrailManager;
import me.invis.fireball.manager.constant.CooldownManager;
import me.invis.fireball.manager.constant.InventoryManager;
import me.invis.fireball.manager.constant.semiconstant.SettingsManager;
import me.invis.fireball.runnable.LiveConfigChecker;
import me.invis.fireball.util.ItemBuilder;
import me.invis.fireball.util.string.StringUtil;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class Fireball extends JavaPlugin {

    private static CooldownManager cooldownManager;
    private static SettingsManager settingsManager;
    private static FireballManager fireballManager;
    private static TrailManager trailManager;
    private static InventoryManager inventoryManager;
    private static NamespacedKey fireballKey;
    private static boolean isCooldown;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        isCooldown = CooldownManager.isEnabled(getConfig());
        if(isCooldown) {
            cooldownManager = new CooldownManager(this, getConfig(), new ArrayList<>());

            log("Cooldown enabled");
        }

        settingsManager = new SettingsManager(getConfig().getConfigurationSection("SETTINGS"));
        fireballManager = new FireballManager(this, getCooldownManager(), getSettingsManager(), new HashMap<>());

        Bukkit.getPluginManager().registerEvents(new PlayerShootListener(), this);
        Bukkit.getPluginManager().registerEvents(getFireballManager(), this);
        Bukkit.getPluginManager().registerEvents(new ConfigChangeListener(), this);

        if(getSettingsManager().isLiveConfig()) {
            new LiveConfigChecker(
                    this::log, (non) -> reloadConfig(), getSettingsManager(),
                    (non) -> getConfig().getConfigurationSection("SETTINGS")
            ).runTaskTimer(this, 20, 20);

            log("~ SEMI-STABLE~ Live settings enabled");
        }

        fireballKey = new NamespacedKey(this, "fireballTrail");
        trailManager = new TrailManager();

        if(getSettingsManager().isTrailInventory()) {
            initInventory();
            Objects.requireNonNull(getCommand("fireballtrails")).setExecutor(new TrailsCommand());

            log("Trail inventory enabled");
        }
    }

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static FireballManager getFireballManager() {
        return fireballManager;
    }

    public static TrailManager getTrailManager() {
        return trailManager;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static boolean isCooldown() {
        return isCooldown;
    }

    public static NamespacedKey getFireballKey() {
        return fireballKey;
    }

    private void initInventory() {
        inventoryManager = new InventoryManager(this, Bukkit.createInventory(null, 54, "Fireball trails"));

        List<Particle> particles =
                Arrays.stream(Arrays.copyOfRange(Particle.values(), 0, 53))
                        .filter(particle ->
                                        particle != Particle.BLOCK_DUST &&
                                        particle != Particle.BLOCK_CRACK &&
                                        particle != Particle.ITEM_CRACK &&
                                        particle != Particle.REDSTONE &&
                                        particle != Particle.FALLING_DUST
                                )
                        .collect(Collectors.toList());


        for (Particle particle : particles) {
            ItemStack particleItem = new ItemBuilder(Material.PAPER)
                    .setName(StringUtil.prettify(particle.name())).toItemStack();

            inventoryManager.addItem(particleItem, (clickEvent) -> getTrailManager().addTrailPropForPlayer(clickEvent.getClicker(), particle.toString()));
        }

        ItemStack clearItem = new ItemBuilder(Material.BARRIER).setName(ChatColor.RED + "Clear trail").toItemStack();

        inventoryManager.addItem(clearItem, (clickEvent) -> clickEvent.getClicker().getPersistentDataContainer().remove(getFireballKey()));
    }

    public void log(String s) {
        getLogger().info(s);
    }
}
