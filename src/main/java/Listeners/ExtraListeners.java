package Listeners;

import me.gravity.gravity.Gravity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;

import static me.gravity.gravity.FunctionUtils.*;

public class ExtraListeners implements Listener {
    @EventHandler
    public void onPlayerDismount(EntityDismountEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void itemDropHandler(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        Entity vehicle = getPassengerVehicle(p);
        if (e.getItemDrop().getName().toString().toLowerCase().contains("iron sword") || e.getItemDrop().getName().toString().toLowerCase().contains("iron axe")){
            e.setCancelled(true);
            if(vehicle.getMetadata("canJump").get(0).asBoolean()) {
                Vector velocity = p.getEyeLocation().getDirection().multiply(1);
                vehicle.setVelocity(velocity);
                vehicle.setMetadata("canJump", new FixedMetadataValue(Gravity.getPlugin(), false));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArrowCollide(ProjectileHitEvent e) {

        e.setCancelled(true);
        Arrow arrow = (Arrow) e.getEntity();
        Player player;
        try {
            player = (Player) arrow.getPassengers().get(0);
            double pitch = Math.toRadians(arrow.getLocation().getPitch());
            double yaw = Math.toRadians(arrow.getLocation().getYaw());
            double x = -Math.sin(yaw) * Math.cos(pitch);
            double y = -Math.sin(pitch);
            double z = Math.cos(yaw) * -Math.cos(pitch);
            Vector arrowDirection = new Vector(x, y, z);
            double distance = 0.7;
            Location arrowLocation = arrow.getLocation();
            arrowLocation.add(arrowDirection.multiply(distance));
            spawnVehicle(player, arrowLocation, e.getHitEntity());
            arrow.remove();
            e.setCancelled(true);} catch (Exception ex) {
            ArrayList<Arrow> arrows = findVehicles(arrow);
            for (Arrow item:
                    arrows) {
                Location location1 = arrow.getLocation().add(arrow.getVelocity().normalize().multiply(3));
                Location location2 = item.getLocation();
                Vector direction = location2.toVector().subtract(location1.toVector()).normalize().multiply(0.8);
                item.setVelocity(direction);
                item.setMetadata("canJump", new FixedMetadataValue(Gravity.getPlugin(), false));
            }
            arrow.remove();
        }
    }
}
