package lv.pi.animalrp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import lv.pi.animalrp.AnimalRP;


public class Emote {
    public enum Emotes {
        HAPPY, UWU, RAWR, LOVE, RAWR2, HAPPY2
    }

    public static HashMap<Emotes, String[]> emotes = new HashMap<Emotes, String[]>();
    private HashMap<UUID, Emotes> players = new HashMap<UUID, Emotes>();

    static {
        emotes.put(Emotes.HAPPY, new String[]{
            "0010000000100",
            "0101000001010",
            "0000000000000",
            "0000011100000"
        });
        emotes.put(Emotes.UWU, new String[]{
            "0101000001010",
            "0010000000100",
            "0000000000000",
            "0000011100000"
        });

        emotes.put(Emotes.RAWR, new String[]{
            "0000100",
            "0100010",
            "0000100",
            "0100010",
            "0000100",
        });
        emotes.put(Emotes.RAWR2, new String[]{
            "001000000010",
            "010100000101",
            "001000000010",
            "000010101000",
            "000001010000",
        });
        emotes.put(Emotes.LOVE, new String[]{
            "0001001000",
            "0010000100",
            "0100001000",
            "0010000100",
            "0001001000",
        });
        emotes.put(Emotes.HAPPY2, new String[]{
            "010011100",
            "000010010",
            "000010010",
            "010011100"
        });
    }

    public Emote() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AnimalRP.getProvidingPlugin(AnimalRP.class), new Runnable() {
            @Override
            public void run() {
                Set<Entry<UUID, Emotes>> playerset = players.entrySet();

                for (Iterator<Entry<UUID, Emotes>> iterator = playerset.iterator(); iterator.hasNext(); ) {
                    Entry<UUID, Emotes> value = iterator.next();
                    Player plr = Bukkit.getPlayer(value.getKey());
                    if(plr == null) {
                        iterator.remove();
                    } else {
                        drawEmote(plr, value.getValue());
                    }
                }
            }
        }, 5, 5);
    }

    public void drawEmote(Player player, Emotes emote) {
        List<Location> locs = getEmoteLocs(player.getLocation(), emotes.get(emote), player.getLocation());
        for (Location loc : locs) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 0.5f);
            player.getWorld().spawnParticle(Particle.REDSTONE, loc, 15, 0, 0, 0, dustOptions);
        }
    }

    public static List<Location> getEmoteLocs(Location loc, String[] ttEmote, Location rotationLoc) {
        List<List<Location>> locations = new ArrayList<>();
        int y = 0;
        int distance = 10;
        for (String s : ttEmote) {
            List<Integer> emoteTextLine = textToEmote(s);
            List<Location> addLocs = new ArrayList<>();
            for (int x = 0; x < emoteTextLine.size(); x++) {
                float y2 = (float)y/((float)distance/2);
                float side = (((float)emoteTextLine.get(x)/distance)-((float)ttEmote[0].length())/2/distance);
                Vector rotation = rotationLoc.getDirection().normalize().setY(0);
                Vector rot = rotation.crossProduct(new Vector(0, side, 0));
                Location addLoc = loc.clone().add(rot);
                addLoc.setY(loc.getY()+((float)ttEmote.length/distance-y2+2.25));
                addLocs.add(addLoc);
            }
            locations.add(addLocs);
            y++;
        }

        List<Location> locs = new ArrayList<>();

        for (List<Location> listLocs : locations) {
            locs.addAll(listLocs);
        }


        return locs;
    }

    private static List<Integer> textToEmote(String textToEmote) {
        List<Integer> locs = new ArrayList<>();
        for (int i=0; i<textToEmote.length(); i++) {
            char l = textToEmote.charAt(i);
            String letter = String.valueOf(l);
            if (letter.equals("1")) {
                locs.add(i);
            }
        }
        return locs;
    }

    public void playEmote(UUID uuid, Emotes emote) {
        this.players.put(uuid, emote);     
    }

    public void stopEmote(UUID uuid) {
        this.players.remove(uuid);
    }

    public boolean isPlayerEmoting(UUID uuid) {
        return this.players.containsKey(uuid);
    }
}
