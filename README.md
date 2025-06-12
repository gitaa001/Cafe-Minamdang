# IF2050-2025-K3K-Cafe-Minamdang

Aplikasi ini merupakan tugas besar mata kuliah IF2050 Dasar Rekayasa Perangkat Lunak. Sistem yang dikembangkan adalah aplikasi manajemen Cafe Minamdang, untuk membantu proses pemesanan, pembayaran, pengelolaan stok, dan administrasi cafe.

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-blue?logo=apachemaven&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-007396?logo=openjfx&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-3%2B-003B57?logo=sqlite&logoColor=white)

## Cara Menjalankan Aplikasi

**Prasyarat:**  
- Java (JDK 8 atau lebih baru)
- Maven

**Langkah-langkah:**
1. Clone repository:
   ```
   git clone https://github.com/gitaa001/IF2050-2025-K3K-Cafe-Minamdang.git
   ```
2. Masuk ke direktori proyek:
   ```
   cd IF2050-2025-K3K-Cafe-Minamdang/CafeMinamdang
   ```
3. Compile dan jalankan aplikasi menggunakan Maven:
   ```
   mvn compile exec:java
   ```

## Daftar Modul yang Diimplementasi

| No. | Nama Modul      | Deskripsi Singkat            | 
|-----|-----------------|-----------------------------|
| 1   | Modul Resep         |        |
| 2   | Modul Barang        |      | 
| 3  | Invoice             |                          | 

## Daftar Tabel Basis Data

| Nama Tabel | Atribut                                 |
|------------|-----------------------------------------|
| Barang      | IDBarang, NamaBarang, Deskripsi, Kuantitas, IsiKonsinyasi, IDGudang            |
| Gudang     | IDGudang, NamaGudang, Lokasi, DeskripsiGudang   |
| Invoice        | IDInvoice, JudulInvoice, Tanggal, IDGudang, UnitPrice, Kuantitas            |
| Penjualan     | IDPenjualan, TotalHarga, Tanggal, IDGudang   |
| Resep     | IDResep, NamaResep, Deskripsi, Preskripsi   |

## Contributors:
_______

| **No** | **Nama** | **NIM**  | **Pembagian Tugas** |
| ------ | ---------------------- | ------------- | ----------- |
| 1      | Bagas Noor Fadhilah           | 18223115      |  |
| 2      | Khairunnisa Azizah  | 18223117      | 
| 3      | Laras Hati Mahendra      | 18223118      | 
| 4      | Anggita Najmi Layali| 18223122      | 
| 5      | M Rabbani K A      | 18223130      | |

**Asisten:** Angelica Kierra Ninta Gurning

**Struktur Folder Utama:**
- `CafeMinamdang/`
  - `pom.xml` — Konfigurasi Maven
  - `src/main/` — Source code utama aplikasi
  - `src/test/` — Unit test
  - `target/` — Hasil build Maven

