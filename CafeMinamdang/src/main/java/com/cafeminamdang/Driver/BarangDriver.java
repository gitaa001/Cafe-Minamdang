package com.cafeminamdang.Driver;

import com.cafeminamdang.Model.Barang;

import java.util.List;
import java.util.Scanner;

public class BarangDriver {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MENU BARANG ===");
            System.out.println("1. Lihat Semua Barang");
            System.out.println("2. Tambah Barang");
            System.out.println("3. Update Barang");
            System.out.println("4. Hapus Barang");
            System.out.println("5. Cari Barang by ID");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 1 -> lihatSemua();
                case 2 -> tambahBarang();
                case 3 -> updateBarang();
                case 4 -> hapusBarang();
                case 5 -> cariBarang();
                case 0 -> {
                    System.out.println("Sampai jumpa, Yang Mulia!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void lihatSemua() {
        List<Barang> list = Barang.getAllBarang();
        if (list.isEmpty()) {
            System.out.println("Tidak ada barang.");
            return;
        }
        for (Barang b : list) {
            printBarang(b);
        }
    }

    private static void tambahBarang() {
        System.out.println("=== Tambah Barang Baru ===");
        Barang b = inputBarang();
        boolean success = b.save();
        System.out.println(success ? "Berhasil disimpan." : "Gagal menyimpan.");
    }

    private static void updateBarang() {
        System.out.print("Masukkan ID Barang yang ingin diupdate: ");
        String id = scanner.nextLine();
        Barang b = Barang.getBarangByID(id);
        if (b == null) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        System.out.println("Data sekarang:");
        printBarang(b);
        System.out.println("Masukkan data baru:");
        Barang baru = inputBarang();
        baru.setIdBarang(id); // ID tetap
        boolean success = baru.save();
        System.out.println(success ? "Update berhasil." : "Update gagal.");
    }

    private static void hapusBarang() {
        System.out.print("Masukkan ID Barang yang ingin dihapus: ");
        String id = scanner.nextLine();
        boolean success = Barang.deleteById(id);
        System.out.println(success ? "Berhasil dihapus." : "Gagal menghapus.");
    }

    private static void cariBarang() {
        System.out.print("Masukkan ID Barang: ");
        String id = scanner.nextLine();
        Barang b = Barang.getBarangByID(id);
        if (b != null) {
            printBarang(b);
        } else {
            System.out.println("Barang tidak ditemukan.");
        }
    }

    private static Barang inputBarang() {
        System.out.print("ID Barang: ");
        String id = scanner.nextLine();
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Deskripsi: ");
        String deskripsi = scanner.nextLine();
        System.out.print("Kuantitas: ");
        int qty = Integer.parseInt(scanner.nextLine());
        System.out.print("Konsinyasi (true/false): ");
        boolean konsinyasi = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("ID Gudang: ");
        String idGudang = scanner.nextLine();

        return new Barang(id, nama, deskripsi, qty, konsinyasi, idGudang);
    }

    private static void printBarang(Barang b) {
        System.out.println("ID       : " + b.getIDBarang());
        System.out.println("Nama     : " + b.getNamaBarang());
        System.out.println("Deskripsi: " + b.getDeskripsi());
        System.out.println("Kuantitas: " + b.getKuantitas());
        System.out.println("Konsinyasi: " + b.getIsKonsinyasi());
        System.out.println("ID Gudang: " + b.getIDGudang());
        System.out.println("-------------------------------");
    }
}
