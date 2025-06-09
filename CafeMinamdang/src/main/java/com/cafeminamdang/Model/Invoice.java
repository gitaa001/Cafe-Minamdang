package com.cafeminamdang.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;

import java.util.Scanner;
import java.util.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.*;
import com.cafeminamdang.Database.DatabaseManager;



public class Invoice {
    private static final Logger logger = Logger.getLogger(Invoice.class.getName());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private StringProperty judulInvoice;
    private ObjectProperty<Date> tanggal;
    private ObjectProperty<Barang> barang;
    private IntegerProperty hargaTotal;
    private IntegerProperty kuantitas;
    private IntegerProperty idGudang;
    private IntegerProperty idInvoice;

    // Constructor
    public Invoice(String judulInvoice, Date tanggal, Barang barang, int hargaTotal, int kuantitas, int idGudang) {
        this.judulInvoice = new SimpleStringProperty(judulInvoice);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.barang = new SimpleObjectProperty<>(barang);
        this.hargaTotal = new SimpleIntegerProperty(hargaTotal);
        this.kuantitas = new SimpleIntegerProperty(kuantitas);
        this.idGudang = new SimpleIntegerProperty(idGudang);
    }

    // Constructor with ID (buat database)
    public Invoice(Integer idInvoice, String judulInvoice, Date tanggal, Barang barang, Integer hargaTotal, Integer kuantitas, Integer idGudang) {
        this.idInvoice = new SimpleIntegerProperty(idInvoice);
        this.judulInvoice = new SimpleStringProperty(judulInvoice);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.barang = new SimpleObjectProperty<>(barang);
        this.hargaTotal = new SimpleIntegerProperty(hargaTotal);
        this.kuantitas = new SimpleIntegerProperty(kuantitas);
        this.idGudang = new SimpleIntegerProperty(idGudang);
    }

    // Default constructor
    public Invoice() {
        this.judulInvoice = new SimpleStringProperty();
        this.tanggal = new SimpleObjectProperty<>();
        this.barang = new SimpleObjectProperty<>();
        this.hargaTotal = new SimpleIntegerProperty();
        this.kuantitas = new SimpleIntegerProperty();
        this.idGudang = new SimpleIntegerProperty();
        this.idInvoice = new SimpleIntegerProperty();
    }

    public StringProperty judulInvoiceProperty() {
        return judulInvoice;
    }

    public ObjectProperty<Date> tanggalProperty() {
        return tanggal;
    }

    public ObjectProperty<Barang> barangProperty() {
        return barang;
    }

    public IntegerProperty hargaTotalProperty() {
        return hargaTotal;
    }

    public IntegerProperty kuantitasProperty() {
        return kuantitas;
    }

    public IntegerProperty idGudangProperty() {
        return idGudang;
    }

    public IntegerProperty idInvoiceProperty() {
        return idInvoice;
    }

    public String getJudulInvoice() {
        return judulInvoice.get();
    }

    public Date getTanggalInvoice() {
        return tanggal.get();
    }

    public Barang getBarangInvoice() {
        return barang.get();
    }

    public Integer getHargaTotalInvoice() {
        return hargaTotal.get();
    }

    public Integer getKuantitasInvoice() {
        return kuantitas.get();
    }

    public Integer getIDGudang() {
        return idGudang.get();
    }

    public Integer getIDInvoice() {
        return idInvoice.get();
    }

    public void setJudulInvoice(String judulInvoice) {
        this.judulInvoice.set(judulInvoice);
    }

    public void setTanggalInvoice(Date tanggal) {
        this.tanggal.set(tanggal);
    }

    public void setBarangInvoice(Barang barang) {
        this.barang.set(barang);
    }

    public void setHargaTotalInvoice(Integer hargaTotal) {
        this.hargaTotal.set(hargaTotal);
    }

    public void setKuantitasInvoice(Integer kuantitas) {
        this.kuantitas.set(kuantitas);
    }

    public void setIDGudang(Integer idGudang) {
        this.idGudang.set(idGudang);
    }

    public void setIDInvoice(Integer idInvoice) {
        this.idInvoice.set(idInvoice);
    }

    // Method untuk mengambil koneksi dari DatabaseManager
    public Connection getConnection() {
        return databaseManager.getConnection();
    }

