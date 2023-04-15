package lv.pi.animalrp.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import lv.pi.animalrp.AnimalRP;

enum Yaw {
    NORTH,EAST,WEST,SOUTH;
}

class SexModel {
    ArmorStand as;
    Yaw yaw;
    int sexTicks = 0;
    boolean finished = false;
    Location playerLocation;

    public SexModel(ArmorStand as, Yaw yaw, Location playerLocation) {
        this.as = as; this.yaw = yaw; this.playerLocation = playerLocation;
    }

    public void tickSex(Player player) {
        player.teleport(this.playerLocation);

        this.sexTicks ++;
        if(this.sexTicks %2 == 0) {
            Yaw opposite = Yaw.NORTH;
            if(this.yaw == Yaw.NORTH) opposite = Yaw.SOUTH;
            if(this.yaw == Yaw.WEST) opposite = Yaw.EAST;
            if(this.yaw == Yaw.EAST) opposite = Yaw.WEST;
            if(this.yaw == Yaw.SOUTH) opposite = Yaw.NORTH;
            this.yaw = opposite;
            Location asl = this.as.getLocation();
            asl.add(SexCommand.getVector(this.yaw, 0.2));
            as.teleport(asl);
        }
        if(this.sexTicks == 30) {
            player.sendMessage(AnimalRP.mm.deserialize("<#FFC0CB>I'm about to.."));
        }
        if(this.sexTicks == 50) {
            player.sendMessage(AnimalRP.mm.deserialize("<#FFC0CB>cum!!"));
            player.spawnParticle(Particle.END_ROD, player.getLocation(), 999, 0, 0, 0);
            this.removeModel();
            finished = true;
        }
    }


    public void removeModel() {
        this.as.remove();
    }
}

public class SexCommand implements CommandExecutor {

    static public HashMap<UUID, SexModel> models = new HashMap<UUID, SexModel>();
    
    public SexCommand() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AnimalRP.getProvidingPlugin(AnimalRP.class), new Runnable() {
            @Override
            public void run() {
                Set<Entry<UUID, SexModel>> modelset = models.entrySet();

                for (Iterator<Entry<UUID, SexModel>> iterator = modelset.iterator(); iterator.hasNext(); ) {
                    Entry<UUID, SexModel> value = iterator.next();
                    Player plr = Bukkit.getPlayer(value.getKey());
                    if(plr == null || value.getValue().finished) {
                        value.getValue().removeModel();
                        iterator.remove();
                    } else {
                        value.getValue().tickSex(plr);
                    }
                }
            }
        }, 5, 5);
    }
    
    public static Location faceLocation(Entity entity, Location to) {
        if (entity.getWorld() != to.getWorld()) {
            return null;
        }
        Location fromLocation = entity.getLocation();

        double xDiff = to.getX() - fromLocation.getX();
        double yDiff = to.getY() - fromLocation.getY();
        double zDiff = to.getZ() - fromLocation.getZ();

        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D;
        if (zDiff < 0.0D) {
            yaw += Math.abs(180.0D - yaw) * 2.0D;
        }
        Location loc = entity.getLocation();
        loc.setYaw((float) (yaw - 90.0F));
        loc.setPitch((float) (pitch - 90.0F));
        return loc;
    }

    public static Yaw getYaw(Player player) {
        float yaw = player.getLocation().getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 315 || yaw < 45) {
            return Yaw.SOUTH;
        } else if (yaw < 135) {
            return Yaw.WEST;
        } else if (yaw < 225) {
            return Yaw.NORTH;
        } else if (yaw < 315) {
            return Yaw.EAST;
        }
        return Yaw.NORTH;
    }

    public static Vector getVector(Yaw yaw) {
        return getVector(yaw, 0.5);
    }

    public static Vector getVector(Yaw yaw, Double amount) {
        if(yaw == Yaw.NORTH) {
            return new Vector(0, 0, -amount);
        }
        
        if(yaw == Yaw.SOUTH) {
            return new Vector(0, 0, amount);
        }
        
        if(yaw == Yaw.EAST) {
            return new Vector(amount, 0, 0);
        }
        
        if(yaw == Yaw.WEST) {
            return new Vector(-amount, 0, 0);
        }
    
        return new Vector(0, 0,0);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
        if(!(arg0 instanceof Player)) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<gray>I'm sorry console. :(</gray>"));
            return true;
        }
        
        Player player = (Player)arg0;

        if(!player.isOp()) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<red>You are not an OP!"));
            return true;
        }

        if(arg3.length == 0) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<red>Include player!"));
            return true;
        }

        String playerName = arg3[0];
        OfflinePlayer of = Bukkit.getOfflinePlayer(playerName);
        if(of == null) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<red>User has never joined."));
            return true;
        }
        
        if(SexCommand.models.containsKey(player.getUniqueId())) {
            arg0.sendMessage(AnimalRP.mm.deserialize("<red>You are already in progress."));
            return true;
        }

        Yaw yaw = getYaw(player);

        Location loc = player.getLocation().add(0, -1, 0);
        loc.add(getVector(yaw));

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(of);
        head.setItemMeta(meta);
        as.getEquipment().setHelmet(head);
        as.setBasePlate(false);
        as.setArms(true);
        as.setGravity(false);
        as.setHeadPose(new EulerAngle(0.15, 0, 0));
        as.setInvulnerable(true);
        
        as.addEquipmentLock(EquipmentSlot.HEAD, LockType.ADDING);
        as.addEquipmentLock(EquipmentSlot.CHEST, LockType.ADDING);
        as.addEquipmentLock(EquipmentSlot.FEET, LockType.ADDING);
        as.addEquipmentLock(EquipmentSlot.LEGS, LockType.ADDING);
        as.addEquipmentLock(EquipmentSlot.OFF_HAND, LockType.ADDING);
        as.addEquipmentLock(EquipmentSlot.HAND, LockType.ADDING);

        Location facing = faceLocation(as, player.getEyeLocation());
        as.teleport(facing);

        SexCommand.models.put(player.getUniqueId(), new SexModel(as, yaw, player.getLocation()));
        return true;
    }
    
}