package lv.pi.animalrp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lv.pi.animalrp.AnimalRP;

public class ChatModCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
        if(!(arg0 instanceof Player)) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry console. :(</gray>"));
            return true;
        }
        
        Player player = (Player)arg0;

        boolean off = false;
        if(arg2.endsWith("off")) {
            off = true;
        }

        boolean isDisabled = false;

        if(AnimalRP.isChatModOff.get(player.getUniqueId()) != null) {
            isDisabled = true;
        }

        if(isDisabled) { // chat modifications are turned off
            if(off) { // asking to be turned off
                arg0.sendMessage(AnimalRP.mm.deserialize("<red>Chat modifications for you are already disabled!"));
            } else { // asking to be turned on
                arg0.sendMessage(AnimalRP.mm.deserialize("<green>Chat modifications enabled!"));
                AnimalRP.isChatModOff.remove(player.getUniqueId());
            }
        } else { // chat modifications are turned on
            if(off) { // asking to be turned off
                arg0.sendMessage(AnimalRP.mm.deserialize("<red>Chat modifications disabled!"));
                AnimalRP.isChatModOff.put(player.getUniqueId(), true);
            } else { // asking to be turned on
                arg0.sendMessage(AnimalRP.mm.deserialize("<green>Chat modifications for you are already enabled!!"));
            }
        }
        return true;
    }
    
}
