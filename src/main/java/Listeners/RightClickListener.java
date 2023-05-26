package Listeners;

import me.gravity.gravity.Gravity;
import me.gravity.gravity.FunctionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import static me.gravity.gravity.FunctionUtils.spawnBullet;


public class RightClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().toString().contains("RIGHT") &&
                p.getInventory().getItemInMainHand().getType().toString().contains("IRON_SWORD")) {
            //player.setGravity(false);
            Entity vehicle = FunctionUtils.getPassengerVehicle(p);
            if (vehicle != null) {
                double speedLimit = 0.6;
                double acceleration = 0.1;
                if (vehicle instanceof Arrow) {
                    Vector velocity = p.getEyeLocation().getDirection().multiply(acceleration);
                    if(!(vehicle.getVelocity().getX() < speedLimit && vehicle.getVelocity().getX() > -speedLimit)){
                        velocity.setX(0);
                    }
                    if(!(vehicle.getVelocity().getY() < speedLimit && vehicle.getVelocity().getY() > -speedLimit)){
                        velocity.setY(0);
                    }
                    if(!(vehicle.getVelocity().getZ() < speedLimit && vehicle.getVelocity().getZ() > -speedLimit)){
                        velocity.setZ(0);
                    }
                    vehicle.setVelocity(vehicle.getVelocity().add(velocity));
                    vehicle.setMetadata("canJump", new FixedMetadataValue(Gravity.getPlugin(), false));
                }
            }
        }
        if (e.getAction().toString().contains("RIGHT") &&
                p.getInventory().getItemInMainHand().getType().toString().contains("IRON_AXE")) {
            spawnBullet(p);
        }
    }


}
