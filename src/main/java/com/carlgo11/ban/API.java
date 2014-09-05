package com.carlgo11.ban;

import java.util.UUID;
import org.bukkit.Bukkit;

public class API {

    private Main Main;

    public void setup(Main m)
    {
        this.Main = m;
    }

    public static boolean isBanned(UUID uuid)
    {
        if (Mysql.ifPlayerBanned(uuid.toString())) {
            return true;
        }
        return false;
    }

    public static boolean addBan(UUID uuid, int time, String format, UUID banner, String reason)
    {
        if (Bukkit.getPlayer(banner).hasPermission("ban.ban")) {
            Mysql.addBan(Bukkit.getOfflinePlayer(uuid).getName(), uuid.toString(), reason, time, format, Bukkit.getOfflinePlayer(banner).getName());
            return true;
        }
        return false;
    }

    public static String getReason(UUID uuid)
    {
        return Mysql.getString(Bukkit.getOfflinePlayer(uuid).getName(), 3);
    }

    public static String getBanner(UUID uuid)
    {
        return Mysql.getString(Bukkit.getOfflinePlayer(uuid).getName(), 6);
    }
}
