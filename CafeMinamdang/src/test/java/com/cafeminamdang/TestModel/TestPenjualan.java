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
        assertTrue(list.size() >= 2); // fleksibel, minimal 2 dummy

        boolean hasValidPenjualan = list.stream().anyMatch(p ->
            p.getTotalPenjualan() == 50000 &&
            "2025-06-01".equals(p.getTanggalPenjualan())
        );
        assertTrue(hasValidPenjualan);
    }

    @Test
    void testGetAllPenjualanFromSpesificWh() {
        List<Penjualan> list = Penjualan.getAllPenjualanFromSpesificWh(1);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        boolean allFromGudang1 = list.stream().allMatch(p -> p.getIdGudang() == 1);
        assertTrue(allFromGudang1);

        boolean hasExpectedDate = list.stream().anyMatch(p ->
            "2025-06-01".equals(p.getTanggalPenjualan())
        );
        assertTrue(hasExpectedDate);
    }

    @Test
    void testSetterGetter() {
        Penjualan p = new Penjualan();
        p.setIdPenjualan(99);
        p.setTotalPenjualan(123456);
        p.setTanggalPenjualan("2025-01-01");
        p.setIdGudang(5);

        assertAll(
            () -> assertEquals(99, p.getIdPenjualan()),
            () -> assertEquals(123456, p.getTotalPenjualan()),
            () -> assertEquals("2025-01-01", p.getTanggalPenjualan()),
            () -> assertEquals(5, p.getIdGudang())
        );
    }
}
