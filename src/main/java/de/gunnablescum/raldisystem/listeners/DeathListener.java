package de.gunnablescum.raldisystem.listeners;

import de.gunnablescum.raldisystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setKeepInventory(true);
        event.setKeepLevel(false);
        double deathBreakFactor = Main.getInstance().deathBreakFactor;
        Player player = event.getEntity();
        List<String> affected = new ArrayList<>();
        for(ItemStack is : player.getInventory()) {
            if(is == null) continue;
            if(is.getItemMeta() instanceof Damageable damageable) {
                if(damageable.getDamage() == is.getType().getMaxDurability()-1) continue;
                int damage = (int) (damageable.getDamage() + (is.getType().getMaxDurability() * deathBreakFactor));
                if(damage > is.getType().getMaxDurability()) {
                    damage = is.getType().getMaxDurability()-1;
                }
                affected.add("§8- §c" + is.getType().name().toUpperCase() + " receives " + (int)is.getType().getMaxDurability()*deathBreakFactor + " damage.");
                damageable.setDamage(damage);
                is.setItemMeta(damageable);
            }
        }
        if(affected.isEmpty()) return;
        Bukkit.getConsoleSender().sendMessage("§e§lItem-Damage stats of " + player.getName() + "'s Death:");
        for(String str : affected) {
            Bukkit.getConsoleSender().sendMessage(str);
        }
    }

}
