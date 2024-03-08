package de.gunnablescum.raldisystem.commands;

import de.gunnablescum.raldisystem.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player player) {
            if(!player.isOp()) {
                player.sendMessage("§cNo Permissions.");
                return true;
            }
        }
        if(args.length < 2) {
            commandSender.sendMessage("§cSyntax: /config [Parameter] [Value]");
            commandSender.sendMessage("§cExample: /config DeathBreakFactor 0.5");
        }
        String parameter = args[0];
        if(parameter.equalsIgnoreCase("RemoteControlAllowed")) {
            commandSender.sendMessage("§cBlacklisted Parameter, may not be changed ingame.");
            return true;
        }
        try {
            double data = Double.parseDouble(args[1]);
            Main.getInstance().setConfig(parameter, data);
            Main.getInstance().loadConfig();
            commandSender.sendMessage("§aUpdated and reloaded config.");
        } catch(NumberFormatException e) {
            commandSender.sendMessage("§cInvalid Data.");
        }
        return false;
    }
}
