package com.carlgo11.ban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mysql {

    public static String url;
    public static String username;
    public static String password;
    public static String database;
    public static String table;

    public static boolean addBan(String User, String UUID, String Reason, int time, String format, String banner)
    {
        Connection con = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);

            if (!ifPlayerBanned(UUID)) { // Insert new ban

                PreparedStatement ps = con.prepareStatement("INSERT INTO `"+Mysql.database+"`.`"+Mysql.table+"` (`name`, `UUID`, `reason`, `time`, `timeformat`, `banner`) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, User);
                ps.setString(2, UUID);
                ps.setString(3, Reason);
                ps.setInt(4, time);
                ps.setString(5, format);
                ps.setString(6, banner);
                System.out.println(ps.toString());
                ps.execute();
            } else { // Update existing ban
                PreparedStatement ps = con.prepareStatement("UPDATE " + Mysql.table + " SET `reason` = ?, `time` = ?, `timeformat` = ?, `banner` = ? WHERE `bans`.`UUID` = ?;");
                ps.setString(1, Reason);
                ps.setInt(2, time);
                ps.setString(3, format);
                ps.setString(4, banner);
                ps.setString(5, UUID);

            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return true;
    }

    public static void delBan(String UUID)
    {
        Connection con = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);

            PreparedStatement ps = con.prepareStatement("DELETE FROM " + Mysql.table + " WHERE `UUID` = ?");
            ps.setString(1, UUID);
            ps.execute();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static boolean ifPlayerBanned(String UUID)
    {
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            PreparedStatement ps = con.prepareStatement("SELECT * from " + Mysql.table + " where `UUID` = ? ");
            ps.setString(1, UUID);
            rs = ps.executeQuery();

            while (true) {
                if (rs.next()) {
                    int r = Main.time(rs.getString(5), realtime(rs.getString(5), rs.getInt(4)));
                    System.out.println(r + "\t" + rs.getInt(4));
                    if (r < rs.getInt(4)) {
                        return true;
                    }else{
                        delBan(UUID);
                    }
                } else {
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    static int realtime(String m, int amount)
    {
        int outp = -1;
        int time = (int) (System.currentTimeMillis() / 1000);
        if (m.equalsIgnoreCase("h")) {
            outp = time / (3600 * amount);
        } else if (m.equalsIgnoreCase("d")) {
            outp = time / (24 * 3600 * amount);
        } else if (m.equalsIgnoreCase("s")) {
            outp = time / amount;
        }

        return outp;
    }

    public static String getString(String UUID, int var)
    {
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            PreparedStatement ps = con.prepareStatement("SELECT * from " + Mysql.table + " where `UUID` = ?");
            ps.setString(1, UUID);
            rs = ps.executeQuery();
            while (true) {
                if (rs.next()) {
                    return rs.getString(var);
                } else {
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return null;
    }
}
