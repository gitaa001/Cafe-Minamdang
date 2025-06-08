package com.cafeminamdang.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.cafeminamdang.Database.DatabaseManager;

public class Barang {
    private static final Logger logger = Logger.getLogger(Barang.class.getName());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private StringProperty idBarang;
    private StringProperty nama;
    private StringProperty deskripsi;
    private Integer kuantitas;
    private boolean isKonsinyasi;
    private StringProperty idGudang;

    // Constructor kosong (wajib untuk read from DB)
    public Barang() {
        this.idBarang = new SimpleStringProperty();
        this.nama = new SimpleStringProperty();
        this.deskripsi = new SimpleStringProperty();
        this.kuantitas = 0;
        this.isKonsinyasi = false;
        this.idGudang = new SimpleStringProperty();
    }

    // Constructor penuh
    public Barang(String idBarang, String nama, String deskripsi, Integer kuantitas, boolean isKonsinyasi, String idGudang) {
        this.idBarang = new SimpleStringProperty(idBarang);
        this.nama = new SimpleStringProperty(nama);
        this.deskripsi = new SimpleStringProperty(deskripsi);
        this.kuantitas = kuantitas;
        this.isKonsinyasi = isKonsinyasi;
        this.idGudang = new SimpleStringProperty(idGudang);
    }

    // --- GETTER untuk JavaFX TableView (property)
    public StringProperty getIDBarangProperty() { return idBarang; }
    public StringProperty getNamaProperty() { return nama; }
    public StringProperty getDeskripsiProperty() { return deskripsi; }
    public StringProperty getIDGudangProperty() { return idGudang; }

    // --- GETTER biasa
    public String getIDBarang() { return idBarang.get(); }
    public String getNamaBarang() { return nama.get(); }
    public String getDeskripsi() { return deskripsi.get(); }
    public String getIDGudang() { return idGudang.get(); }
    public Integer getKuantitas() { return kuantitas; }
    public Boolean getIsKonsinyasi() { return isKonsinyasi; }

    // --- SETTER
    public void setIdBarang(String idBarang) { this.idBarang.set(idBarang); }
    public void setNamaBarang(String nama) { this.nama.set(nama); }
    public void setDeskripsi(String deskripsi) { this.deskripsi.set(deskripsi); }
    public void setKuantitas(Integer kuantitas) { this.kuantitas = kuantitas; }
    public void setKonsinyasi(boolean konsinyasi) { this.isKonsinyasi = konsinyasi; }
    public void setIdGudang(String idGudang) { this.idGudang.set(idGudang); }

    // === CRUD ===

    public static List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM Barang ORDER BY IDBarang";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Barang barang = new Barang();
                barang.setIdBarang(rs.getString("IDBarang"));
                barang.setNamaBarang(rs.getString("NamaBarang"));
                barang.setDeskripsi(rs.getString("Deskripsi"));
                barang.setKuantitas(rs.getInt("Kuantitas"));
                barang.setKonsinyasi(rs.getBoolean("IsKonsinyasi"));
                barang.setIdGudang(rs.getString("IDGudang"));

                list.add(barang);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching barang: " + e.getMessage());
        }

        return list;
    }

    public static Barang getBarangByID(String idBarang) {
        String sql = "SELECT * FROM Barang WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Barang barang = new Barang();
                barang.setIdBarang(rs.getString("IDBarang"));
                barang.setNamaBarang(rs.getString("NamaBarang"));
                barang.setDeskripsi(rs.getString("Deskripsi"));
                barang.setKuantitas(rs.getInt("Kuantitas"));
                barang.setKonsinyasi(rs.getBoolean("IsKonsinyasi"));
                barang.setIdGudang(rs.getString("IDGudang"));

                return barang;
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving barang #" + idBarang + ": " + e.getMessage());
        }

        return null;
    }

    public boolean save() {
        if (getBarangByID(this.getIDBarang()) != null) {
            return update();
        } else {
            return insert();
        }
    }

    private boolean insert() {
        String sql = "INSERT INTO Barang (IDBarang, NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getIDBarang());
            pstmt.setString(2, getNamaBarang());
            pstmt.setString(3, getDeskripsi());
            pstmt.setInt(4, getKuantitas());
            pstmt.setBoolean(5, getIsKonsinyasi());
            pstmt.setString(6, getIDGudang());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error inserting barang: " + e.getMessage());
        }

        return false;
    }

    private boolean update() {
        String sql = "UPDATE Barang SET NamaBarang = ?, Deskripsi = ?, Kuantitas = ?, IsKonsinyasi = ?, IDGudang = ? WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getNamaBarang());
            pstmt.setString(2, getDeskripsi());
            pstmt.setInt(3, getKuantitas());
            pstmt.setBoolean(4, getIsKonsinyasi());
            pstmt.setString(5, getIDGudang());
            pstmt.setString(6, getIDBarang());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error updating barang #" + getIDBarang() + ": " + e.getMessage());
        }

        return false;
    }

    public boolean delete() {
        return deleteById(this.getIDBarang());
    }

    public static boolean deleteById(String idBarang) {
        String sql = "DELETE FROM Barang WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting barang #" + idBarang + ": " + e.getMessage());
        }

        return false;
    }

    //Method tambahan buat ambil last ID karena ID dibuat increment
    public static String getLastBarangId() {
        String lastId = null;
        String query = "SELECT idbarang FROM barang ORDER BY idbarang DESC LIMIT 1";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                lastId = rs.getString("idbarang");

                // Misal lastId = "B012", kita ambil angka lalu +1
                int number = Integer.parseInt(lastId.replaceAll("[^\\d]", ""));
                number += 1;

                // Format kembali menjadi ID seperti "B013"
                return String.format("B%03d", number);
            } else {
                // Kalau belum ada data sama sekali
                return "B001";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
