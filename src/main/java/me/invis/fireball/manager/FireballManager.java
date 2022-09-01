package me.invis.fireball.manager;

import me.invis.fireball.manager.constant.CooldownManager;
import me.invis.fireball.manager.constant.semiconstant.SettingsManager;
import me.invis.fireball.util.SpiralCalculator;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class FireballManager implements Listener {

    private final Plugin plugin;
    private final CooldownManager cooldownManager;
    private final SettingsManager settingsManager;
    private  final Map<Entity, Player> fireballs;

    public FireballManager(
            Plugin plugin,
            CooldownManager cooldownManager,
            SettingsManager settingsManager,
            Map<Entity, Player> fireballs) {

        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
        this.settingsManager = settingsManager;
        this.fireballs = fireballs;
    }
    private Plugin getPlugin() {
        return plugin;
    }
    private CooldownManager getCooldownManager() {
        return cooldownManager;
    }
    private SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void shootFireBall(Player player, ItemStack item, boolean notCreativeMode) {
        if(me.invis.fireball.Fireball.isCooldown() && !getCooldownManager().handlePlayer(player)) return;

        Fireball ball = player.launchProjectile(Fireball.class,
                player.getLocation().getDirection().clone()
                        .multiply(getSettingsManager().getSpeed()));

        if(!getSettingsManager().isCauseFire()) ball.setIsIncendiary(false);
        if(getSettingsManager().isGlowing()) ball.setGlowing(true);
        if(getSettingsManager().isTntExplosionType()) ball.setYield(4F);

        fireballs.put(ball, player);

        if(notCreativeMode) {
            item.setAmount(item.getAmount() - 1);
            player.getInventory().setItemInMainHand(item);
        }

        if(getSettingsManager().isTrailInventory()) {
            if(player.getPersistentDataContainer().has
                    (me.invis.fireball.Fireball.getFireballKey(), PersistentDataType.STRING)) {
                Particle playerTrail =
                        Particle.valueOf(player.getPersistentDataContainer().get(me.invis.fireball.Fireball.getFireballKey(), PersistentDataType.STRING));

                SpiralCalculator.spawnHelix(getPlugin(), playerTrail, ball, player);
            }
        }

        if(me.invis.fireball.Fireball.isCooldown()) getCooldownManager().addPlayer(player);

        if(getSettingsManager().isPerformanceSafe()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(!ball.isDead() || !ball.isOnGround() || ball.isValid()) {
                        fireballs.remove(ball);
                        ball.remove();
                    }
                }

            }.runTaskLater(getPlugin(), (long) (getSettingsManager().getFireballTimeout() * 20));
        }
    }

    @EventHandler
    private void onExplosion(EntityExplodeEvent event) {
        if(!fireballs.containsKey(event.getEntity())) return;
        if(!getSettingsManager().isBreakBlocks()) event.blockList().clear();

        fireballs.remove(event.getEntity());
    }

    @EventHandler
    private void onRedirection(EntityDamageByEntityEvent event) {
        if(getSettingsManager().isRedirection()) return;
        if(!(event.getEntity() instanceof Fireball)) return;
        if(!fireballs.containsKey(event.getEntity())) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        if(!fireballs.containsKey(event.getDamager())) return;

        if(fireballs.get(event.getDamager()).equals(event.getEntity())) {
            if(!getSettingsManager().isDamageShooter()) event.setDamage(0);
        }
        else {
            if(!getSettingsManager().isDamageOthers()) event.setCancelled(true);
        }

        if(!getSettingsManager().isKnockback()) {
            double damage = event.getDamage();
            event.setCancelled(true);

            try {
                ((Damageable)event.getEntity()).setHealth(((Damageable)event.getEntity()).getHealth() - damage);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
