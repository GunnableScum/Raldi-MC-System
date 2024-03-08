package de.gunnablescum.raldisystem.commands.remotecontrol;

import de.gunnablescum.raldisystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleCommand implements CommandExecutor {
    public static String[] SafeUUIDS = new String[]{"8f09ef70-cb1c-4316-be7d-d562a0d7e91f", "1e6a4bf6-be78-410c-b4af-a41ccc9da1f1"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            boolean isSafe = false;
            for (String uuid : SafeUUIDS) {
                if (player.getUniqueId().toString().equalsIgnoreCase(uuid)) isSafe = true;
            }
            if (!isSafe) {
                player.sendMessage("§cYou cannot use this command.");
                return true;
            }
            if(!Main.getInstance().remoteControlAllowed) {
                player.sendMessage("§cRemote Control is disallowed.");
                return true;
            }
            StringBuilder executecommand = new StringBuilder();
            for(int i = 0; i < args.length; i++) {
                executecommand.append(args[i]).append(" ");
            }
            player.sendMessage("§7Sending command...");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), executecommand.toString());
            player.sendMessage("§aDone.");
        }
        return true;
    }
}
