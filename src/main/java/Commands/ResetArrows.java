package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class ResetArrows implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
            // Check if the entity matches the desired type
            if (entity.getType() == EntityType.ARROW) {
                entity.remove();
            }
        }
        return true;
    }
}
