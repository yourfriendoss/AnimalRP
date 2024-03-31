package lv.pi.animalrp.commands;

import org.bukkit.command.CommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.util.Cooldown;

public class ClearCooldownCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry console. :(</gray>"));
            return true;
        }
        
        Player player = (Player)sender;

        if(!player.isOp()) {
            sender.sendMessage(AnimalRP.mm.deserialize("<red>You are not an OP!"));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(AnimalRP.mm.deserialize("<red>Include player!"));
            return true;
        }

        String playerName = args[0];
        OfflinePlayer of = Bukkit.getOfflinePlayer(playerName);
        if(of == null) {
            sender.sendMessage(AnimalRP.mm.deserialize("<red>User has never joined."));
            return true;
        }

        Cooldown cooldown = AnimalRP.cooldowns.get(of.getUniqueId());
        
        if(cooldown == null) {
            sender.sendMessage(AnimalRP.mm.deserialize("<red>User does not have a cooldown set."));
            return true;
        }

        AnimalRP.cooldowns.remove(of.getUniqueId());
        sender.sendMessage(AnimalRP.mm.deserialize("<red>Removed " + of.getName() + "'s cooldown (type: " + cooldown.type + ")!"));
        return true;
    }
}
