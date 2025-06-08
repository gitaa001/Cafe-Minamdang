package com.cafeminamdang.model;

import com.cafeminamdang.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Penjualan {
    private static final Logger logger = Logger.getLogger(Resep.class.getName());

    private int idPenjualan;
    private int totalPenjualan;
    private String tanggalPenjualan;
    private int idGudang;

    // Constructor
    public Penjualan(){

    }

    public Penjualan(int idPenjualan, int totalPenjualan, String tanggalPenjualan, int idGudang){
        this.idPenjualan = idPenjualan;
        this.totalPenjualan = totalPenjualan;
        this.tanggalPenjualan = tanggalPenjualan;
        this.idGudang = idGudang;
    }

    // Getter Setter
    public int getIdGudang() {
        return idGudang;
    }

    public int getIdPenjualan() {
        return idPenjualan;
    }

    public String getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public int getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setIdGudang(int idGudang) {
        this.idGudang = idGudang;
    }

    public void setTanggalPenjualan(String tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public void setTotalPenjualan(int totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    @Override // CLI Testing
    public String toString() {
        return "Resep{" +
                "IDPenjualan=" + idPenjualan +
                ", TotalHarga='" + totalPenjualan + '\'' +
                ", Tanggal='" + tanggalPenjualan + '\'' +
                ", IDGudang='" + idGudang + '\'' +
                '}';
    }

    // DBO
    public static List<Penjualan> getAllPenjualan(){
        List<Penjualan> penjualanList = new ArrayList<>();
        String sql = "SELECT * FROM Penjualan ORDER BY IDPenjualan";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            // int i = 1;

            while (rs.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setIdPenjualan(rs.getInt("IDPenjualan"));
                penjualan.setTotalPenjualan(rs.getInt("TotalHarga"));
                penjualan.setTanggalPenjualan(rs.getString("Tanggal"));
                penjualan.setIdGudang(rs.getInt("IDGudang"));
                
                penjualanList.add(penjualan);
                // System.out.println((i++) + resep.toString());
            }
        } catch (SQLException e) {
            logger.info("Error retrieving recipes : " + e.getMessage());
            return null;
        }

        return penjualanList;
    }

    public static List<Penjualan> getAllPenjualanFromSpesificWh(int gudangID){
        List<Penjualan> penjualanList = new ArrayList<>();
        String sql = "SELECT * FROM Penjualan WHERE IDGudang = ? ORDER BY IDPenjualan";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement ptsmt = conn.prepareStatement(sql)) {

            ptsmt.setInt(1, gudangID);
            ResultSet rs = ptsmt.executeQuery();
            // int i = 1;

            while (rs.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setIdPenjualan(rs.getInt("IDPenjualan"));
                penjualan.setTotalPenjualan(rs.getInt("TotalHarga"));
                penjualan.setTanggalPenjualan(rs.getString("Tanggal"));
                penjualan.setIdGudang(rs.getInt("IDGudang"));
                
                penjualanList.add(penjualan);
                // System.out.println((i++) + resep.toString());
            }
        } catch (SQLException e) {
            logger.info("Error retrieving recipes : " + e.getMessage());
            return null;
        }

        return penjualanList;
    }
}
