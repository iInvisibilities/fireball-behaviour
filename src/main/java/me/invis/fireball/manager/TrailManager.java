package me.invis.fireball.manager;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class TrailManager {
    public TrailManager() {}

    public void addTrailPropForPlayer(Player player, String particle) {
        player.getPersistentDataContainer().set(
                me.invis.fireball.Fireball.getFireballKey(), PersistentDataType.STRING, particle);
    }
}
