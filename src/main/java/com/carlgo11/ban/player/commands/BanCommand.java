package com.carlgo11.ban.player.commands;

import com.carlgo11.ban.Main;
import com.carlgo11.ban.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanCommand implements CommandExecutor {

    /*
     args: 0        1 2 3...
     //ban Carlgo11 1 d Griefing very much
     cmd user     time amount amount Reason
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (args.length == 0) {
            help(sender, cmd, commandLabel, args);
        } else if (args.length == 1) {

        } else if (args.length > 2) {
            //if (Mysql.ifPlayerBanned(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())) {
                int am = Integer.parseInt(args[1]);
                int t = Integer.parseInt(args[1]);


                /* String User, String UUID, String Reason, int time, String banner */
                if (Mysql.addBan(args[0].toString(), Bukkit.getPlayer(args[0]).getUniqueId().toString(), reason(args), Main.time(args[2], t), args[2], sender.getName().toString())) {
                    sender.sendMessage(ChatColor.GREEN + args[0] + " banned for " + t + args[2]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Error banning the user. Please inform a server owner!");
                }

           /* } else {
                sender.sendMessage("already banned");
            }*/
        }
        return true;
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
