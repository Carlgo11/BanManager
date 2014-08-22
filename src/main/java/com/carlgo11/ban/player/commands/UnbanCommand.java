package com.carlgo11.ban.player.commands;

import com.carlgo11.ban.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(args.length == 1){
            this.unban(sender, cmd, commandLabel, args);
        }else{
            this.help(sender, cmd, commandLabel, args);
        }
        
        return true;
    }
    
    void help(CommandSender sender, Command cmd, String commandLabel, String[] args){
        
    }
    
    void unban(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(sender.hasPermission("ban.unban")){
            Mysql.delBan(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
        }else{
            
        }
    }
}
