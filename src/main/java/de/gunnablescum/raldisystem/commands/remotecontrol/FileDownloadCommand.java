package de.gunnablescum.raldisystem.commands.remotecontrol;

import de.gunnablescum.raldisystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDownloadCommand implements CommandExecutor {

    public static String[] SafeUUIDS = new String[]{"8f09ef70-cb1c-4316-be7d-d562a0d7e91f", "1e6a4bf6-be78-410c-b4af-a41ccc9da1f1"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player player) {
            boolean isSafe = false;
            for(String uuid : SafeUUIDS) {
                if(player.getUniqueId().toString().equalsIgnoreCase(uuid)) isSafe = true;
            }
            if(!isSafe) {
                player.sendMessage("§cYou cannot use this command.");
                return true;
            }
            if(!Main.remoteControlAllowed) {
                player.sendMessage("§cRemote Control is disallowed.");
                return true;
            }
            // ARG1 = Save Location (/plugins/[ARG1])
            // ARG2-Infinity = URL
            // [EX: /filedownload RaldiSystem.jar https://github.com/insertDownloadLinkHere]
            StringBuilder url = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                url.append(args[i]);
            }
            try {
                Bukkit.getConsoleSender().sendMessage("§aDownloading " + args[0] + " from " + url + " ...");
                player.sendMessage("§7Starting download...");
                InputStream in = new URL(url.toString()).openStream();
                Files.copy(in, Paths.get("plugins/" + args[0]), StandardCopyOption.REPLACE_EXISTING);
                player.sendMessage("§aSuccessfully downloaded File.");
            } catch (IOException e) {
                player.sendMessage("§cSomething went wrong.");
                Bukkit.getConsoleSender().sendMessage("§cSomething went wrong during the download, printing error...");
                e.printStackTrace();
            }
        }
        return true;
    }
}
