package lv.pi.animalrp.listeners;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lv.pi.animalrp.animals.Animal;
import lv.pi.animalrp.util.Mood;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.format.TextColor;
import lv.pi.animalrp.AnimalRP;

class CustomChatRenderer implements ChatRenderer {
    Animal animal;
    
    public CustomChatRenderer(Animal animal) {
        this.animal = animal;
    }

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message,
            @NotNull Audience viewer) {
        return Component
            .text("<")
            .append(sourceDisplayName.color(TextColor.fromHexString(animal.color)))
            .append(Component.text(">"))
            .appendSpace()
            .append(AnimalRP.mm.deserialize(animal.chatTransformations(AnimalRP.mm.serialize(message))));
    }
};

public class PlayerChat implements Listener {
    Random random = new Random();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncChatEvent event) {
        Animal animal = AnimalRP.users.get(event.getPlayer().getUniqueId());
        
        if(animal != null) { 
            if(AnimalRP.isChatModOff.get(event.getPlayer().getUniqueId())) return;

            if(random.nextDouble() < 0.08) {
                event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), animal.moodSounds.get(Mood.HAPPY), 10F, 1);
            }

            event.renderer(new CustomChatRenderer(animal));
        }
    }
}

