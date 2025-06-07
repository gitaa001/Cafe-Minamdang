package com.cafeminamdang.Driver;

import com.cafeminamdang.Model.Barang;
import com.cafeminamdang.Model.Invoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InvoiceDriver {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MENU INVOICE ===");
            System.out.println("1. Lihat Semua Invoice");
            System.out.println("2. Tambah Invoice");
            System.out.println("3. Hapus Invoice");
            System.out.println("4. Cari Invoice by ID");
            System.out.println("5. Lihat Invoice Berdasarkan Nominal (naik)");
            System.out.println("6. Lihat Invoice Berdasarkan Tanggal (naik)");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 1 -> lihatSemuaInvoice();
                case 2 -> tambahInvoice();
                case 3 -> hapusInvoice();
                case 4 -> cariInvoice();
                case 5 -> sortByNominal();
                case 6 -> sortByDate();
                case 0 -> {
                    System.out.println("Sampai jumpa, Yang Mulia!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void lihatSemuaInvoice() {
        List<Invoice> list = Invoice.getAllInvoices();
        if (list.isEmpty()) {
            System.out.println("Tidak ada invoice.");
            return;
        }
        for (Invoice invoice : list) {
            printInvoice(invoice);
        }
    }

    private static void tambahInvoice() {
        System.out.println("=== Tambah Invoice Baru ===");
        try {
            Invoice invoice = inputInvoice();
            invoice.addInvoice(invoice);
            System.out.println("Invoice berhasil ditambahkan.");
        } catch (ParseException e) {
            System.out.println("Format tanggal salah: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Gagal menambahkan invoice: " + e.getMessage());
        }
    }

    private static void hapusInvoice() {
        System.out.print("Masukkan ID Invoice yang ingin dihapus: ");
        String id = scanner.nextLine();
        boolean success = Invoice.deleteInvoiceById(id);
        System.out.println(success ? "Berhasil dihapus." : "Gagal menghapus.");
    }

    private static void cariInvoice() {
        System.out.print("Masukkan ID Invoice: ");
        String id = scanner.nextLine();
        Invoice invoice = Invoice.getInvoiceById(id);
        if (invoice != null) {
            printInvoice(invoice);
        } else {
            System.out.println("Invoice tidak ditemukan.");
        }
    }

    private static void sortByNominal() {
        System.out.println("=== Invoice Berdasarkan Nominal (terendah ke tertinggi) ===");
        List<Invoice> sortedList = Invoice.sortByNominal();
        if (sortedList.isEmpty()) {
            System.out.println("Tidak ada invoice.");
            return;
        }
        for (Invoice invoice : sortedList) {
            System.out.println("ID: " + invoice.getIDInvoice() + 
                              ", Judul: " + invoice.getJudulInvoice() + 
                              ", Harga Total: " + invoice.getHargaTotalInvoice());
        }
    }

    private static void sortByDate() {
        System.out.println("=== Invoice Berdasarkan Tanggal (terlama ke terbaru) ===");
        List<Invoice> sortedList = Invoice.sortByDate();
        if (sortedList.isEmpty()) {
            System.out.println("Tidak ada invoice.");
            return;
        }
        for (Invoice invoice : sortedList) {
            System.out.println("ID: " + invoice.getIDInvoice() + 
                              ", Judul: " + invoice.getJudulInvoice() + 
                              ", Tanggal: " + dateFormat.format(invoice.getTanggalInvoice()));
        }
    }

    private static Invoice inputInvoice() throws ParseException {
        System.out.print("Judul Invoice: ");
        String judulInvoice = scanner.nextLine();
        
        System.out.print("Tanggal (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date tanggal = dateFormat.parse(dateString);
        
        System.out.print("ID Barang: ");
        String idBarang = scanner.nextLine();
        
        Barang barang = Barang.getBarangByID(idBarang);
        if (barang == null) {
            throw new RuntimeException("Barang dengan ID " + idBarang + " tidak ditemukan.");
        }
        
        System.out.print("Kuantitas: ");
        int kuantitas = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Harga Total: ");
        int hargaTotal = Integer.parseInt(scanner.nextLine());
        
        System.out.print("ID Gudang: ");
        String idGudang = scanner.nextLine();
        
        return new Invoice(judulInvoice, tanggal, barang, hargaTotal, kuantitas, idGudang);
    }

    private static void printInvoice(Invoice invoice) {
        System.out.println("-------------------------------");
        System.out.println("ID Invoice    : " + invoice.getIDInvoice());
        System.out.println("Judul Invoice : " + invoice.getJudulInvoice());
        System.out.println("Tanggal      : " + dateFormat.format(invoice.getTanggalInvoice()));
        System.out.println("Barang       : " + invoice.getBarangInvoice().getNamaBarang());
        System.out.println("ID Barang    : " + invoice.getBarangInvoice().getIDBarang());
        System.out.println("Harga Total  : " + invoice.getHargaTotalInvoice());
        System.out.println("Kuantitas    : " + invoice.getKuantitasInvoice());
        System.out.println("ID Gudang    : " + invoice.getIDGudang());
        System.out.println("-------------------------------");
    }
}