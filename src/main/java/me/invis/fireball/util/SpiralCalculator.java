package me.invis.fireball.util;

import me.invis.fireball.Fireball;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpiralCalculator {

    /*
     * This code bas been passed down by private and relative hands
     * Never use this code for malicious intents!
     * You have been warned.
     */

    private static final int
            CIRCLE_POINTS = 2, ROTATION_SPEED = 40;
    private static final double
            RADIUS = 0.3;
    private static final double INCREMENT = (2 * Math.PI) / ROTATION_SPEED;

    public static void spawnHelix(Plugin plugin, Particle particle, Entity entity, Player shooter) {

        new BukkitRunnable() {
            final Location pLoc = shooter.getLocation();
            double circlePointOffset = 0;

            @Override
            public void run() {
                Location startLoc = entity.getLocation();
                for (double i1 = 0; i1 < Fireball.getSettingsManager().getSpeed(); i1+=.1) {
                    if(entity.isDead() || entity.isOnGround()) {
                        this.cancel();
                        return;
                    }

                    final double pitch = (pLoc.getPitch() +90.0F) * 0.017_453_292F;
                    final double yaw = -pLoc.getYaw() * 0.017_453_292F;

                    for (int i = 0; i < CIRCLE_POINTS; i++) {
                        double x =  RADIUS * Math.cos(2 * Math.PI * i / CIRCLE_POINTS + circlePointOffset);
                        double z =  RADIUS * Math.sin(2 * Math.PI * i / CIRCLE_POINTS + circlePointOffset);

                        Vector vec = new Vector(x, i1, z);
                        VectorUtil.rotateAroundAxisX(vec, pitch);
                        VectorUtil.rotateAroundAxisY(vec, yaw);

                        startLoc.add(vec);
                        shooter.spawnParticle(particle, startLoc, 0);
                        startLoc.subtract(vec);
                    }

                    circlePointOffset += INCREMENT;
                    if (circlePointOffset >= (2 * Math.PI)) {
                        circlePointOffset = 0;
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 0);

    }

}
