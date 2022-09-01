package me.invis.fireball.manager.constant;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/*
 * Please don't use this as your main inventory manager
 * I made this code EXTREMELY customized for MY/this plugin's needs
 * So it's not going to be of any use for any other usage
 *
 * Example?
 * ItemClickEvent only gives you who clicked
 * Automatically closes inventory on any click
 *
 */

public class InventoryManager {

    private final Plugin plugin;
    private final Inventory inventory;
    private final Map<ItemStack, Consumer<ItemClickEvent>> items;

    public InventoryManager(Plugin plugin, Inventory inventory) {
        this.plugin = plugin;
        this.inventory = inventory;

        this.items = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new ItemClickListener(), getPlugin());
    }
    private Plugin getPlugin() {
        return plugin;
    }
    private Inventory getInventory() {
        return inventory;
    }

    public void openForPlayer(Player player) {
        player.openInventory(getInventory());
    }

    public void addItem(ItemStack itemStack, Consumer<ItemClickEvent> onClick) {
        items.put(itemStack, onClick);
        getInventory().setItem(getInventory().firstEmpty(), itemStack);
    }

    public static class ItemClickEvent {

        private final Player clicker;

        public ItemClickEvent(Player clicker) {
            this.clicker = clicker;
        }
        public Player getClicker() {
            return clicker;
        }

    }

    private class ItemClickListener implements Listener {

        @EventHandler
        private void onInventoryClick(InventoryClickEvent event) {
            if(!event.getInventory().equals(getInventory())) return;

            items.getOrDefault(event.getCurrentItem(), (itemClickEvent) -> {}).accept(
                    new ItemClickEvent((Player) event.getWhoClicked())
            );
            if(items.containsKey(event.getCurrentItem())) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
            }
        }

    }
}
