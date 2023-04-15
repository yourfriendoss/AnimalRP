package lv.pi.animalrp.commands;

import org.bukkit.command.CommandExecutor;

import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lv.pi.animalrp.AnimalRP;
import lv.pi.animalrp.animals.Animal;
import lv.pi.animalrp.util.Mood;

public class InteractionCommand implements CommandExecutor {
    String toThem;
    String toYou;
    Mood mood;
    
    public InteractionCommand(Mood mood, String toThem, String toYou) {
        this.toThem = toThem;
        this.toYou = toYou;
        this.mood = mood;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry console. :(</gray>"));
            return true;
        }

        Player player = (Player)sender;
        
        Animal aplayer = AnimalRP.users.get(player.getUniqueId());

        if(aplayer == null) {
            player.sendMessage(AnimalRP.mm.deserialize("<gray>Only animals can interact with other animals :("));
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(AnimalRP.mm.deserialize("<gray>Include a user!"));
            return true;
        }

        Player splayer = Bukkit.getPlayer(args[0]);

        if(splayer == null) {
            player.sendMessage(AnimalRP.mm.deserialize("<gray>I can't find this player :("));
            return true;
        }

        if(splayer.getName() == player.getName()) {
            player.sendMessage(AnimalRP.mm.deserialize("<gray>You can't "+command+" yourself."));
            return true;
        }
        Animal asplayer = AnimalRP.users.get(splayer.getUniqueId());

        if(asplayer == null) {
            player.sendMessage(AnimalRP.mm.deserialize("<gray>"+splayer.getName() + " is not an animal! :("));
            return true;
        }
        
        splayer.sendMessage(AnimalRP.mm.deserialize(String.format(this.toThem, "<light_purple>"+player.getName()+"</light_purple>", "<italic><gray>"+aplayer.catchphrase+"</italic>")));
        player.sendMessage(AnimalRP.mm.deserialize(String.format(this.toYou, "<light_purple>"+splayer.getName()+"</light_purple>", "<italic><gray>"+asplayer.catchphrase+"</italic>")));
        player.getWorld().playSound(splayer.getLocation(), asplayer.moodSounds.get(this.mood), 1F, 1);
        return true;
    }
}
