package com.cafeminamdang.Driver;

import com.cafeminamdang.Model.Invoice;

import java.sql.Date;
import java.math.BigDecimal;
import java.util.Scanner;

public class InvoiceDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Invoice Driver =====");
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
                        Date tanggal = Date.valueOf(dateString);

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