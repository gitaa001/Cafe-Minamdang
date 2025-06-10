package com.cafeminamdang.Driver;

import java.math.BigDecimal;
import java.util.Scanner;

import com.cafeminamdang.Model.Invoice;

import java.util.List;

public class InvoiceDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Invoice Driver =====");
            System.out.println("1. Lihat semua invoice");
            System.out.println("2. Cari invoice berdasarkan ID");
            System.out.println("3. Tambah invoice baru");
            System.out.println("4. Hapus invoice");
            System.out.println("5. Lihat invoice berdasarkan nominal (tertinggi ke terendah)");
            System.out.println("6. Lihat invoice berdasarkan tanggal (terbaru ke terlama)");
            System.out.println("7. Keluar");
            System.out.print("Pilih opsi (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // menangkap newline

            switch (choice) {
                case 1:
                    System.out.println("Daftar Invoice:");
                    List<Invoice> allInvoices = Invoice.getAllInvoice();
                    for (Invoice invoice : allInvoices) {
                        System.out.println(
                            "ID: " + invoice.getIdInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + (invoice.getTanggal() != null ? invoice.getTanggal().toString() : "null") +
                            ", Unit Price: " + invoice.getUnitPrice() +
                            ", Kuantitas: " + invoice.getKuantitas() +
                            ", ID Gudang: " + invoice.getIdGudang()
                        );
                    }
                    break;

                case 2:
                    System.out.print("Masukkan ID Invoice: ");
                    int idInvoice = Integer.parseInt(scanner.nextLine());

                    Invoice found = Invoice.getInvoiceById(idInvoice);
                    if (found != null) {
                        System.out.println(
                            "ID: " + found.getIdInvoice() +
                            ", Judul: " + found.getJudulInvoice() +
                            ", Tanggal: " + (found.getTanggal() != null ? found.getTanggal().toString() : "null") +
                            ", Unit Price: " + found.getUnitPrice() +
                            ", Kuantitas: " + found.getKuantitas() +
                            ", ID Gudang: " + found.getIdGudang()
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
                        String tanggal = String.valueOf(dateString);

                        System.out.print("Unit Price: ");
                        BigDecimal unitPrice = new BigDecimal(scanner.nextLine());

                        System.out.print("Kuantitas: ");
                        int kuantitas = Integer.parseInt(scanner.nextLine());

                        System.out.print("ID Gudang: ");
                        int idGudang = Integer.parseInt(scanner.nextLine());

                        Invoice newInvoice = new Invoice(judulInvoice, tanggal, idGudang, unitPrice, kuantitas);
                        if (newInvoice.save()) {
                            System.out.println("Invoice berhasil ditambahkan.");
                        } else {
                            System.out.println("Gagal menambah invoice.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Format angka salah: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Format tanggal salah: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
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
                    System.out.println("Invoice Berdasarkan Nominal (tertinggi ke terendah):");
                    List<Invoice> sortedNominal = Invoice.sortByNominal();
                    for (Invoice invoice : sortedNominal) {
                        System.out.println(
                            "ID: " + invoice.getIdInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Total Harga: " + invoice.getHargaTotalInvoice()
                        );
                    }
                    break;

                case 6:
                    System.out.println("Invoice Berdasarkan Tanggal (terbaru ke terlama):");
                    List<Invoice> sortedDate = Invoice.sortByDate();
                    for (Invoice invoice : sortedDate) {
                        System.out.println(
                            "ID: " + invoice.getIdInvoice() +
                            ", Judul: " + invoice.getJudulInvoice() +
                            ", Tanggal: " + (invoice.getTanggal() != null ? invoice.getTanggal().toString() : "null")
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