package me.invis.fireball.command;

import me.invis.fireball.Fireball;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }
        if(Fireball.getSettingsManager().isTrailInventory()) {
            Fireball.getInventoryManager().openForPlayer((Player) sender);
            return true;
        }
        sender.sendMessage(ChatColor.RED + "This feature is not enabled!");

        return true;
    }
}
