package DataBase;

import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.Calendar;

/**
 * Класс содержит функции для работы с базой данных
 */
public class DataBase {
    private Connection c = null;

    private Statement stmt = null;

    private static final Logger log = Logger.getLogger(DataBase.class);

    /** Устанавливает соединение с базой данных.
     * Перед подключением к базе данных производим проверку на её существование.
     * В зависимости от результата производим открытие базы данных или её восстановление
     */
    public void connectToDataBase() {
        if(!new File("database.db").exists()){
            if (!this.restoreDataBase()) {
                log.error("Tables not created");
                System.out.println("Tables not created");
            }
        } else {
            this.openDataBase();
        }
    }

    /**
     * Востановление базы данных.
     * Создается файл базы данных и таблицы.
     * @return false - файл или таблица не создались, true - успех)
     */
    private boolean restoreDataBase() {
        if (this.openDataBase()) {
            if (!this.createUsers()) {
                return false;
            } else {
                return true;
            }
        } else {
            log.error("Restore database failed");
            System.out.println("Restore database failed");
            return false;
        }
    }

    /**
     * Открытие базы данных или, создание и открытие.
     * @return false - возникло исключение при создании файла БД, true - в случае успеха
     */
    private boolean openDataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Opened database successfully");
        System.out.println("Opened database successfully");
        return true;
    }

    /**
     * Создание таблицы кораблей.
     * @return true - таблица успешно создана, false - исключение
     */
    private boolean createUsers() {
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE ShipsInfo " +
                    "(ID                TEXT           NOT NULL," +
                    " Loaded            TEXT           NOT NULL," +
                    " Unloaded           TEXT           NOT NULL," +
                    " Transgressions    TEXT           NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Table ShipInfo created successfully");
        System.out.println("Table ShipInfo created successfully");
        return true;
    }

    public void updateShipInfo(int shipID, int load, int unload, int transgression) {
        try {
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM ShipsInfo WHERE ID = '" + shipID + "';" );
            int Loaded = Integer.parseInt(rs.getString("Loaded"));
            int Unloaded = Integer.parseInt(rs.getString("Unloaded"));
            int Transgressions = Integer.parseInt(rs.getString("Transgressions"));

            Loaded += load;
            Unloaded += unload;
            Transgressions += transgression;

            String sql = "UPDATE ShipsInfo SET Loaded = " + Loaded + ", Unloaded = " + Unloaded + "," +
                    " Transgressions = " + Transgressions + " WHERE ID = '" + shipID+ "';";
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            log.error("Ship not updated");
            System.out.println("Ship not updated");
            return;
        }
    }

    public void registerShip(int ShipID) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO ShipsInfo (ID, Loaded, Unloaded, Transgressions) " +
                    "VALUES ('" + ShipID + "', '" + 0 + "', '" + 0 + "', '" + 0 + "');";
            stmt.executeUpdate(sql);

            stmt.close();
        } catch ( Exception e ) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        log.info("Registered ShipID: " + ShipID + "successfully");
        System.out.println("Registered ShipID: " + ShipID + " successfully");
    }

    public int getTransgressions(int ShipID) {
        try {
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM ShipsInfo WHERE ID = '" + ShipID + "';" );
            return Integer.parseInt(rs.getString("Transgressions"));

        } catch ( Exception e ) {
            log.error("Ship not found");
            System.out.println("Ship not found");
            return 0;
        }
    }

    /**
     * Закрывает базу данных.
     */
    public void closeDataBase() {
        try {
            c.close();
        } catch (SQLException e) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(e.getErrorCode());
        }
        log.info("Database closed successfully");
        System.out.println("Database closed successfully");
    }

    public boolean isRegistered(int ShipID) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ShipsInfo WHERE ID = '" + ShipID + "';" );
            if (rs.getString("ID") != null) {
                rs.close();
                stmt.close();
                return true;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            return false;
        }
        return false;
    }
}
