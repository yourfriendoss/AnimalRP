package lv.pi.animalrp.animals;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Mood;
import lv.pi.animalrp.util.TextDestroyer;

class Cooldown {
    Integer duration;
    Long executionTime;
    public Cooldown(Integer duration, Long executionTime) {
        this.duration = duration;
        this.executionTime = executionTime;
    }

    public boolean isExpired() {
        return this.executionTime-(System.currentTimeMillis()-this.duration) <= 0;
    }
}

public class Bee extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        ">_<", "*buzz*",
        ";3", ":3", "εწз", " ≧◠◡◠≦ ", "*stings you*", "*humms*",
        "*i'm a bee*"
    }, new String[][]{
        {"e", "ee"},
        {"b", "bzz"},
        {"h", "hh"},
        {"ie", "ee"},
        {"be", "bee"},
        {"E", "EE"},
        {"B", "BZZ"},
        {"H", "HH"},
        {"IE", "EE"},
        {"BE", "BEE"}
    });

    ArrayList<UUID> sneakers = new ArrayList<UUID>();

    public Bee() {
        super("bee", "Buzz...", "#FFFF00");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_BEE_LOOP);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_BEE_LOOP);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_BEE_HURT);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_BEE_STING);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_BEE_LOOP_AGGRESSIVE);

        this.superfoods.add(Material.HONEY_BOTTLE);
        this.superfoods.add(Material.CHORUS_FLOWER);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Animal animal = AnimalRP.users.get(event.getPlayer().getUniqueId());
        if(animal == null) return;
        if(animal.name != this.name) return;

        if (event.isSneaking() && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            Player player = event.getPlayer();

            if (!sneakers.contains(player.getUniqueId())) {
                sneakers.add(player.getUniqueId());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(sneakers.contains(player.getUniqueId())) sneakers.remove(player.getUniqueId());
                    }
                }.runTaskLater(AnimalRP.getProvidingPlugin(Animal.class), 20);
            } else {
                sneakers.remove(player.getUniqueId());
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20*1, 5, true));
                player.getWorld().playSound(player.getLocation(), this.moodSounds.get(Mood.HAPPY), 1F, 1);
            }
        }
    }

    @Override
    public String chatTransformations(String message) {
        return destroyer.destroy(message);
    }

    @Override
    public Location movementTransformations(Location location) {
        return location;
    }
    
}