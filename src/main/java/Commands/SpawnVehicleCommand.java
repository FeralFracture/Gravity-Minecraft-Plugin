package Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.gravity.gravity.FunctionUtils.getPassengerVehicle;
import static me.gravity.gravity.FunctionUtils.spawnVehicle;

public class SpawnVehicleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player p){
            try{getPassengerVehicle(p).remove();} catch(Exception ex){}
            Location entityLocation = p.getLocation().clone().add(0, 1, 0);
            spawnVehicle(p, entityLocation, null);
        }
        return true;
    }
}
