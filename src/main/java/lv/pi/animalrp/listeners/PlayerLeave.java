package lv.pi.animalrp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import lv.pi.animalrp.commands.SexCommand;
import lv.pi.animalrp.commands.SexCommand.SexModel;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onLeavePlayer(PlayerQuitEvent event) {
        SexModel model = SexCommand.models.get(event.getPlayer().getUniqueId());
        if(model != null) {
            model.removeModel();
            SexCommand.models.remove(event.getPlayer().getUniqueId());
        }
    }
}
