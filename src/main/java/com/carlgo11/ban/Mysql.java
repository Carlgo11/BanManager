package com.carlgo11.ban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mysql {

    public static String url = "jdbc:mysql://localhost:3306/ban";
    public static String username = "Ban";
    public static String password = "H84VAp3BzBXhWxRR";

    public static boolean addBan(String User, String UUID, String Reason, int time, String format, String banner)
    {
        Connection con = null;
        Statement st = null;

        try {
            con = DriverManager.getConnection(Mysql.url, Mysql.username, Mysql.password);
            st = con.createStatement();
            if (!ifPlayerBanned(UUID)) { // Insert new ban
                st.execute("INSERT INTO `ban`.`bans` (`name`, `UUID`, `reason`, `time`, `timeformat`, `banner`) VALUES ('" + User + "', '" + UUID + "', '" + Reason + "', '" + time + "', '" + format + "', '" + banner + "');");
            } else { // Update existing ban
                st.execute("UPDATE `ban`.`bans` SET `reason` = '" + Reason + "', `time` = '" + time + "', `timeformat` = '" + format + "' `banner` = '" + banner + "' WHERE `bans`.`UUID` = '" + UUID + "';");
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
        Statement st = null;

        try {
            con = DriverManager.getConnection(Mysql.url, Mysql.username, Mysql.password);
            st = con.createStatement();
            st.execute("DELETE FROM `bans` WHERE `UUID` = '" + UUID + "'");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url, Mysql.username, Mysql.password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * from bans where `UUID`='" + UUID + "'");

            if (rs.next()) {
                int r = Main.time(rs.getString(5), realtime(rs.getString(5), rs.getInt(4)));
                System.out.println(r + "\t" + rs.getInt(4));
                if (r < rs.getInt(4)) {
                    System.out.println("true");
                    return true;
                } else {
                    System.out.println("false");
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
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
        System.out.println(m);
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
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url, Mysql.username, Mysql.password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * from bans where `UUID`='" + UUID + "'");
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
                if (st != null) {
                    st.close();
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
