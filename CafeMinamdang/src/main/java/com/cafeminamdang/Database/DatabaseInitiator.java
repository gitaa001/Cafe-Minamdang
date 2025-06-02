package com.cafeminamdang.Database;

import java.sql.SQLException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.*;

import com.cafeminamdang.Database.*;

public class DatabaseInitiator {
    public static final Logger logger = Logger.getLogger(DatabaseInitiator.class.getName()); 


    public static Connection connect(){
        if (DatabaseManager.DEVELOPMENT_MODE){
            Connection conn = null;
            try {
                var dburl = DatabaseInitiator.class.getClassLoader().getResource("CafeMinamdang.db");
                logger.info("URL : " + dburl);

                if (dburl == null){
                    logger.info("Tidak ada database dengan nama tersebut");
                    return null;
                }

                var url = "jdbc:sqlite:" + new File(dburl.toURI()).getPath();
                logger.info("Path :" + url);

                conn = DriverManager.getConnection(url);
                logger.info("Connection to SQLite has been established");
                return conn;  

            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        } else {
            // Sementara ok!
            return null;
        }
    }

    public static void initializeTables() throws SQLException {
        try (Connection conn = connect()) {
            if (conn == null) {
                logger.severe("Cannot initialize tables: connection failed");
                return;
            }
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
                logger.info("Foreign keys enabled");
                
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Gudang (" +
                    "IDGudang INTEGER PRIMARY KEY," +
                    "NamaGudang VARCHAR(24) NOT NULL," +
                    "Lokasi TEXT NOT NULL," +
                    "DeskripsiGudang TEXT" +
                    ")"
                );
                logger.info("Gudang table created");
                
                stmt.execute(
                    "INSERT OR IGNORE INTO Gudang (IDGudang, NamaGudang, Lokasi, DeskripsiGudang) " +
                    "VALUES (1, 'Gudang Kasablanka', 'Jl. Ahmad Yani, No.54 Jakarta Timur', 'Gudang penyimpanan utama')"
                );
                stmt.execute(
                    "INSERT OR IGNORE INTO Gudang (IDGudang, NamaGudang, Lokasi, DeskripsiGudang) " +
                    "VALUES (2, 'Gudang Bekasi', 'Jl. Mawar, No.27 Bekasi Barat', 'Gudang penyimpanan utama')"
                );
                logger.info("Sample Gudang data added");
                
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Barang (" +
                    "IDBarang INTEGER PRIMARY KEY," +
                    "NamaBarang VARCHAR(24) NOT NULL," +
                    "Deskripsi TEXT NOT NULL," +
                    "Kuantitas INTEGER NOT NULL," +
                    "IsKonsinyasi BOOLEAN NOT NULL," +
                    "IDGudang INTEGER NOT NULL," +
                    "FOREIGN KEY(IDGudang) REFERENCES Gudang(IDGudang)" +
                    ")"
                );
                logger.info("Barang table created");
                
                stmt.execute(
                    "INSERT OR IGNORE INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                    "VALUES (1, 'Kopi Arabica', 'Kopi premium', 50, 0, 1)" 
                );
                stmt.execute(
                    "INSERT OR IGNORE INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                    "VALUES (2, 'Kopi Robusta', 'Kopi premium', 50, 0, 1)"
                );
                stmt.execute(
                    "INSERT OR IGNORE INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                    "VALUES (3, 'Kopi Pacamara', 'Kopi premium', 50, 1, 1)"
                );
                stmt.execute(
                    "INSERT OR IGNORE INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                    "VALUES (4, 'Kopi Liberica', 'Kopi premium', 50, 1, 1)"
                );
                stmt.execute(
                    "INSERT OR IGNORE INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                    "VALUES (5, 'Kopi Excelsa', 'Kopi premium', 50, 0, 1)"
                );
                logger.info("Sample Barang data added");

                
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Invoice (" +
                    "IDInvoice INTEGER PRIMARY KEY," +
                    "JudulInvoice VARCHAR(24) NOT NULL," +
                    "Tanggal DATE NOT NULL," +
                    "IDGudang INTEGER NOT NULL," +
                    "IDBarang INTEGER NOT NULL," +
                    "UnitPrice DECIMAL(10,2) NOT NULL," +
                    "FOREIGN KEY(IDGudang) REFERENCES Gudang(IDGudang)," +
                    "FOREIGN KEY(IDBarang) REFERENCES Barang(IDBarang)" +
                    ")"
                );
                logger.info("Invoice table created");
                
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Resep (" +
                    "IDResep INTEGER PRIMARY KEY," +
                    "NamaResep VARCHAR(24) NOT NULL," +
                    "Deskripsi TEXT NOT NULL," + 
                    "Preskripsi TEXT NOT NULL" +
                    ")"
                );
                logger.info("Resep table created");
                
                logger.info("All tables created successfully");
            }
        }
    }
    
    public static void main(String[] args){
        var dburl = DatabaseInitiator.class.getClassLoader().getResource("CafeMinamdang.db");
        
        if (dburl != null) {
            try {
                File dbFile = new File(dburl.toURI());
                
                // Display info 
                logger.info("Found database at: " + dbFile.getAbsolutePath());
                logger.info("File size: " + dbFile.length() + " bytes");
                
                // Testing purpose
                File outputDir = new File(dbFile.getParent());
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }
                
                // Testing purpose (Create empty database file)
                logger.info("Creating fresh database...");
                try (FileOutputStream fos = new FileOutputStream(dbFile)) {
                } catch (IOException e) {
                    logger.severe("Could not create fresh database: " + e.getMessage());
                }
                
                logger.info("Database reset complete");
                
            } catch (URISyntaxException e) {
                logger.severe("URI syntax error: " + e.getMessage());
            }
        } else {
            logger.info("Database file not found in resources, will be created");
        }
        try {
            initializeTables();   
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

