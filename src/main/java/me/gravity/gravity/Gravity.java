package me.gravity.gravity;

import Commands.ResetArrows;
import Commands.SpawnVehicleCommand;
import Listeners.ExtraListeners;
import Listeners.RightClickListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.gravity.gravity.FunctionUtils.*;

public final class Gravity extends JavaPlugin {
    private static Gravity plugin;
    @Override
    public void onEnable() {
        plugin = this;
        task.runTaskTimer(this, 1, 1);
        getServer().getPluginManager().registerEvents(new RightClickListener(),this);
        getServer().getPluginManager().registerEvents(new ExtraListeners(),this);
        getCommand("ResetArrows").setExecutor(new ResetArrows());
        getCommand("SpawnVehicle").setExecutor(new SpawnVehicleCommand());

    }
    BukkitRunnable task = new BukkitRunnable() {
        @Override
        public void run() {

            for (Player player:
                    Bukkit.getServer().getOnlinePlayers()) {
                Block block = BlockCheck(player);
                if(block != null){
                Entity arrow = getPassengerVehicle(player);
                double pitch = Math.toRadians(arrow.getLocation().getPitch());
                double yaw = Math.toRadians(arrow.getLocation().getYaw());
                double x = -Math.sin(yaw) * Math.cos(pitch);
                double y = 0;
                double z = Math.cos(yaw) * -Math.cos(pitch);
                Vector arrowDirection = new Vector(x, y, z);
                double distance = 1; // Distance to move the arrow away from the block
                Location arrowLocation = arrow.getLocation();
                arrowLocation.add(arrowDirection.multiply(distance));
                arrowLocation.setY(arrow.getLocation().getY() -0.5);
                spawnVehicle(player, arrowLocation, null);
                arrow.remove();
            }
        }}
    };

    public static Gravity getPlugin() {
        return plugin;
    }


}
