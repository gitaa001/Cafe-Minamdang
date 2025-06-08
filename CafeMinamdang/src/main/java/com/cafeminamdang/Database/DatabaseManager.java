package com.cafeminamdang.Database;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseManager {
    public static final Logger logger = Logger.getLogger(DatabaseManager.class.getName()); 
    private static DatabaseManager instance;
    private Connection connection;
    public static final boolean DEVELOPMENT_MODE = true;

    private DatabaseManager(){
        if (DEVELOPMENT_MODE){
            try {
                DatabaseInitiator.tes();

                var dburl = DatabaseManager.class.getClassLoader().getResource("CafeMinamdang.db");
                System.out.println("Database URL: " + dburl);
                
                if (dburl == null) {
                    System.out.println("Database file not found!");
                    return;
                }

                File dbFile = new File(dburl.toURI());
                System.out.println("Database file path: " + dbFile.getAbsolutePath());
                System.out.println("File exists: " + dbFile.exists());
                System.out.println("File size: " + dbFile.length() + " bytes");
                String url = "jdbc:sqlite:" + dbFile.getPath();
                connection = DriverManager.getConnection(url);
                // logger.info("Connection to SQLite has been established");
    
                // String url = "jdbc:sqlite:CafeMinamdang.db";
                // connection = DriverManager.getConnection(url);
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON"); // Setup biar foreign key always on
                }
                // Method 1: Standard connection
                System.out.println("\n=== METHOD 1: Standard Connection ===");
                checkTables(connection, "Standard");
            } catch (SQLException e) {
                logger.info("Could not initialize database connection: " + e.getMessage());
            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            } 
        }
    }

    public static synchronized DatabaseManager getInstance(){
        if (instance == null){
            instance = new DatabaseManager();
        } 
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
                logger.info("berhasil menutup koneksi");
            } catch (SQLException e) {
                logger.info("Error : " + e.getMessage());
            }
        }
    }

    private static void checkTables(Connection conn, String methodName) throws SQLException {
        // Check settings Foreign key
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("PRAGMA foreign_keys")) {
                if (rs.next()) {
                    System.out.println("Foreign keys setting: " + rs.getInt(1));
                }
            }
            
            // Check tabel
            System.out.println("\n--- ALL Database Objects (" + methodName + ") ---");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master ORDER BY type")) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    String type = rs.getString("type");
                    String name = rs.getString("name");
                    System.out.println(count + ". " + type + ": " + name);
                }
                System.out.println("Total objects: " + count);
            }
            
            // Check tabel query beda
            System.out.println("\n--- Tables using different query (" + methodName + ") ---");
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_schema WHERE type='table'")) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    String name = rs.getString("name");
                    System.out.println(count + ". Table: " + name);
                }
                System.out.println("Total tables: " + count);
            } catch (SQLException e) {
                System.out.println("sqlite_schema query failed: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args){
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.closeConnection();
    }
}
