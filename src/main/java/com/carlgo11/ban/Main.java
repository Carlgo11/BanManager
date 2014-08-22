package com.carlgo11.ban;

import com.carlgo11.ban.player.*;
import com.carlgo11.ban.player.commands.BanCommand;
import com.carlgo11.ban.player.commands.UnbanCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        commands();
    }
    
    public void commands(){
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
    }
    
    public static int time(String m, int amount)
    {
        int outp = -1;
        int time = (int) (System.currentTimeMillis() / 1000);
         if (m.equalsIgnoreCase("h")) {
            outp = time + amount * 3600 ;
        } else if (m.equalsIgnoreCase("d")) {
            outp = time + (24 * amount) * 3600;
        } else if (m.equalsIgnoreCase("s")) {
            outp = time + amount;
        }
        return outp;
    }

}
