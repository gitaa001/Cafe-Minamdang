package com.cafeminamdang.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;

import java.sql.Date; // gunakan java.sql.Date untuk property dan query tanggal
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.*;
import com.cafeminamdang.Database.DatabaseManager;

public class Invoice {
    private static final Logger logger = Logger.getLogger(Invoice.class.getName());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private IntegerProperty idInvoice;
    private StringProperty judulInvoice;
    private ObjectProperty<Date> tanggal; // java.sql.Date
    private IntegerProperty idGudang;
    private ObjectProperty<BigDecimal> unitPrice;
    private IntegerProperty kuantitas;

    // Constructors
    public Invoice(String judulInvoice, Date tanggal, int idGudang, BigDecimal unitPrice, int kuantitas) {
        this.judulInvoice = new SimpleStringProperty(judulInvoice);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.idGudang = new SimpleIntegerProperty(idGudang);
        this.unitPrice = new SimpleObjectProperty<>(unitPrice);
        this.kuantitas = new SimpleIntegerProperty(kuantitas);
    }

    public Invoice(Integer idInvoice, String judulInvoice, Date tanggal, Integer idGudang, BigDecimal unitPrice, Integer kuantitas) {
        this.idInvoice = new SimpleIntegerProperty(idInvoice);
        this.judulInvoice = new SimpleStringProperty(judulInvoice);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.idGudang = new SimpleIntegerProperty(idGudang);
        this.unitPrice = new SimpleObjectProperty<>(unitPrice);
        this.kuantitas = new SimpleIntegerProperty(kuantitas);
    }

    public Invoice() {
        this.idInvoice = new SimpleIntegerProperty();
        this.judulInvoice = new SimpleStringProperty();
        this.tanggal = new SimpleObjectProperty<>();
        this.idGudang = new SimpleIntegerProperty();
        this.unitPrice = new SimpleObjectProperty<>();
        this.kuantitas = new SimpleIntegerProperty();
    }

    // Property getters
    public IntegerProperty idInvoiceProperty() { return idInvoice; }
    public StringProperty judulInvoiceProperty() { return judulInvoice; }
    public ObjectProperty<Date> tanggalProperty() { return tanggal; }
    public IntegerProperty idGudangProperty() { return idGudang; }
    public ObjectProperty<BigDecimal> unitPriceProperty() { return unitPrice; }
    public IntegerProperty kuantitasProperty() { return kuantitas; }

    // Value getters
    public Integer getIDInvoice() { return idInvoice.get(); }
    public String getJudulInvoice() { return judulInvoice.get(); }
    public Date getTanggalInvoice() { return tanggal.get(); }
    public Integer getIDGudang() { return idGudang.get(); }
    public BigDecimal getUnitPrice() { return unitPrice.get(); }
    public Integer getKuantitasInvoice() { return kuantitas.get(); }

