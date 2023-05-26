package me.gravity.gravity;

import me.gravity.gravity.Gravity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class FunctionUtils {
    public static Entity getPassengerVehicle(Player player) {
        for (Entity entity : player.getNearbyEntities(2, 2, 2)) {
            if (entity.getPassengers().contains(player)) {
                return entity;
            }
        }
        return null;
    }
    public static void spawnVehicle(Player player, Location location, Entity entity) {
        double stop = 0.0001;
        boolean grounded = true;
        if(entity != null){
            stop = 0.4;
            grounded = false;
        }
        Arrow arrow = (Arrow) player.getWorld().spawnEntity(location, EntityType.ARROW);
        arrow.setGravity(false); // Disable gravity
        Vector v = location.getDirection().multiply(stop);
        v.multiply(new Vector(1, 1, -1));
        arrow.setVelocity(v);
        arrow.setSilent(true);
        arrow.setCustomName(""); // Set the custom name to an empty string
        arrow.setCustomNameVisible(false); // Hide the custom name
        arrow.setInvulnerable(true);
        arrow.addPassenger(player);
        arrow.setMetadata("canJump", new FixedMetadataValue(Gravity.getPlugin(), grounded));
    }

    public static void spawnBullet(Player p) {
        double speed = 1.2;
        Vector position = p.getEyeLocation().getDirection().multiply(1);
        Location entityLocation = p.getLocation().clone().add(position); // Adjust the offset as needed
        Arrow arrow = (Arrow) p.getWorld().spawnEntity(entityLocation.add(0, 1, 0), EntityType.ARROW);
        arrow.setGravity(false); // Disable gravity
        arrow.setSilent(true);
        arrow.setInvulnerable(true);
        Vector velocity = p.getEyeLocation().getDirection().multiply(speed);
        arrow.setVelocity(velocity);
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                arrow.setVelocity(velocity);
            }
        };
        task.runTaskTimer(Gravity.getPlugin(), 1, 1);
    }

    public static Block BlockCheck(Player p) {

        Location playerLocation = p.getLocation();
        BoundingBox playerBoundingBox = p.getBoundingBox();
        playerBoundingBox.expand(-0.2, -0.5, -0.2, -0.2, 1, -0.2);
        // Iterate over nearby blocks
        int radius = 2; // Adjust the radius as needed
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLocation = playerLocation.clone().add(x, y, z);
                    Block block = blockLocation.getBlock();

                    // Check if the player's bounding box intersects with the block's bounding box
                    BoundingBox blockBoundingBox = block.getBoundingBox();
                    if (playerBoundingBox.overlaps(blockBoundingBox) && block.getType().isSolid()) {
                        // Player has collided with a block
                        return block;
                    }
                }
            }
        }

        // Player has not collided with any block
        return null;
    }

    public static ArrayList<Arrow> findVehicles(Arrow vehicle) {
        ArrayList<Arrow> vehicles = new ArrayList<>();
        for (Entity entity : vehicle.getNearbyEntities(2.5, 2.5, 2.5)) {
            if(entity instanceof Arrow arrow) {
                if (!arrow.getPassengers().isEmpty()) {
                    vehicles.add(arrow);
                }
            }
        }
        return vehicles;
    }
}
