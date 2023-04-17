package lv.pi.animalrp.animals;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Mood;
import lv.pi.animalrp.util.TextDestroyer;

public class Cat extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        ">_<", ":3", "ʕʘ‿ʘʔ", ":D", "._.",
        ";3", "xD", "ㅇㅅㅇ",
        ">_>", "ÙωÙ", "UwU", "OwO", ":P",
        "(◠‿◠✿)", "^_^", ";_;",
        "x3", "(• o •)", "<_<"
    }, new String[][]{
        {"l", "w"},
        {"r", "w"},
        {"th", "d"},
        {"L", "W"},
        {"R", "W"},
        {"TH", "D"}
    });

    public Cat() {
        super("cat", "Nya~", "#F2BDCD");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_CAT_PURR);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_CAT_PURREOW);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_CAT_AMBIENT);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_CAT_STRAY_AMBIENT);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_CAT_HISS);

        this.superfoods.add(Material.COOKED_COD);
        this.superfoods.add(Material.COD);
        this.superfoods.add(Material.COOKED_SALMON);
        this.superfoods.add(Material.SALMON);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player)event.getEntity();
        Animal animal = AnimalRP.users.get(player.getUniqueId());
        if(animal == null) return;
        if(animal.name != this.name) return;
        if(event.getCause() == DamageCause.FALL) {
            event.setDamage(event.getDamage() - 5);
        }
    }
    
    @Override
    public String chatTransformations(String message) {
        return destroyer.destroy(message);
    }
}