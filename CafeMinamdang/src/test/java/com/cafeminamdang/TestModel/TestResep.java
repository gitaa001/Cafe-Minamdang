package com.cafeminamdang.TestModel;

import com.cafeminamdang.TestDatabase.TestDatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import com.cafeminamdang.Model.Resep;

class ResepTest {

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetTableResep();
        TestDatabaseHelper.insertDummyResep();
    }

    @Test
    void testGetAllResep() {
        List<Resep> beforeInsert = Resep.getAllResep();
        int initialSize = beforeInsert.size();

        // Tambahkan satu resep baru
        Resep newResep = new Resep(null, "Nasi Goreng Spesial", "Pakai kecap dan ayam", "Nasi, Kecap, Telur, Ayam");
        boolean saved = newResep.save();
        assertTrue(saved);

        List<Resep> afterInsert = Resep.getAllResep();
        assertEquals(initialSize + 1, afterInsert.size());

        // Pastikan resep yang dimasukkan ada di list terbaru
        boolean found = afterInsert.stream().anyMatch(r ->
            r.getNamaResep().equals("Nasi Goreng Spesial") &&
            r.getDeskripsi().equals("Pakai kecap dan ayam")
        );
        assertTrue(found);
    }

    @Test
    void testGetResepByID() {
        List<Resep> all = Resep.getAllResep();
        Integer id = all.get(0).getIdResep();

        Resep found = Resep.getResepByID(id);
        assertNotNull(found);
        assertEquals("Nasi Goreng", found.getNamaResep());
    }

    @Test
    void testInsertResep() {
        int beforeSize = Resep.getAllResep().size();

        Resep r = new Resep(null, "Soto Ayam", "Kuah kuning", "Ayam, Bumbu, Air");
        boolean saved = r.save();
        assertTrue(saved);

        int afterSize = Resep.getAllResep().size();
        assertEquals(beforeSize + 1, afterSize);

        // Validasi isi data yang dimasukkan
        boolean found = Resep.getAllResep().stream().anyMatch(rp ->
            rp.getNamaResep().equals("Soto Ayam") &&
            rp.getPreskripsi().contains("Ayam")
        );
        assertTrue(found);
    }

    @Test
    void testUpdateResep() {
        Resep r = Resep.getAllResep().get(0);
        r.setDeskripsi("Diperbarui: pakai telur");
        boolean updated = r.save();
        assertTrue(updated);

        Resep updatedResep = Resep.getResepByID(r.getIdResep());
        assertEquals("Diperbarui: pakai telur", updatedResep.getDeskripsi());
    }

    @Test
    void testDeleteResep() {
        Resep r = Resep.getAllResep().get(0);
        boolean deleted = r.delete();
        assertTrue(deleted);

        Resep shouldBeNull = Resep.getResepByID(r.getIdResep());
        assertNull(shouldBeNull);
    }
}
