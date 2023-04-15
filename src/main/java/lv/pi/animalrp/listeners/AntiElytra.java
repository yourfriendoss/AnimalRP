package lv.pi.animalrp.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import lv.pi.animalrp.AnimalRP;

public class AntiElytra implements Listener {

    public AntiElytra() {
        JavaPlugin plugin = AnimalRP.getProvidingPlugin(AnimalRP.class);

        new BukkitRunnable(){
            public void run(){
                for(Player p : plugin.getServer().getOnlinePlayers())
                    dequipElytra(p);
            }
        }.runTaskTimer(plugin, 20 * 5, 20 * 5);
    }
    
    private void message(Player player) {
        player.sendMessage(AnimalRP.mm.deserialize("<red>Elytras are not allowed to be worn. You <green>can still trade with them <red>as they might have a use in the future."));
    }
    @EventHandler
    public void onGlide(EntityToggleGlideEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER))
            dequipElytra((Player) event.getEntity());
    }
    
    private int firstEmptyFromBack(PlayerInventory inv) {
        int z = -1;

        for(int i = 35; i >= 9; i--) {
            if(inv.getItem(i) == null) {
                z = i;
                break;
            }
        }

        return z;
    }

    private void dequipElytra(Player player) {
        PlayerInventory i = player.getInventory();
        if (!( (i.getChestplate() != null) && i.getChestplate().getType().equals(Material.ELYTRA))) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack elytra = i.getChestplate();
        i.setChestplate(null);
        message(player);

        // inventory full?
        if (firstEmptyFromBack(i) != -1) {
            i.setItem(firstEmptyFromBack(i), elytra);
            player.updateInventory();
        } else {
            Location l = i.getLocation();
            l.getWorld().dropItemNaturally(l, elytra);
            player.updateInventory();
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action a = event.getAction();
        if(a == Action.PHYSICAL || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) return;

        if(event.getItem() == null) return;
        if(event.getItem().getType() != Material.ELYTRA) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        message(event.getPlayer());
        dequipElytra(event.getPlayer());
    }
}