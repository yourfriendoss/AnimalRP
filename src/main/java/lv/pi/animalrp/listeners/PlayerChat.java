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

import net.kyori.adventure.text.format.TextColor;
import lv.pi.animalrp.AnimalRP;

class CustomChatRenderer implements ChatRenderer {
    Animal animal;
    Team team;
    Component suffix;
    Component prefix;

    public CustomChatRenderer(Animal animal, Team team, Component suffix, Component prefix) {
        this.animal = animal;
        this.team = team;
        this.suffix = suffix;
        this.prefix = prefix;
    }

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message,
            @NotNull Audience viewer) {
        Component name = sourceDisplayName;

        if(team != null) {
            name = name.color(team.color());
        }

        if(animal != null) {
            name = name.color(TextColor.fromHexString(animal.color));
        }

        Component msg = message;

        if(animal != null) {
            msg = AnimalRP.mm.deserialize(animal.chatTransformations(AnimalRP.mm.serialize(message)));
        }

        return this.prefix
            .append(name)
            .append(this.suffix)
            .append(Component.text(":"))
            .appendSpace()
            .append(msg);
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

        Component csuffix = Component.text("");
        Component cprefix = Component.text("");

        if(AnimalRP.vaultChat != null) {
            String suffix = AnimalRP.vaultChat.getPlayerSuffix(event.getPlayer());
            String prefix = AnimalRP.vaultChat.getPlayerPrefix(event.getPlayer());

            if(suffix != null) {
                csuffix = AnimalRP.mm.deserialize(suffix);
            }

            if(prefix != null) {
                cprefix = AnimalRP.mm.deserialize(prefix);
            }
        }
        
        event.renderer(new CustomChatRenderer(chatModOff ? null : animal, team, csuffix, cprefix));
    }
}

