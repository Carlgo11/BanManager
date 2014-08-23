package com.carlgo11.ban.player;

import com.carlgo11.ban.Main;
import com.carlgo11.ban.Mysql;
import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {
    
    private Main plugin;

    public JoinEvent(Main plug)
    {
        this.plugin = plug;
    }
    
    @EventHandler
    public void onJoin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(isValid(p.getUniqueId().toString())){
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, MessageFormat.format("Banned by: {0}{1} \n     {2} \n\"{3}\"", ChatColor.GREEN, Mysql.getString(p.getUniqueId().toString(), 6), ChatColor.YELLOW, Mysql.getString(p.getUniqueId().toString(), 3)));
        }else{
            e.allow();
        }
    }
    
    boolean isValid(String UUID){
        
        if(Mysql.ifPlayerBanned(UUID)){
            return true;
        }else{
        return false;
        }
    }

}
