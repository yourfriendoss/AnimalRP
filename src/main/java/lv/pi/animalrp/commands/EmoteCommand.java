package lv.pi.animalrp.commands;

import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Emote;
import lv.pi.animalrp.util.Emote.Emotes;

public class EmoteCommand implements CommandExecutor {
    private void options(Player player) {
        player.sendMessage(AnimalRP.mm.deserialize("<red>You have <green>" + String.join(", ", Emote.emotes.keySet().stream().map(Emote.Emotes::name).map(String::toLowerCase).collect(Collectors.toSet())) + "</green> as options."));
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
        if(!(arg0 instanceof Player)) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry console. :(</gray>"));
            return true;
        }
        
        Player player = (Player)arg0;

        if(arg3.length == 0) {
            options(player);
            return true;
        }

        String emoteString = arg3[0];
        Emote.Emotes emote;
        
        try {
            emote = Emotes.valueOf(emoteString.toUpperCase());
        } catch(Exception e) {
            emote = null;
        }

        if(emote == null) {
            options(player);
            return true;
        }
        
        if(AnimalRP.emotes.isPlayerEmoting(player.getUniqueId())) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<red>Stopped emoting."));
            AnimalRP.emotes.stopEmote(player.getUniqueId());
        } else {
            arg0.sendMessage(AnimalRP.mm.deserialize("<green>Emoting!"));
            AnimalRP.emotes.playEmote(player.getUniqueId(), emote);
        }

        return true;
    }
    
}
