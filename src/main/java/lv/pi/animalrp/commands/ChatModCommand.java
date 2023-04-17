package lv.pi.animalrp.commands;

import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lv.pi.animalrp.AnimalRP;

// TODO: Unfinished
public class ChatModCommand implements CommandExecutor {
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

        boolean off = false;
        if(arg2.endsWith("off")) {
            off = true;
        }

        return true;
    }
    
}
