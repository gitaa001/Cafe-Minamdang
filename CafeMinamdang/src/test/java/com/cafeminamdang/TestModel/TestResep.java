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
        List<Resep> reseps = Resep.getAllResep();
        assertEquals(2, reseps.size());

        Resep r1 = reseps.get(0);
        assertEquals("Nasi Goreng", r1.getNamaResep());
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
        Resep r = new Resep(null, "Soto Ayam", "Kuah kuning", "Ayam, Bumbu, Air");
        boolean saved = r.save();
        assertTrue(saved);

        List<Resep> all = Resep.getAllResep();
        assertEquals(3, all.size());
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
