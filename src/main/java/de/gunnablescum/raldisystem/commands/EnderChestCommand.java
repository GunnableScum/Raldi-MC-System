package de.gunnablescum.raldisystem.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 3, 1);
            player.openInventory(player.getEnderChest());
            return true;
        } else {
            commandSender.sendMessage("You must do that ingame!");
        }
        return true;
    }

}
