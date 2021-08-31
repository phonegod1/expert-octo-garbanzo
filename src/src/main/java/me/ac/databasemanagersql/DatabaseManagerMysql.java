package me.ac.databasemanagersql;

import me.ac.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerMysql {
    private Connection conn = null;

    // Hostname
    private String dbHost;

    // Port -- Standard: 3306
    private String dbPort;

    // Databankname
    private String database;

    // Databank username
    private String dbUser;

    // Databank password
    private String dbPassword;

    private Main Money;

    public DatabaseManagerMysql(Main money) {
        this.Money= money;

        setupDatabase();
    }


    public boolean setupDatabase() {
        try {
            //Load Drivers
            Class.forName("com.mysql.jdbc.Driver");

            dbHost=Money.getConfig().getString("host");
            dbPort=Money.getConfig().getString("port");
            database=Money.getConfig().getString("databasename");
            dbUser=Money.getConfig().getString("username");
            dbPassword=Money.getConfig().getString("password");


            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
                    + dbPort + "/" + database + "?" + "user=" + dbUser + "&"
                    + "password=" + dbPassword+"&"+"autoReconnect=true");


        } catch (ClassNotFoundException e) {
            //System.out.println("Could not locate drivers!");
            //Money.log.severe("Could not locate drivers for mysql!");
            return false;
        } catch (SQLException e) {
            //System.out.println("Could not connect");
            //Money.log.severe("Could not connect to mysql database!");
            return false;
        }

        //Create tables if needed
        Statement query;
        try {
            query = conn.createStatement();

            String accounts = "CREATE TABLE IF NOT EXISTS `bc_accounts` (id int(10) AUTO_INCREMENT, player_name varchar(50) NOT NULL UNIQUE, balance DOUBLE(10,8) NOT NULL, PRIMARY KEY(id));";
            //String loans="CREATE TABLE IF NOT EXISTS `bc_loans` (id int(10) AUTO_INCREMENT, player1_name varchar(50) NOT NULL, player2_name varchar(50) NOT NULL, day DATETIME, PRIMARY KEY(id));";
            query.executeUpdate(accounts);
            //query.executeUpdate(loans);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        //Money.log.info("Mysql has been set up!");
        return true;
    }

    public Connection getConnection() {
        return conn;
    }


    public boolean closeDatabase() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
