package lv.pi.animalrp.animals;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Mood;
import lv.pi.animalrp.util.TextDestroyer;

public class Fox extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        "yap",
        "*yap yap*",
        "*beeps*",
        "*barks*",
        "*screeches*",
        ":3"
    }, new String[][]{
        {"you", "u"},
        {"o", "yo"},
        {"i", "yi"},
        {"!", " !"},
        {"?", " ?"}
    });
    Random rand = new Random();
    
    public Fox() {
        super("fox", "Yap!", "#FF8000");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_FOX_SNIFF);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_FOX_SLEEP);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_FOX_SNIFF);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_FOX_AGGRO);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_FOX_BITE);

        this.superfoods.add(Material.SWEET_BERRIES);
        this.superfoods.add(Material.GLOW_BERRIES);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            Animal animal = AnimalRP.users.get(player.getUniqueId());
        
            if(animal == null) return;
            if(animal.name != this.name) return;
        
            if(event.getCause() == DamageCause.ENTITY_ATTACK && event.getEntity().getType() != EntityType.PLAYER) {
                event.setDamage(event.getDamage()*1.25);
            }
        }
    }

    @Override
    public String chatTransformations(String message) {
        return this.destroyer.destroy(message);
    }

    @Override
    public Location movementTransformations(Location location) {
        return location;
    }
    
}