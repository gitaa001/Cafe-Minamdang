package com.cafeminamdang.Controller;

import com.cafeminamdang.Model.Invoice;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceController {
    /**
     * Gets all invoices from the database.
     *
     * @return A list of all Invoice objects in the database.
     */
    public List<Invoice> getAllInvoice() {
        return Invoice.getAllInvoice();
    }

    /**
     * Gets an invoice by its ID from the database.
     *
     * @param idInvoice ID of the invoice to retrieve.
     * @return The Invoice object with the given ID, or null if none found.
     */
    public Invoice getInvoiceById(int idInvoice) {
        return Invoice.getInvoiceById(idInvoice);
    }



    /**
     * Saves an invoice to the database. If the invoice ID is 0, a new invoice
     * will be inserted. Otherwise, the invoice with the given ID will be
     * updated.
     *
     * @param invoice The invoice to save.
     * @return True if the invoice was successfully saved, false otherwise.
     */
    public boolean saveInvoice(Invoice invoice) {
        return invoice.save();
    }

    /**
     * Deletes an invoice from the database.
     *
     * @param invoice The invoice to delete.
     * @return True if the invoice was successfully deleted, false otherwise.
     */
    public boolean deleteInvoice(Invoice invoice) {
        return Invoice.deleteInvoiceById(invoice.getIdInvoice());
    }

    /**
     * Creates a new invoice with the given properties.
     *
     * @param judulInvoice The title of the invoice.
     * @param tanggal The date of the invoice.
     * @param idGudang The ID of the warehouse.
     * @param unitPrice The unit price of the invoice.
     * @param kuantitas The quantity of the invoice.
     * @return The newly created Invoice object.
     */
    public Invoice createInvoice(String judulInvoice, String tanggal, int idGudang, BigDecimal unitPrice, int kuantitas) {
        return new Invoice(judulInvoice, tanggal, idGudang, unitPrice, kuantitas);
    }

    /**
     * Updates the given invoice's properties. This does not modify the database.
     *
     * @param invoice The invoice to update.
     * @param judulInvoice The new title of the invoice.
     * @param tanggal The new date of the invoice.
     * @param idGudang The new ID of the warehouse.
     * @param unitPrice The new unit price of the invoice.
     * @param kuantitas The new quantity of the invoice.
     * @return The updated Invoice object.
     */
    public Invoice updateInvoice(Invoice invoice, String judulInvoice, String tanggal, int idGudang, BigDecimal unitPrice, int kuantitas) {
        invoice.setJudulInvoice(judulInvoice);
        invoice.setTanggal(tanggal);
        invoice.setIdGudang(idGudang);
        invoice.setUnitPrice(unitPrice);
        invoice.setKuantitas(kuantitas);
        return invoice;
    }

    /**
     * Retrieves a list of all invoices sorted by their nominal value (total price) in descending order.
     *
     * @return A list of Invoice objects sorted by total price in descending order.
     */

    public List<Invoice> sortByNominal() {
        return Invoice.sortByNominal();
    }

    /**
     * Retrieves a list of all invoices sorted by their date in descending order.
     *
     * @return A list of Invoice objects sorted by date in descending order.
     */
    public List<Invoice> sortByDate() {
        return Invoice.sortByDate();
    }
}