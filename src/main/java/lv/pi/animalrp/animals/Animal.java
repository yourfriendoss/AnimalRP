package lv.pi.animalrp.animals;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Mood;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public abstract class Animal implements Listener {
    public String name;
    public String catchphrase;
    public String color;
    public HashMap<Mood, Sound> moodSounds = new HashMap<Mood, Sound>();
    public ArrayList<Material> superfoods = new ArrayList<Material>();
    
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        Animal animal = AnimalRP.users.get(player.getUniqueId());
        if(animal == null) return;
        if(animal.name != this.name) return;
        
        if(event.getItem() != null) {
            if(this.superfoods.contains(event.getItem().getType())) {
                player.setFoodLevel(player.getFoodLevel() + 4);
                player.setSaturation(player.getSaturation() + 9.4f);
                PotionEffect eff = player.getPotionEffect(PotionEffectType.SPEED);
                Integer duration = 20*4;
                
                if(eff != null) { // This stacks!
                    duration += eff.getDuration();
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1, true));
            }
        }
    }

    Animal(String name, String catchphrase, String color) {
        this.name = name; this.catchphrase = catchphrase; this.color = color;
    } 
    
    public abstract String chatTransformations(String message);
}