    // DAO methods
    public void addInvoice(Invoice invoice) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Invoice (JudulInvoice, Tanggal, IDBarang, HargaTotal, Kuantitas, IDGudang) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, invoice.getJudulInvoice());
                pstmt.setTimestamp(2, new Timestamp(invoice.getTanggalInvoice().getTime()));
                pstmt.setInt(3, invoice.getBarangInvoice().getIDBarang());
                pstmt.setInt(4, invoice.getHargaTotalInvoice());
                pstmt.setInt(5, invoice.getKuantitasInvoice());
                pstmt.setInt(6, invoice.getIDGudang());
                pstmt.executeUpdate();
                logger.info("Invoice added successfully.");
            }
        } catch (SQLException e) {
            logger.severe("Error adding invoice: " + e.getMessage());
        }
    }

    public static Invoice getInvoiceById(Integer IDInvoice) {
        String sql = "SELECT i.*, b.* FROM Invoice i JOIN Barang b ON i.IDBarang = b.IDBarang WHERE i.IDInvoice = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, IDInvoice);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Create Barang object from result set
                    Barang barang = new Barang(
                        rs.getInt("IDBarang"),
                        rs.getString("NamaBarang"),
                        rs.getString("Deskripsi"),
                        rs.getInt("Kuantitas"),
                        rs.getBoolean("IsKonsinyasi"),
                        rs.getInt("IDGudang")
                    );
                    
                    // Create and return Invoice object
                    Invoice invoice = new Invoice();
                    invoice.setIDInvoice(rs.getInt("IDInvoice"));
                    invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                    invoice.setTanggalInvoice(new Date(rs.getTimestamp("Tanggal").getTime()));
                    invoice.setBarangInvoice(barang);
                    invoice.setHargaTotalInvoice(rs.getInt("HargaTotal"));
                    invoice.setKuantitasInvoice(rs.getInt("Kuantitas"));
                    invoice.setIDGudang(rs.getInt("IDGudang"));
                    
                    return invoice;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error fetching invoice: " + e.getMessage());
        }
        return null;
    }

    public static boolean deleteInvoiceById(Integer IDInvoice) {
        String sql = "DELETE FROM Invoice WHERE IDInvoice = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT i.*, b.* FROM Invoice i JOIN Barang b ON i.IDBarang = b.IDBarang";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Barang barang = new Barang(
                    rs.getInt("IDBarang"),
                    rs.getString("NamaBarang"),
                    rs.getString("Deskripsi"),
                    rs.getInt("Kuantitas"),
                    rs.getBoolean("IsKonsinyasi"),
                    rs.getInt("IDGudang")
                );
                
                Invoice invoice = new Invoice();
                invoice.setIDInvoice(rs.getInt("IDInvoice"));
                invoice.setJudulInvoice(rs.getString("JudulInvoice"));
                invoice.setTanggalInvoice(new Date(rs.getTimestamp("Tanggal").getTime()));
                invoice.setBarangInvoice(barang);
                invoice.setHargaTotalInvoice(rs.getInt("HargaTotal"));
                invoice.setKuantitasInvoice(rs.getInt("Kuantitas"));
                invoice.setIDGudang(rs.getInt("IDGudang"));
                
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
        list.sort(Comparator.comparing(Invoice::getHargaTotalInvoice));
        return list;
    }

    public static List<Invoice> sortByDate() {
        List<Invoice> list = getAllInvoices();
        list.sort(Comparator.comparing(Invoice::getTanggalInvoice));
        return list;
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    // Lihat semua invoice
                    System.out.println("Daftar Invoice:");
                    for (Invoice invoice : Invoice.getAllInvoices()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + dateFormat.format(invoice.getTanggalInvoice()) +
                            ", Barang: " + invoice.getBarangInvoice().getNamaBarang() +
                            ", Harga Total: " + invoice.getHargaTotalInvoice() +
                            ", Kuantitas: " + invoice.getKuantitasInvoice() +
                            ", ID Gudang: " + invoice.getIDGudang()
                        );
                    }
                    break;

                case 2:
                    // Cari invoice berdasarkan ID
                    System.out.print("Masukkan ID Invoice: ");
                    int idInvoice = Integer.parseInt(scanner.nextLine());
                    
                    Invoice found = Invoice.getInvoiceById(idInvoice);
                    if (found != null) {
                        System.out.println(
                            "ID: " + found.getIDInvoice() +
                            ", Judul: " + found.getJudulInvoice() +
                            ", Tanggal: " + dateFormat.format(found.getTanggalInvoice()) +
                            ", Barang: " + found.getBarangInvoice().getNamaBarang() +
                            ", Harga Total: " + found.getHargaTotalInvoice() +
                            ", Kuantitas: " + found.getKuantitasInvoice() +
                            ", ID Gudang: " + found.getIDGudang()
                        );
                    } else {
                        System.out.println("Invoice tidak ditemukan.");
                    }
                    break;

                case 3:
                    // Tambah invoice baru
                    try {
                        System.out.println("Masukkan data invoice baru:");
                        System.out.print("Judul Invoice: ");
                        String judulInvoice = scanner.nextLine();
        
                        System.out.print("Tanggal (yyyy-MM-dd): ");
                        String dateString = scanner.nextLine();
                        Date tanggal = dateFormat.parse(dateString);
        
                        System.out.print("ID Barang: ");
                        int idBarang = Integer.parseInt(scanner.nextLine());
        
                        // Fetch Barang object
                        Barang barang = Barang.getBarangByID(idBarang);
        
                        if (barang == null) {
                            System.out.println("Barang tidak ditemukan.");
                            break;
                        }
        
                        System.out.print("Kuantitas: ");
                        int kuantitas = scanner.nextInt();
                        scanner.nextLine();
        
                        System.out.print("Harga Total: ");
                        int hargaTotal = scanner.nextInt();
                        scanner.nextLine();
        
                        System.out.print("ID Gudang: ");
                        int idGudang = Integer.parseInt(scanner.nextLine());
        
                        // Create and add Invoice
                        Invoice newInvoice = new Invoice(judulInvoice, tanggal, barang, hargaTotal, kuantitas, idGudang);
                        newInvoice.addInvoice(newInvoice);
        
                        System.out.println("Invoice berhasil ditambahkan.");
                    } catch (ParseException e) {
                        System.out.println("Format tanggal salah: " + e.getMessage());
                        logger.severe("Error parsing date: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Format angka salah: " + e.getMessage());
                        logger.severe("Error parsing number: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        logger.severe("Error adding invoice: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Hapus invoice
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
                    // Sort by nominal
                    System.out.println("Invoice Berdasarkan Nominal (terendah ke tertinggi):");
                    for (Invoice invoice : Invoice.sortByNominal()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Harga Total: " + invoice.getHargaTotalInvoice()
                        );
                    }
                    break;

                case 6:
                    // Sort by date
                    System.out.println("Invoice Berdasarkan Tanggal (terlama ke terbaru):");
                    for (Invoice invoice : Invoice.sortByDate()) {
                        System.out.println(
                            "ID: " + invoice.getIDInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + dateFormat.format(invoice.getTanggalInvoice())
                        );
                    }
                    break;

                case 7:
                    // Exit
                    System.out.println("Terima kasih!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }
}