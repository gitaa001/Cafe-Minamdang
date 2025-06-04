package com.cafeminamdang.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;
import com.cafeminamdang.Database.DatabaseManager;

public class Resep {
    private static final Logger logger = Logger.getLogger(Resep.class.getName());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    // Define properties for TableView binding
    private StringProperty nama;
    private StringProperty deskripsi;
    private StringProperty resep;
    private StringProperty idResep;

    public Resep(String nama, String deskripsi, String resep, String idResep) {
        this.nama = new SimpleStringProperty(nama);
        this.deskripsi = new SimpleStringProperty(deskripsi);
        this.resep = new SimpleStringProperty(resep);
        this.idResep = new SimpleStringProperty(idResep);
    }

    // Getter untuk properti yang bisa digunakan di TableView
    public StringProperty namaProperty() {
        return nama;
    }

    public StringProperty deskripsiProperty() {
        return deskripsi;
    }

    public StringProperty resepProperty() {
        return resep;
    }

    public StringProperty idresepProperty(){
        return idResep;
    }

    // Method untuk menambah resep ke database
    public void tambahResep(String kode, String nama, String deskripsi, String resep, String harga, String idGudang) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Resep (NamaResep, Deskripsi, Preskripsi, Harga, IDGudang) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nama);
                pstmt.setString(2, deskripsi);
                pstmt.setString(3, resep);
                pstmt.setString(4, harga);
                pstmt.setString(5, idGudang);
                pstmt.executeUpdate();
                logger.info("Resep added successfully.");
            }
        } catch (SQLException e) {
            logger.severe("Error adding recipe: " + e.getMessage());
        }
    }


    // Method untuk update resep di database
    public void updateResep(int idResep, String nama, String deskripsi, String resep, String harga, String idGudang) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE Resep SET NamaResep = ?, Deskripsi = ?, Preskripsi = ?, Harga = ?, IDGudang = ? WHERE IDResep = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nama);
                pstmt.setString(2, deskripsi);
                pstmt.setString(3, resep);
                pstmt.setString(4, harga);
                pstmt.setString(5, idGudang);
                pstmt.setInt(6, idResep);
                pstmt.executeUpdate();
                logger.info("Resep updated successfully.");
            }
        } catch (SQLException e) {
            logger.severe("Error updating recipe: " + e.getMessage());
        }
    }

    // Method untuk mengambil koneksi dari DatabaseManager
    public Connection getConnection() {
        return databaseManager.getConnection();  // Access connection via DatabaseManager Singleton
    }

    // Method untuk mengambil semua resep
    public static ObservableList<Resep> getAllResep() {
        ObservableList<Resep> list = FXCollections.observableArrayList();  // ObservableList untuk TableView
        String sql = "SELECT * FROM Resep";  // Query untuk mengambil semua data resep
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // Loop untuk memasukkan setiap baris data dari ResultSet ke dalam ObservableList
            while (rs.next()) {
                String namaResep = rs.getString("NamaResep");
                String deskripsi = rs.getString("Deskripsi");
                String preskripsi = rs.getString("Preskripsi");
                String IDResep = rs.getString("IDResep");
                list.add(new Resep(namaResep, deskripsi, preskripsi, IDResep));
            }
            
        } catch (SQLException e) {
            logger.severe("Error fetching recipes: " + e.getMessage());
        }
        return list;  // Kembalikan ObservableList yang berisi semua resep
    }

    // Method untuk menghapus resep
    public void deleteResep(int idResep) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM Resep WHERE IDResep = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idResep);
                pstmt.executeUpdate();
                logger.info("Resep deleted successfully.");
            }
        } catch (SQLException e) {
            logger.severe("Error deleting recipe: " + e.getMessage());
        }
    }

    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        Resep resep = new Resep("", "", "", "");

        // Menu pilihan untuk pengguna
        while (true) {
            System.out.println("===== Menu =====");
            System.out.println("1. Lihat semua resep");
            System.out.println("2. Tambah resep");
            System.out.println("3. Update resep");
            System.out.println("4. Hapus resep");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // untuk menangkap newline setelah nextInt
            
            switch (choice) {
                case 1:
                    // Lihat semua resep
                    System.out.println("Daftar Resep:");
                    for (Resep r : Resep.getAllResep()) {
                        System.out.println("ID: " + r.namaProperty().get() + ", Nama: " + r.deskripsiProperty().get() + ", Deskripsi: " + r.resepProperty().get() + ", Resep ID: " + r.idresepProperty().get());
                    }
                    break;

                case 2:
                    // Tambah resep
                    System.out.println("Masukkan data resep baru:");
                    System.out.print("Nama Resep: ");
                    String nama = scanner.nextLine();
                    System.out.print("Deskripsi: ");
                    String deskripsi = scanner.nextLine();
                    System.out.print("Preskripsi: ");
                    String preskripsi = scanner.nextLine();
                    System.out.print("Harga: ");
                    String harga = scanner.nextLine();
                    System.out.print("ID Gudang: ");
                    String idGudang = scanner.nextLine();

                    // Menambahkan resep ke database
                    resep.tambahResep(null, nama, deskripsi, preskripsi, harga, idGudang);
                    break;

                case 3:
                    // Update resep
                    System.out.print("Masukkan ID Resep untuk update: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); // untuk menangkap newline setelah nextInt
                    System.out.println("Masukkan data baru:");
                    System.out.print("Nama Resep: ");
                    String namaUpdate = scanner.nextLine();
                    System.out.print("Deskripsi: ");
                    String deskripsiUpdate = scanner.nextLine();
                    System.out.print("Preskripsi: ");
                    String preskripsiUpdate = scanner.nextLine();
                    System.out.print("Harga: ");
                    String hargaUpdate = scanner.nextLine();
                    System.out.print("ID Gudang: ");
                    String idGudangUpdate = scanner.nextLine();
                    
                    // Update resep di database
                    resep.updateResep(idUpdate, namaUpdate, deskripsiUpdate, preskripsiUpdate, hargaUpdate, idGudangUpdate);
                    break;

                case 4:
                    // Hapus resep
                    System.out.print("Masukkan ID Resep yang ingin dihapus: ");
                    int idDelete = scanner.nextInt();
                    scanner.nextLine(); // untuk menangkap newline setelah nextInt
                    
                    // Hapus resep di database
                    resep.deleteResep(idDelete);
                    break;

                case 5:
                    // Keluar dari aplikasi
                    System.out.println("Terima kasih!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }
}
