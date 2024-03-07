package de.gunnablescum.raldisystem.commands;

import de.gunnablescum.raldisystem.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player) {
            if(!player.isOp()) {
                player.sendMessage("§cYou don't have permissions for this.");
                return true;
            }
        }
        Main.loadConfig();
        commandSender.sendMessage("§aReload success.");
        return false;
    }
}
