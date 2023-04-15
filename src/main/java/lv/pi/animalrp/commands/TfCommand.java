package lv.pi.animalrp.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.animals.Animal;
import lv.pi.animalrp.util.Cooldown;

public class TfCommand implements CommandExecutor {
    public void options(Player player) {
        ArrayList<String> parts = new ArrayList<String>();
        
        for (Entry<String,Animal> entry : AnimalRP.animals.entrySet()) {
            parts.add("<"+entry.getValue().color+">"+entry.getKey()+"</"+entry.getValue().color+">");
        }
        
        player.sendMessage(AnimalRP.mm.deserialize("<green>Looking around you, you have <dark_green>\"" + String.join(", ", parts) + "\"<green> as options."));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry, but you have to be a player to become an animal. :("));
            return true;
        }

        Player player = (Player)sender;
        
        if(args.length == 0) {
            if(AnimalRP.users.get(player.getUniqueId()) != null) {
                Animal previous = AnimalRP.users.get(player.getUniqueId());
                player.sendMessage(AnimalRP.mm.deserialize("<green>You start splitting apart, dropping your <blue>" + previous.name+"-like<green> appearence.."));
                AnimalRP.users.remove(player.getUniqueId());
                return true;
            } else {
                options(player); return true;

            }
        }

        String tf = args[0];

        Animal animal = AnimalRP.animals.get(tf);

        if(animal == null) {
            options(player); return true;
        }

        Cooldown cldn = AnimalRP.cooldowns.get(player.getUniqueId());

        if(cldn != null) {
            if(cldn.isExpired()) {
                cldn = null;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                player.sendMessage(AnimalRP.mm.deserialize("<red>You are still on cooldown. Time left: " + sdf.format(new Date(cldn.getTime()))));
                return true;
            }
        }

        if(AnimalRP.users.get(player.getUniqueId()) != null) {
            Animal previous = AnimalRP.users.get(player.getUniqueId());
            if(previous.name == animal.name) {
                player.sendMessage(AnimalRP.mm.deserialize("<gray>You're <blue>" + previous.name + "</blue> already! " + animal.catchphrase));
                return true;
            }
            player.sendMessage(AnimalRP.mm.deserialize("<green>You slowly transform, from <blue>" + previous.name + "<green> to.. <blue>" + animal.name + ". "+  animal.catchphrase));
        } else {
            player.sendMessage(AnimalRP.mm.deserialize("<green>You slowly transform, to.. <blue>" + animal.name + "<green>. "+  animal.catchphrase));
        }

        if(cldn == null) {
            cldn = new Cooldown();
            cldn.length = 21600000;
            cldn.timeCreated = System.currentTimeMillis();
            cldn.type = "tf-command";
            AnimalRP.cooldowns.put(player.getUniqueId(), cldn);
        }

        AnimalRP.users.put(player.getUniqueId(), animal);
        return true;
    }
    
}
