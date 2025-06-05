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

public class Resep {
    private static final Logger logger = Logger.getLogger(Resep.class.getName());
    
    private int idResep;
    private String namaResep;
    private String deskripsi;
    private String preskripsi;

    // Constructor

    public Resep(){

    }

    public Resep(int idResep, String namaResep, String deskripsi, String preskripsi){
        this.idResep = idResep;
        this.namaResep = namaResep;
        this.deskripsi = deskripsi;
        this.preskripsi = preskripsi;
    }

    // Getter Setter

    public int getIdResep(){
        return idResep;
    }

    public void setIdResep(int idResep){
        this.idResep = idResep;
    }

    public String getNamaResep(){
        return namaResep;
    }

    public void setNamaResep(String namaResep){
        this.namaResep = namaResep;
    }

    public String getDeskripsi(){
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi){
        this.deskripsi = deskripsi;
    }

    public String getPreskripsi(){
        return preskripsi;
    }

    public void setPreskripsi(String preskripsi){
        this.preskripsi = preskripsi;
    }

    @Override // CLI Testing
    public String toString() {
        return "Resep{" +
                "idResep=" + idResep +
                ", namaResep='" + namaResep + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", preskripsi='" + preskripsi + '\'' +
                '}';
    }

    // CRUD

    /**
     * Get all recipes from database
     * @return list of all recipes
     */
    public static List<Resep> getAllResep(){
        List<Resep> resepList = new ArrayList<>();
        String sql = "SELECT * FROM Resep ORDER BY IDResep";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            // int i = 1;

            while (rs.next()) {
                Resep resep = new Resep();
                resep.setIdResep(rs.getInt("IDResep"));
                resep.setNamaResep(rs.getString("NamaResep"));
                resep.setDeskripsi(rs.getString("Deskripsi"));
                resep.setPreskripsi(rs.getString("Preskripsi"));
                resepList.add(resep);
                // System.out.println((i++) + resep.toString());
            }
        } catch (SQLException e) {
            logger.info("Error retrieving recipes : " + e.getMessage());
        }

        return resepList;
    }

    /**
     * Get spesific recipe by ID
     * @param id Recipe ID to find
     * @return Recipe object if found, null otherwise
     */
    public static Resep getResepByID(int id) {
        String sql = "SELECT * FROM Resep WHERE IDResep = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // mengubah parameter ? menjadi id masukan parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                Resep resep = new Resep();
                resep.setIdResep(rs.getInt("IDResep"));
                resep.setNamaResep(rs.getString("NamaResep"));
                resep.setDeskripsi(rs.getString("Deskripsi"));
                resep.setPreskripsi(rs.getString("Preskripsi"));
                
                return resep;  
            }
        } catch (SQLException e) { 
            logger.severe("Error retrieving recipe #" + id + ": " + e.getMessage());
        }

        return null;
    }

    /**
     * Save recipe to database (insert or update)
     * @return true if successful, false otherwise
     */
    public boolean save(){
        if (idResep > 0) {
            return update();
        } else {
            return insert();
        }
    }

    private boolean insert() {
        String sql = "INSERT INTO Resep (NamaResep, Deskripsi, Preskripsi) VALUES (?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, namaResep);
            pstmt.setString(2, deskripsi);
            pstmt.setString(3, preskripsi);

            int affectedRows = pstmt.executeUpdate(); // Mendapatkan row yang terkena dampak seharusnya 1

            if (affectedRows > 0){
                try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                        idResep = rs.getInt(1);
                        logger.info("Recipe deleted: " + idResep);
                        return true;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error inserting recipe: " + e.getMessage());
        }

        return false;
    }

    /**
     * Update existing recipe in the database
     * @return true if succesful, false otherwise
     */
    private boolean update() {
        String sql = "UPDATE Resep SET NamaResep = ?, Deskripsi = ?, Preskripsi = ? WHERE IDResep = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement ptsmt = conn.prepareStatement(sql)) {
            
            ptsmt.setString(1, namaResep);
            ptsmt.setString(2, deskripsi);
            ptsmt.setString(3, preskripsi);
            ptsmt.setInt(4, idResep);

            int affectedRows = ptsmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Recipe updated: " + idResep);
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error updating recipe #" + idResep + ": " + e.getMessage()); 
        }

        return false;
    }

    /**
     * Delete a recipe from the databse
     * @return true if successful, false otherwise
     */
    public boolean delete() {
        String sql = "DELETE FROM Resep WHERE IDResep = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idResep);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Recipe deleted: " + idResep);
                return true;
            } 
        } catch (SQLException e) {
                logger.severe("Error deleting recipe #" + idResep + ": " + e.getMessage()); 
        }

        return false;
    }

    /**
     * Delete a recipe from the databse
     * @param id Recipe ID to be deleted
     * @return true if successful, false otherwise
     */
    public static boolean deleteById(int id) {
        String sql = "DELETE FROM Resep WHERE IDResep = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Recipe deleted: " + id);
                return true;
            } 
        } catch (SQLException e) {
                logger.severe("Error deleting recipe #" + id + ": " + e.getMessage()); 
        }

        return false;
    }

    // Driver
    // public static void main (String[] args) {
    //     List<Resep> listReseps = new ArrayList<>();
    //     listReseps = Resep.getAllResep();

    //     for (Resep resep : listReseps){
    //         System.out.println(resep.toString());
    //     }
    // }
}
