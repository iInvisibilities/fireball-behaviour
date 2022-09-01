package me.invis.fireball.manager.constant.semiconstant;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class SettingsManager {

    private final ConfigurationSection settingsSection;

    private final String
            LIVE_SETTINGS = "LIVE-SETTINGS",
            SPEED = "SPEED",
            BREAK_BLOCKS = "BREAK-BLOCKS",
            CAUSE_FIRE = "CAUSE-FIRE",
            DAMAGE_SHOOTER = "DAMAGE-SHOOTER",
            PERFORMANCE_TIMEOUT = "PERFORMANCE.TIMEOUT",
            PERFORMANCE_SAFETY = "PERFORMANCE.SAFETY",
            KNOCKBACK = "KNOCKBACK",
            TRAIL_INVENTORY = "TRAIL-INVENTORY",
            GLOWING = "GLOWING",
            DAMAGE_OTHERS = "DAMAGE-OTHERS",
            REDIRECTION = "REDIRECTION",
            TNT_EXPLOSION_TYPE = "TNT-EXPLOSION-TYPE";

    private final Map<String, Object> settings;

    private double speed, fireballTimeout;
    private boolean
            liveConfig,
            breakBlocks,
            causeFire,
            damageShooter,
            isPerformanceSafe,
            knockback,
            trailInventory,
            glowing,
            damageOthers,
            redirection,
            tntExplosionType;

    public SettingsManager(ConfigurationSection settingsSection) {
        this.settingsSection = settingsSection;

        this.liveConfig = getSettingsSection().getBoolean(LIVE_SETTINGS);
        this.speed = getSettingsSection().getDouble(SPEED);
        this.breakBlocks = getSettingsSection().getBoolean(BREAK_BLOCKS);
        this.causeFire = getSettingsSection().getBoolean(CAUSE_FIRE);
        this.damageShooter = getSettingsSection().getBoolean(DAMAGE_SHOOTER);
        this.fireballTimeout = getSettingsSection().getDouble(PERFORMANCE_TIMEOUT);
        this.isPerformanceSafe = getSettingsSection().getBoolean(PERFORMANCE_SAFETY);
        this.knockback = getSettingsSection().getBoolean(KNOCKBACK);
        this.trailInventory = getSettingsSection().getBoolean(TRAIL_INVENTORY);
        this.glowing = getSettingsSection().getBoolean(GLOWING);
        this.damageOthers = getSettingsSection().getBoolean(DAMAGE_OTHERS);
        this.redirection = getSettingsSection().getBoolean(REDIRECTION);
        this.tntExplosionType = getSettingsSection().getBoolean(TNT_EXPLOSION_TYPE);

        settings = getSettingsSection().getValues(true);
    }
    private ConfigurationSection getSettingsSection() {
        return settingsSection;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void updateValue(String key, Object value) {
        settings.put(key, value);

        switch (key) {
            case LIVE_SETTINGS:
                this.liveConfig = Boolean.parseBoolean(value.toString());
                break;
            case SPEED:
                this.speed = Double.parseDouble(value.toString());
                break;
            case BREAK_BLOCKS:
                this.breakBlocks = Boolean.parseBoolean(value.toString());
                break;
            case CAUSE_FIRE:
                this.causeFire = Boolean.parseBoolean(value.toString());
                break;
            case DAMAGE_SHOOTER:
                this.damageShooter = Boolean.parseBoolean(value.toString());
                break;
            case PERFORMANCE_TIMEOUT:
                this.fireballTimeout = Double.parseDouble(value.toString());
                break;
            case PERFORMANCE_SAFETY:
                this.isPerformanceSafe = Boolean.parseBoolean(value.toString());
                break;
            case KNOCKBACK:
                this.knockback = Boolean.parseBoolean(value.toString());
                break;
            case TRAIL_INVENTORY:
                this.trailInventory = Boolean.parseBoolean(value.toString());
                break;
            case GLOWING:
                this.glowing = Boolean.parseBoolean(value.toString());
                break;
            case DAMAGE_OTHERS:
                this.damageOthers = Boolean.parseBoolean(value.toString());
                break;
            case REDIRECTION:
                this.redirection = Boolean.parseBoolean(value.toString());
                break;
            case TNT_EXPLOSION_TYPE:
                this.tntExplosionType = Boolean.parseBoolean(value.toString());
                break;
            default:
                break;
        }
    }

    public boolean isLiveConfig() {
        return liveConfig;
    }
    public double getSpeed() {
        return speed;
    }
    public boolean isBreakBlocks() {
        return breakBlocks;
    }
    public boolean isCauseFire() {
        return causeFire;
    }
    public boolean isDamageShooter() {
        return damageShooter;
    }
    public double getFireballTimeout() {
        return fireballTimeout;
    }
    public boolean isPerformanceSafe() {
        return isPerformanceSafe;
    }
    public boolean isKnockback() {
        return knockback;
    }
    public boolean isTrailInventory() {
        return trailInventory;
    }
    public boolean isGlowing() {
        return glowing;
    }
    public boolean isDamageOthers() {
        return damageOthers;
    }
    public boolean isRedirection() {
        return redirection;
    }
    public boolean isTntExplosionType() {
        return tntExplosionType;
    }
}
