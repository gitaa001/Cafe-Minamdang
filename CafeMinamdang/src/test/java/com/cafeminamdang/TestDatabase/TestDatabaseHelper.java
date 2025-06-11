package com.cafeminamdang.TestDatabase;

import com.cafeminamdang.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.Statement;

public class TestDatabaseHelper {

    // ---------------- BARANG ----------------
    public static void resetTableBarang() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM Barang");
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

    // ---------------- RESEP ----------------
    public static void resetTableResep() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM Resep");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Resep'");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDummyResep() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Resep (NamaResep, Deskripsi, Preskripsi) VALUES " +
                               "('Nasi Goreng', 'Pakai kecap', 'Nasi, Kecap, Telur'), " +
                               "('Mie Rebus', 'Pakai telur', 'Mie, Air, Telur')");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- INVOICE ----------------
    public static void resetTableInvoice() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            // Hapus semua data di tabel Invoice
            stmt.executeUpdate("DELETE FROM Invoice");
            // Reset autoincrement (khusus SQLite, opsional)
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Invoice'");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM Invoice");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Invoice'");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDummyInvoice() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            // Hanya insert data dummy berikut, tidak ada insert lain!
            stmt.executeUpdate("INSERT INTO Invoice (JudulInvoice, Tanggal, IDGudang, UnitPrice, Kuantitas) VALUES ('Invoice A', '2025-06-11', 1, 10000, 2)");
            stmt.executeUpdate("INSERT INTO Invoice (JudulInvoice, Tanggal, IDGudang, UnitPrice, Kuantitas) VALUES ('Invoice B', '2025-06-10', 2, 20000, 1)");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ---------------- PENJUALAN ----------------
    public static void resetTablePenjualan() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM Penjualan");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Penjualan'");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDummyPenjualan() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Penjualan (TotalHarga, Tanggal, IDGudang) " +
                    "VALUES (50000, '2025-06-01', 1)");
            stmt.executeUpdate("INSERT INTO Penjualan (TotalHarga, Tanggal, IDGudang) " +
                    "VALUES (70000, '2025-06-02', 2)");

            stmt.close();
            System.out.println("Dummy penjualan berhasil dimasukkan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
