-- Gudang
CREATE TABLE Gudang (
    IDGudang INTEGER PRIMARY KEY,
    NamaGudang VARCHAR(24) NOT NULL,
    Lokasi TEXT NOT NULL,
    DeskripsiGudang TEXT
);

-- Barang
CREATE TABLE Barang (
    IDBarang INTEGER PRIMARY KEY,
    NamaBarang VARCHAR(24) NOT NULL,
    Deskripsi TEXT NOT NULL,
    Kuantitas INTEGER NOT NULL,
    IsKonsinyasi BOOLEAN NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang)
);

-- Invoice
CREATE TABLE Invoice (
    IDInvoice INTEGER PRIMARY KEY,
    JudulInvoice VARCHAR(24) NOT NULL,
    Tanggal DATE NOT NULL,
    Kuantitas INTEGER NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang)
);

-- Resep
CREATE TABLE Resep (
    IDInvoice INTEGER PRIMARY KEY,
    NamaResep VARCHAR(24) NOT NULL,
    Deskripsi TEXT NOT NULL,
    Preskripsi TEXT NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang)
);

-- Transfer
CREATE TABLE Transfer (
    IDTransfer INTEGER PRIMARY KEY,
    Domain VARCHAR(24) NOT NULL,
    Target VARCHAR(24) NOT NULL,
    IDBarang INTEGER NOT NULL,
    JumlahBarang INTEGER NOT NULL,
    Tanggal DATE NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang),
    FOREIGN KEY (IDBarang) REFERENCES Barang(IDBarang)
);

-- Penjualan
CREATE TABLE Penjualan (
    IDPenjualan INTEGER PRIMARY KEY,
    TotalPenjualan INTEGER NOT NULL,
    Tanggal DATE NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang)
);

-- Laporan
CREATE TABLE Laporan (
    IDLaporan INTEGER PRIMARY KEY,
    Sum INTEGER NOT NULL,
    Startdate DATE NOT NULL,
    Enddate DATE NOT NULL,
    IDGudang INTEGER NOT NULL,
    FOREIGN KEY (IDGudang) REFERENCES Gudang(IDGudang)
);
