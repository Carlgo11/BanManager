package com.carlgo11.ban.player.commands;

import com.carlgo11.ban.Main;
import com.carlgo11.ban.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (args.length == 0) {
            return false;
        } else if (args.length == 1) {
            this.ban(sender, cmd, commandLabel, args, "1", "d");
        } else if (args.length > 2) {
            this.ban(sender, cmd, commandLabel, args, args[1], args[2]);
        }
        return true;
    }

    void ban(CommandSender sender, Command cmd, String commandLabel, String[] args, String time, String timeamount)
    {
        //if (Mysql.ifPlayerBanned(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())) {
            int t = Integer.parseInt(args[1]);

            if (Mysql.addBan(args[0].toString(), Bukkit.getPlayer(args[0]).getUniqueId().toString(), reason(args), Main.time(timeamount, t), timeamount, sender.getName().toString())) {
                sender.sendMessage(ChatColor.GREEN + args[0] + " banned for " + t + args[2]);
            } else {
                sender.sendMessage(ChatColor.RED + "Error banning the user. Please inform a server owner!");
            }
       /* } else {
            sender.sendMessage("already banned");
        }*/
    }

    void help(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        sender.sendMessage("nope");
    }

    String reason(String[] args)
    {
        if (args.length > 3) {

            StringBuilder d = new StringBuilder();
            for (int i = 3; i < args.length; i++) {
                d.append(args[i]);
                d.append(" ");
            }
            return d.toString();
        }
        return "You have been banned from the PortalCraft.Se Servers.";
    }
}
