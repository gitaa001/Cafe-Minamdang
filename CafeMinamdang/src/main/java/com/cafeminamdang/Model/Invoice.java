package com.cafeminamdang.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.math.BigDecimal;

import com.cafeminamdang.Database.DatabaseManager;

public class Invoice {
    private static final Logger logger = Logger.getLogger(Invoice.class.getName());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private int idInvoice;
    private String judulInvoice;
    private String tanggal;
    private int idGudang;
    private BigDecimal unitPrice;
    private int kuantitas;

    // Constructor kosong
    public Invoice() {}

    // Constructor dengan parameter
    public Invoice(int idInvoice, String judulInvoice, String tanggal, int idGudang, BigDecimal unitPrice, int kuantitas) {
        this.idInvoice = idInvoice;
        this.judulInvoice = judulInvoice;
        this.tanggal = tanggal;
        this.idGudang = idGudang;
        this.unitPrice = unitPrice;
        this.kuantitas = kuantitas;
    }

    public Invoice(String judulInvoice, String tanggal, int idGudang, BigDecimal unitPrice, int kuantitas) {
        this.judulInvoice = judulInvoice;
        this.tanggal = tanggal;
        this.idGudang = idGudang;
        this.unitPrice = unitPrice;
        this.kuantitas = kuantitas;
    }

    // Getter & Setter
    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getJudulInvoice() {
        return judulInvoice;
    }

    public void setJudulInvoice(String judulInvoice) {
        this.judulInvoice = judulInvoice;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(int idGudang) {
        this.idGudang = idGudang;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public BigDecimal getHargaTotalInvoice() {
        return unitPrice.multiply(BigDecimal.valueOf(kuantitas));
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "idInvoice=" + idInvoice +
                ", judulInvoice='" + judulInvoice + '\'' +
                ", tanggal=" + tanggal +
                ", idGudang=" + idGudang +
                ", unitPrice=" + unitPrice +
                ", kuantitas=" + kuantitas +
                '}';
    }

    // CRUD

    // Tambah Invoice (INSERT)
    public boolean save() {
        if (idInvoice > 0) {
            return update();
        } else {
            return insert();
        }
    }

    private boolean insert() {
        String sql = "INSERT INTO Invoice (JudulInvoice, Tanggal, IDGudang, UnitPrice, Kuantitas) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, judulInvoice);
            pstmt.setString(2, tanggal);
            pstmt.setInt(3, idGudang);
            pstmt.setBigDecimal(4, unitPrice);
            pstmt.setInt(5, kuantitas);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Dapatkan ID terakhir (last_insert_rowid)
                try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        idInvoice = rs.getInt(1);
                        logger.info("Invoice inserted: " + idInvoice);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("Error inserting invoice: " + e.getMessage());
        }
        return false;
    }

    // Update Invoice
    private boolean update() {
        String sql = "UPDATE Invoice SET JudulInvoice = ?, Tanggal = ?, IDGudang = ?, UnitPrice = ?, Kuantitas = ? WHERE IDInvoice = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, judulInvoice);
            pstmt.setString(2, tanggal);
            pstmt.setInt(3, idGudang);
            pstmt.setBigDecimal(4, unitPrice);
            pstmt.setInt(5, kuantitas);
            pstmt.setInt(6, idInvoice);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Invoice updated: " + idInvoice);
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error updating invoice #" + idInvoice + ": " + e.getMessage());
        }
        return false;
    }

    // Ambil Invoice berdasarkan ID
    public static Invoice getInvoiceById(int idInvoice) {
        String sql = "SELECT * FROM Invoice WHERE IDInvoice = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idInvoice);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggal(rs.getString("Tanggal"));
                invoice.setIdGudang(rs.getInt("IDGudang"));
                invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                invoice.setKuantitas(rs.getInt("Kuantitas"));
                return invoice;
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving invoice #" + idInvoice + ": " + e.getMessage());
        }
        return null;
    }

    // Hapus Invoice berdasarkan ID
    public static boolean deleteInvoiceById(int idInvoice) {
        String sql = "DELETE FROM Invoice WHERE IDInvoice = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idInvoice);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Invoice deleted: " + idInvoice);
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error deleting invoice #" + idInvoice + ": " + e.getMessage());
        }
        return false;
    }

    // Mendapatkan semua Invoice
    public static List<Invoice> getAllInvoice() {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT * FROM Invoice ORDER BY IDInvoice";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggal(rs.getString("Tanggal"));
                invoice.setIdGudang(rs.getInt("IDGudang"));
                invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                invoice.setKuantitas(rs.getInt("Kuantitas"));
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving invoices: " + e.getMessage());
        }
        return invoiceList;
    }

    // Sort by Nominal (Total Harga) Descending
    public static List<Invoice> sortByNominal() {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT *, (UnitPrice * Kuantitas) as TotalHarga FROM Invoice ORDER BY TotalHarga DESC";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggal(rs.getString("Tanggal"));
                invoice.setIdGudang(rs.getInt("IDGudang"));
                invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                invoice.setKuantitas(rs.getInt("Kuantitas"));
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            logger.severe("Error sorting invoices by nominal: " + e.getMessage());
        }
        return invoiceList;
    }

    // Sort by Date Descending
    public static List<Invoice> sortByDate() {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT * FROM Invoice ORDER BY Tanggal DESC";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggal(rs.getString("Tanggal"));
                invoice.setIdGudang(rs.getInt("IDGudang"));
                invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                invoice.setKuantitas(rs.getInt("Kuantitas"));
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            logger.severe("Error sorting invoices by date: " + e.getMessage());
        }
        return invoiceList;
    }
}