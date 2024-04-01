package lv.pi.animalrp.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lv.pi.animalrp.animals.Animal;
import lv.pi.animalrp.util.Mood;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import lv.pi.animalrp.AnimalRP;

class CustomChatRenderer implements ChatRenderer {
    String message;

    public CustomChatRenderer(String message) {
        this.message = message;
    }

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message,
            @NotNull Audience viewer) {
        return AnimalRP.mm.deserialize(this.message);
    }
};

public class PlayerChat implements Listener {
    Random random = new Random();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncChatEvent event) {
        final Animal animal = AnimalRP.users.get(event.getPlayer().getUniqueId());
        Boolean chatModOff = false;
        Team team = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer());

        if(animal != null) { 
            if(AnimalRP.isChatModOff.get(event.getPlayer().getUniqueId()) != null) {
                chatModOff = true;    
            } else {
                if(random.nextDouble() < 0.08) {
                    Bukkit.getScheduler().runTask(AnimalRP.getPlugin(AnimalRP.class), () -> {
                        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), animal.moodSounds.get(Mood.HAPPY), 10F, 1);
                    });
                }       
            }     
        }

        String format = "%prefix%teamColor%animalColor%name%suffix: %message";
        String message = format;
        if(AnimalRP.vaultChat != null) {
            message = message.replaceAll("%prefix", AnimalRP.vaultChat.getPlayerPrefix(event.getPlayer()));
            message = message.replaceAll("%suffix", AnimalRP.vaultChat.getPlayerSuffix(event.getPlayer()));
        } else {
            message = message.replaceAll("%prefix", "");
            message = message.replaceAll("%suffix", "");
        }
        message = message.replaceAll("%teamColor", team == null ? "" : "<" + team.color().asHexString() + ">");
        message = message.replaceAll("%animalColor", (animal != null && !chatModOff) ? "<" + animal.color + ">" : "");
        message = message.replaceAll("%name", event.getPlayer().getName());
        message = message.replaceAll("%message", (animal != null && !chatModOff) ? animal.chatTransformations(AnimalRP.mm.serialize(event.message())) : AnimalRP.mm.serialize(event.message()));

        event.renderer(new CustomChatRenderer(message));
    }
}

