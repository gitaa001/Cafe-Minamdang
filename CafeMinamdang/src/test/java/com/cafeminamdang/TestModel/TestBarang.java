package com.cafeminamdang.TestModel;

import com.cafeminamdang.TestDatabase.TestDatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import com.cafeminamdang.Model.Barang;

class BarangTest {

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetTableBarang();
        TestDatabaseHelper.insertDummyBarang();
    }

    @Test
    void testGetAllBarang() {
        List<Barang> barangs = Barang.getAllBarang();
        assertEquals(2, barangs.size());

        Barang b1 = barangs.get(0);
        assertEquals("Teh Manis", b1.getNamaBarang());
    }

    @Test
    void testGetBarangByID() {
        List<Barang> all = Barang.getAllBarang();
        Integer id = all.get(0).getIDBarang();

        Barang found = Barang.getBarangByID(id);
        assertNotNull(found);
        assertEquals("Teh Manis", found.getNamaBarang());
    }

    @Test
    void testInsertBarang() {
        Barang b = new Barang(null, "Brownies", "Kue legit", 10, true, 1);
        boolean saved = b.save();
        assertTrue(saved);

        List<Barang> all = Barang.getAllBarang();
        assertEquals(3, all.size());
    }

    @Test
    void testUpdateBarang() {
        Barang b = Barang.getAllBarang().get(0);
        b.setDeskripsi("Diperbarui");
        boolean updated = b.save();
        assertTrue(updated);

        Barang updatedBarang = Barang.getBarangByID(b.getIDBarang());
        assertEquals("Diperbarui", updatedBarang.getDeskripsi());
    }

    @Test
    void testDeleteBarang() {
        Barang b = Barang.getAllBarang().get(0);
        boolean deleted = b.delete();
        assertTrue(deleted);

        Barang shouldBeNull = Barang.getBarangByID(b.getIDBarang());
        assertNull(shouldBeNull);
    }

    @Test
    void testGetBarangFromSpesificGudang() {
        List<Barang> fromGudang1 = Barang.getAllBarangFromSpesificWh(1);
        assertEquals(1, fromGudang1.size());
        assertEquals("Teh Manis", fromGudang1.get(0).getNamaBarang());

        List<Barang> fromGudang2 = Barang.getAllBarangFromSpesificWh(2);
        assertEquals(1, fromGudang2.size());
        assertEquals("Kopi Hitam", fromGudang2.get(0).getNamaBarang());
    }
}
