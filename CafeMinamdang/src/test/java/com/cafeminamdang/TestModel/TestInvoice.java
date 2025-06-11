package com.cafeminamdang.TestModel;

import com.cafeminamdang.Model.Invoice;
import com.cafeminamdang.TestDatabase.TestDatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetTableInvoice();
        TestDatabaseHelper.insertDummyInvoice();
    }

    @Test
    void testGetAllInvoice() {
        List<Invoice> invoices = Invoice.getAllInvoice();
        assertEquals(5, invoices.size());

        Invoice inv1 = invoices.get(0);
        assertEquals("Pembelian Kopi Arabica", inv1.getJudulInvoice());
    }

    @Test
    void testGetInvoiceByID() {
        List<Invoice> all = Invoice.getAllInvoice();
        int id = all.get(0).getIdInvoice();

        Invoice found = Invoice.getInvoiceById(id);
        assertNotNull(found);
        assertEquals("Pembelian Kopi Arabica", found.getJudulInvoice());
    }

    @Test
    void testInsertInvoice() {
        int beforeSize = Invoice.getAllInvoice().size();

        Invoice inv = new Invoice("Pembelian Rendang Nendank", "2025-06-11", 1, new BigDecimal("50000"), 2);
        boolean saved = inv.save();
        assertTrue(saved);

        int afterSize = Invoice.getAllInvoice().size();
        assertEquals(beforeSize + 1, afterSize); // ← fleksibel, no hardcoded
    }

    @Test
    void testUpdateInvoice() {
        Invoice inv = Invoice.getAllInvoice().get(0);
        inv.setJudulInvoice("Diupdate");
        boolean updated = inv.save();
        assertTrue(updated);

        Invoice updatedInv = Invoice.getInvoiceById(inv.getIdInvoice());
        assertEquals("Diupdate", updatedInv.getJudulInvoice());
    }

    @Test
    void testDeleteInvoice() {
        Invoice inv = Invoice.getAllInvoice().get(0);
        boolean deleted = Invoice.deleteInvoiceById(inv.getIdInvoice());
        assertTrue(deleted);

        Invoice shouldBeNull = Invoice.getInvoiceById(inv.getIdInvoice());
        assertNull(shouldBeNull);
    }

    @Test
    void testGetInvoiceFromSpecificGudang() {
        List<Invoice> all = Invoice.getAllInvoice();
        long expected = all.stream().filter(i -> i.getIdGudang() == 1).count();

        List<Invoice> fromGudang1 = Invoice.getAllInvoiceFromSpecificWh(1);
        assertEquals(expected, fromGudang1.size()); // ← no hardcoded
    }


    @Test
    void testSortByNominal() {
        List<Invoice> sorted = Invoice.sortByNominal();
        assertTrue(sorted.size() >= 2);
        BigDecimal first = sorted.get(0).getHargaTotalInvoice();
        BigDecimal second = sorted.get(1).getHargaTotalInvoice();
        assertTrue(first.compareTo(second) >= 0);
    }

    @Test
    void testSortByDate() {
        List<Invoice> sorted = Invoice.sortByDate();
        assertTrue(sorted.size() >= 2);
        String firstDate = sorted.get(0).getTanggal();
        String secondDate = sorted.get(1).getTanggal();
        assertTrue(firstDate.compareTo(secondDate) >= 0);
    }
}