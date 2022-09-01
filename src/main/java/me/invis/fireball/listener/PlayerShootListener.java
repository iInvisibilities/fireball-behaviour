package me.invis.fireball.listener;

import me.invis.fireball.Fireball;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerShootListener implements Listener {

    @EventHandler
    private void onRightClick(PlayerInteractEvent event) {
        if(!event.getAction().toString().contains("RIGHT")) return;
        if(event.getItem() == null) return;
        if(
                !event.getItem().getType().toString().contains("FIREBALL") &&
                !event.getItem().getType().toString().contains("FIRE_CHARGE")
        ) return;

        event.setCancelled(true);
        Fireball.getFireballManager().shootFireBall(
                event.getPlayer(),
                event.getItem(),
                event.getPlayer().getGameMode() != GameMode.CREATIVE
        );
    }

}
