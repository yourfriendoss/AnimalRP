package lv.pi.animalrp.animals;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Mood;
import lv.pi.animalrp.util.TextDestroyer;


public class Dog extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        "Woof!", "Bark :3",
        "Arf", "bark bark bark", "arf~"
    }, new String[][]{
    });


    public Dog() {
        super("dog", "Arf!", "#ff8c00");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_WOLF_AMBIENT);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_WOLF_STEP);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_WOLF_WHINE);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_WOLF_SHAKE);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_WOLF_GROWL);

        this.superfoods.add(Material.CHICKEN);
        this.superfoods.add(Material.BEEF);
        this.superfoods.add(Material.PORKCHOP);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            Animal animal = AnimalRP.users.get(player.getUniqueId());
        
            if(animal == null) return;
            if(animal.name != this.name) return;
            player.removePotionEffect(PotionEffectType.SPEED);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2));
        }
    }

    @Override
    public String chatTransformations(String message) {
        return destroyer.destroy(message);
    }
}