    // Value setters
    public void setIDInvoice(Integer idInvoice) { this.idInvoice.set(idInvoice); }
    public void setJudulInvoice(String judulInvoice) { this.judulInvoice.set(judulInvoice); }
    public void setTanggalInvoice(Date tanggal) { this.tanggal.set(tanggal); }
    public void setIDGudang(Integer idGudang) { this.idGudang.set(idGudang); }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice.set(unitPrice); }
    public void setKuantitasInvoice(Integer kuantitas) { this.kuantitas.set(kuantitas); }

    // Database connection
    public Connection getConnection() {
        return databaseManager.getConnection();
    }

    // DAO methods
    // Add Invoice
    public void addInvoice(Invoice invoice) {
        Connection conn = getConnection();
        String sql = "INSERT INTO Invoice (JudulInvoice, Tanggal, IDGudang, UnitPrice, Kuantitas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, invoice.getJudulInvoice());
            pstmt.setDate(2, invoice.getTanggalInvoice()); // langsung java.sql.Date tanpa time
            pstmt.setInt(3, invoice.getIDGudang());
            pstmt.setBigDecimal(4, invoice.getUnitPrice());
            pstmt.setInt(5, invoice.getKuantitasInvoice());
            pstmt.executeUpdate();
            logger.info("Invoice added successfully.");
        } catch (SQLException e) {
            logger.severe("Error adding invoice: " + e.getMessage());
        }
    }

    // Get Invoice By ID
    public static Invoice getInvoiceById(Integer IDInvoice) {
        String sql = "SELECT * FROM Invoice WHERE IDInvoice = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, IDInvoice);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setIDInvoice(rs.getInt("IDInvoice"));
                    invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                    invoice.setTanggalInvoice(rs.getDate("Tanggal")); // java.sql.Date langsung
                    invoice.setIDGudang(rs.getInt("IDGudang"));
                    invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                    invoice.setKuantitasInvoice(rs.getInt("Kuantitas"));
                    return invoice;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error fetching invoice: " + e.getMessage());
        }
        return null;
    }

    // Delete Invoice By ID
    public static boolean deleteInvoiceById(Integer IDInvoice) {
        String sql = "DELETE FROM Invoice WHERE IDInvoice = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, IDInvoice);
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Invoice deleted successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting invoice: " + e.getMessage());
            return false;
        }
    }

    // Get all invoices
    public static List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIDInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggalInvoice(rs.getDate("Tanggal")); // java.sql.Date langsung
                invoice.setIDGudang(rs.getInt("IDGudang"));
                invoice.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                invoice.setKuantitasInvoice(rs.getInt("Kuantitas"));
                list.add(invoice);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching invoices: " + e.getMessage());
        }
        return list;
    }

    // Sort methods
    public static List<Invoice> sortByNominal() {
        List<Invoice> list = getAllInvoices();
        list.sort(Comparator.comparing(Invoice::getUnitPrice));
        return list;
    }

    public static List<Invoice> sortByDate() {
        List<Invoice> list = getAllInvoices();
        list.sort(Comparator.comparing(Invoice::getTanggalInvoice, Comparator.nullsLast(Date::compareTo)));
        return list;
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu pilihan untuk pengguna
        while (true) {
            System.out.println("===== Invoice =====");
            System.out.println("1. Lihat semua invoice");
            System.out.println("2. Cari invoice berdasarkan ID");
            System.out.println("3. Tambah invoice baru");
            System.out.println("4. Hapus invoice");
            System.out.println("5. Lihat invoice berdasarkan nominal (naik)");
            System.out.println("6. Lihat invoice berdasarkan tanggal (naik)");
            System.out.println("7. Keluar");
            System.out.print("Pilih opsi (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // untuk menangkap newline setelah nextInt

            switch (choice) {
                case 1:
                    System.out.println("Daftar Invoice:");
                    for (Invoice invoice : Invoice.getAllInvoices()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + (invoice.getTanggalInvoice() != null ? invoice.getTanggalInvoice().toString() : "null") +
                            ", Unit Price: " + invoice.getUnitPrice() +
                            ", Kuantitas: " + invoice.getKuantitasInvoice() +
                            ", ID Gudang: " + invoice.getIDGudang()
                        );
                    }
                    break;

                case 2:
                    System.out.print("Masukkan ID Invoice: ");
                    int idInvoice = Integer.parseInt(scanner.nextLine());

                    Invoice found = Invoice.getInvoiceById(idInvoice);
                    if (found != null) {
                        System.out.println(
                            "ID: " + found.getIDInvoice() +
                            ", Judul: " + found.getJudulInvoice() +
                            ", Tanggal: " + (found.getTanggalInvoice() != null ? found.getTanggalInvoice().toString() : "null") +
                            ", Unit Price: " + found.getUnitPrice() +
                            ", Kuantitas: " + found.getKuantitasInvoice() +
                            ", ID Gudang: " + found.getIDGudang()
                        );
                    } else {
                        System.out.println("Invoice tidak ditemukan.");
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Masukkan data invoice baru:");
                        System.out.print("Judul Invoice: ");
                        String judulInvoice = scanner.nextLine();

                        System.out.print("Tanggal (yyyy-MM-dd): ");
                        String dateString = scanner.nextLine();
                        Date tanggal = Date.valueOf(dateString); // langsung valueOf

                        System.out.print("Unit Price: ");
                        BigDecimal unitPrice = new BigDecimal(scanner.nextLine());

                        System.out.print("Kuantitas: ");
                        int kuantitas = Integer.parseInt(scanner.nextLine());

                        System.out.print("ID Gudang: ");
                        int idGudang = Integer.parseInt(scanner.nextLine());

                        Invoice newInvoice = new Invoice(judulInvoice, tanggal, idGudang, unitPrice, kuantitas);
                        newInvoice.addInvoice(newInvoice);

                        System.out.println("Invoice berhasil ditambahkan.");
                    } catch (NumberFormatException e) {
                        System.out.println("Format angka salah: " + e.getMessage());
                        logger.severe("Error parsing number: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Format tanggal salah: " + e.getMessage());
                        logger.severe("Error parsing date: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        logger.severe("Error adding invoice: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Masukkan ID Invoice yang akan dihapus: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());

                    boolean deleted = Invoice.deleteInvoiceById(deleteId);
                    if (deleted) {
                        System.out.println("Invoice berhasil dihapus.");
                    } else {
                        System.out.println("Gagal menghapus invoice.");
                    }
                    break;

                case 5:
                    System.out.println("Invoice Berdasarkan Nominal (terendah ke tertinggi):");
                    for (Invoice invoice : Invoice.sortByNominal()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Unit Price: " + invoice.getUnitPrice()
                        );
                    }
                    break;

                case 6:
                    System.out.println("Invoice Berdasarkan Tanggal (terlama ke terbaru):");
                    for (Invoice invoice : Invoice.sortByDate()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + (invoice.getTanggalInvoice() != null ? invoice.getTanggalInvoice().toString() : "null")
                        );
                    }
                    break;

                case 7:
                    System.out.println("Terima kasih!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }
}