package com.cafeminamdang.TestModel;

import com.cafeminamdang.Model.Penjualan;
import com.cafeminamdang.TestDatabase.TestDatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PenjualanTest {

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetTablePenjualan(); // hapus semua data
        TestDatabaseHelper.insertDummyPenjualan(); // masukkan 2 data dummy
    }

    @Test
    void testGetAllPenjualan() {
        List<Penjualan> list = Penjualan.getAllPenjualan();
        assertNotNull(list);
        assertEquals(2, list.size());

        Penjualan p = list.get(0);
        assertEquals(50000, p.getTotalPenjualan());
        assertEquals("2025-06-01", p.getTanggalPenjualan());
    }

    @Test
    void testGetAllPenjualanFromSpesificWh() {
        List<Penjualan> list = Penjualan.getAllPenjualanFromSpesificWh(1);
        assertNotNull(list);
        assertEquals(1, list.size());

        Penjualan p = list.get(0);
        assertEquals(1, p.getIdGudang());
        assertEquals("2025-06-01", p.getTanggalPenjualan());
    }

    // Tambahan: test validasi setter dan getter
    @Test
    void testSetterGetter() {
        Penjualan p = new Penjualan();
        p.setIdPenjualan(99);
        p.setTotalPenjualan(123456);
        p.setTanggalPenjualan("2025-01-01");
        p.setIdGudang(5);

        assertEquals(99, p.getIdPenjualan());
        assertEquals(123456, p.getTotalPenjualan());
        assertEquals("2025-01-01", p.getTanggalPenjualan());
        assertEquals(5, p.getIdGudang());
    }
}
