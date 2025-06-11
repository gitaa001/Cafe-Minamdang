package com.cafeminamdang.TestDatabase;

import com.cafeminamdang.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.Statement;

public class TestDatabaseHelper {

    public static void resetTableBarang() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            
            // Kosongkan isi tabel Barang
            stmt.execute("DELETE FROM Barang");

            // Reset autoincrement ID (kalau SQLite)
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Barang'");
            
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDummyBarang() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Barang (NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                               "VALUES ('Teh Manis', 'Minuman khas Cafe', 50, 0, 1)");
            stmt.executeUpdate("INSERT INTO Barang (NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) " +
                               "VALUES ('Kopi Hitam', 'Tanpa gula', 30, 1, 2)");

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
