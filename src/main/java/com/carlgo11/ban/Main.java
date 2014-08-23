package com.carlgo11.ban;

import com.carlgo11.ban.player.*;
import com.carlgo11.ban.player.commands.BanCommand;
import com.carlgo11.ban.player.commands.UnbanCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        commands();
        setupMysql();
    }

    public void commands()
    {
        getCommand("ban").setExecutor(new BanCommand(this));
        getCommand("unban").setExecutor(new UnbanCommand());
    }

    public static int time(String m, int amount)
    {
        int outp = -1;
        int time = (int) (System.currentTimeMillis() / 1000);
        if (m.equalsIgnoreCase("h")) {
            outp = time + amount * 3600;
        } else if (m.equalsIgnoreCase("d")) {
            outp = time + (24 * amount) * 3600;
        } else if (m.equalsIgnoreCase("s")) {
            outp = time + amount;
        }
        return outp;
    }

    void setupMysql()
    {
        Mysql.url = getConfig().getString("mysql.url");
        Mysql.username = getConfig().getString("mysql.username");
        Mysql.password = getConfig().getString("mysql.password");
        Mysql.database = getConfig().getString("mysql.database");
        Mysql.table = getConfig().getString("mysql.table");
    }

    public static void badPerms(CommandSender p)
    {
        p.sendMessage(ChatColor.RED + "[Error] You don't have permission to perform that action.");
    }

    public static void badPerms(Player p)
    {
        p.sendMessage(ChatColor.RED + "[Error] You don't have permission to perform that action.");
    }

    public static void broadcastMessage(String message, String permission)
    {

    }
    
}
