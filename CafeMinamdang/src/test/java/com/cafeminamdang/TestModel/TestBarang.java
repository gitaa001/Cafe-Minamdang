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
        assertTrue(barangs.size() >= 2); // minimal 2 dummy

        boolean found = barangs.stream().anyMatch(b -> b.getNamaBarang().equals("Teh Manis"));
        assertTrue(found);
    }

    @Test
    void testGetBarangByID() {
        Barang sample = Barang.getAllBarang().get(0);
        Barang found = Barang.getBarangByID(sample.getIDBarang());

        assertNotNull(found);
        assertEquals(sample.getNamaBarang(), found.getNamaBarang());
    }

    @Test
    void testInsertBarang() {
        int beforeSize = Barang.getAllBarang().size();

        Barang b = new Barang(null, "Brownies", "Kue legit", 10, true, 1);
        boolean saved = b.save();
        assertTrue(saved);

        int afterSize = Barang.getAllBarang().size();
        assertEquals(beforeSize + 1, afterSize);

        boolean found = Barang.getAllBarang().stream()
            .anyMatch(barang -> barang.getNamaBarang().equals("Brownies"));
        assertTrue(found);
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
        Integer id = b.getIDBarang();

        boolean deleted = b.delete();
        assertTrue(deleted);

        Barang shouldBeNull = Barang.getBarangByID(id);
        assertNull(shouldBeNull);
    }

    @Test
    void testGetBarangFromSpesificGudang() {
        List<Barang> fromGudang1 = Barang.getAllBarangFromSpesificWh(1);
        assertFalse(fromGudang1.isEmpty());

        boolean found1 = fromGudang1.stream()
            .anyMatch(b -> b.getNamaBarang().equals("Teh Manis"));
        assertTrue(found1);

        List<Barang> fromGudang2 = Barang.getAllBarangFromSpesificWh(2);
        assertFalse(fromGudang2.isEmpty());

        boolean found2 = fromGudang2.stream()
            .anyMatch(b -> b.getNamaBarang().equals("Kopi Hitam"));
        assertTrue(found2);
    }
}
