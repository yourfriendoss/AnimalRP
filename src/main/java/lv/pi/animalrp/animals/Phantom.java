package lv.pi.animalrp.animals;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;

import lv.pi.animalrp.util.Mood;
import lv.pi.animalrp.util.TextDestroyer;

public class Phantom extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        "*shows wings*",
        "*flap flap*",
        "*screech*",
        ":3"
    }, new String[][]{
        {"you", "u"},
        {"o", "sho"},
        {"i", "ish"},
        {"!", "! "},
        {"?", "? "}
    });
    Random rand = new Random();
    
    public Phantom() {
        super("phantom", "~screech~", "#FF8000");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_PHANTOM_FLAP);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_PHANTOM_SWOOP);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_PHANTOM_SWOOP);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_PHANTOM_HURT);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_PHANTOM_BITE);

        this.superfoods.add(Material.CHICKEN);
        this.superfoods.add(Material.BEEF);
        this.superfoods.add(Material.PORKCHOP);
    }

    @Override
    public String chatTransformations(String message) {
        return this.destroyer.destroy(message);
    }
}