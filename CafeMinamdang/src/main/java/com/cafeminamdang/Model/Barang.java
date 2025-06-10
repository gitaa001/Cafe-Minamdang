package com.cafeminamdang.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.cafeminamdang.Database.DatabaseManager;

public class Barang {
    private static final Logger logger = Logger.getLogger(Barang.class.getName());

    private Integer idBarang;
    private String nama;
    private String deskripsi;
    private Integer kuantitas;
    private boolean isKonsinyasi;
    private Integer idGudang;

    // Constructor kosong (wajib untuk read from DB)
    public Barang() {}

    // Constructor penuh
    public Barang(Integer idBarang, String nama, String deskripsi, Integer kuantitas, boolean isKonsinyasi, Integer idGudang) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.kuantitas = kuantitas;
        this.isKonsinyasi = isKonsinyasi;
        this.idGudang = idGudang;
    }

    // GETTER
    public Integer getIDBarang() { return idBarang; }
    public String getNamaBarang() { return nama; }
    public String getDeskripsi() { return deskripsi; }
    public Integer getKuantitas() { return kuantitas; }
    public Boolean getIsKonsinyasi() { return isKonsinyasi; }
    public Integer getIDGudang() { return idGudang; }

    // SETTER
    public void setIdBarang(Integer idBarang) { this.idBarang = idBarang; }
    public void setNamaBarang(String nama) { this.nama = nama; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public void setKuantitas(Integer kuantitas) { this.kuantitas = kuantitas; }
    public void setKonsinyasi(boolean konsinyasi) { this.isKonsinyasi = konsinyasi; }
    public void setIdGudang(Integer idGudang) { this.idGudang = idGudang; }

    // === CRUD ===

    public static List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM Barang ORDER BY IDBarang";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Barang barang = new Barang();
                barang.setIdBarang(rs.getInt("IDBarang"));
                barang.setNamaBarang(rs.getString("NamaBarang"));
                barang.setDeskripsi(rs.getString("Deskripsi"));
                barang.setKuantitas(rs.getInt("Kuantitas"));
                barang.setKonsinyasi(rs.getBoolean("IsKonsinyasi"));
                barang.setIdGudang(rs.getInt("IDGudang"));

                list.add(barang);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching barang: " + e.getMessage());
        }

        return list;
    }

    public static Barang getBarangByID(Integer idBarang) {
        String sql = "SELECT * FROM Barang WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBarang);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Barang barang = new Barang();
                barang.setIdBarang(rs.getInt("IDBarang"));
                barang.setNamaBarang(rs.getString("NamaBarang"));
                barang.setDeskripsi(rs.getString("Deskripsi"));
                barang.setKuantitas(rs.getInt("Kuantitas"));
                barang.setKonsinyasi(rs.getBoolean("IsKonsinyasi"));
                barang.setIdGudang(rs.getInt("IDGudang"));
                return barang;
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving barang #" + idBarang + ": " + e.getMessage());
        }

        return null;
    }

    public boolean save() {
        if (idBarang != null && idBarang > 0) {
            return update();
        } else {
            return insert();
        }
    }

    private boolean insert() {
        String sql = "INSERT INTO Barang (NamaBarang, Deskripsi, Kuantitas, IsKonsinyasi, IDGudang) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, deskripsi);
            pstmt.setInt(3, kuantitas);
            pstmt.setBoolean(4, isKonsinyasi);
            pstmt.setInt(5, idGudang);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        idBarang = rs.getInt(1);
                        logger.info("Barang inserted: " + idBarang);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("Error inserting barang: " + e.getMessage());
        }

        return false;
    }

    private boolean update() {
        String sql = "UPDATE Barang SET NamaBarang = ?, Deskripsi = ?, Kuantitas = ?, IsKonsinyasi = ?, IDGudang = ? WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, deskripsi);
            pstmt.setInt(3, kuantitas);
            pstmt.setBoolean(4, isKonsinyasi);
            pstmt.setInt(5, idGudang);
            pstmt.setInt(6, idBarang);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error updating barang #" + idBarang + ": " + e.getMessage());
        }

        return false;
    }

    public boolean delete() {
        return deleteById(this.getIDBarang());
    }

    public static boolean deleteById(Integer idBarang) {
        String sql = "DELETE FROM Barang WHERE IDBarang = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBarang);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting barang #" + idBarang + ": " + e.getMessage());
        }

        return false;
    }

    public static List<Barang> getAllBarangFromSpesificWh(int gudangID){
        List<Barang> barangList = new ArrayList<>();
        String sql = "SELECT * FROM Barang WHERE IDGudang = ? ORDER BY IDBarang";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement ptsmt = conn.prepareStatement(sql)) {

            ptsmt.setInt(1, gudangID);
            ResultSet rs = ptsmt.executeQuery();
            // int i = 1;

            while (rs.next()) {
                Barang barang = new Barang();
                barang.setIdBarang(rs.getInt("IDBarang"));
                barang.setNamaBarang(rs.getString("NamaBarang"));
                barang.setDeskripsi(rs.getString("Deskripsi"));
                barang.setKuantitas(rs.getInt("Kuantitas"));
                barang.setKonsinyasi(rs.getBoolean("IsKonsinyasi"));
                barang.setIdGudang(rs.getInt("IDGudang"));
                
                barangList.add(barang);
                // System.out.println((i++) + resep.toString());
            }
        } catch (SQLException e) {
            logger.info("Error retrieving recipes : " + e.getMessage());
            return null;
        }

        return barangList;
    }
}
