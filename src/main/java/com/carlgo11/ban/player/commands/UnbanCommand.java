package com.carlgo11.ban.player.commands;

import com.carlgo11.ban.Main;
import com.carlgo11.ban.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {

    private Main Main;

    public UnbanCommand(Main plug)
    {
        this.Main = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (args.length == 1) {
            this.unban(sender, cmd, commandLabel, args);
        } else {
            this.help(sender, cmd, commandLabel, args);
        }

        return true;
    }

    void help(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {

    }

    void unban(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (sender.hasPermission("ban.unban")) {
            if (Mysql.ifPlayerBanned(Bukkit.getOfflinePlayer(args[0]).toString())) {
                Mysql.delBan(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
                Main.broadcastMessage(args[0] + " unbanned by " + sender.getName());
            } else {
                Main.error(sender, "That player is not banned");
            }
        } else {
            Main.badPerms(sender);
        }
    }
}
