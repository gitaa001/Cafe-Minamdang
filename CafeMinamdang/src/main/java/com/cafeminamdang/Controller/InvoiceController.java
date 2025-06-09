package com.cafeminamdang.Controller;

import com.cafeminamdang.Model.Invoice;
import com.cafeminamdang.Model.Barang;

import java.util.Date;
import java.util.List;

public class InvoiceController {
    
    /**
     * Controller for getting all invoices in database.
     * Acts as an intermediary from view and model.
     * @return list of invoices
     */
    public List<Invoice> getAllInvoices() {
        return Invoice.getAllInvoices();
    }

    /**
     * Controller for saving an invoice based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects an invoice as a parameter.
     * @param invoice
     * @return true if invoice is saved, false otherwise
     */
    public boolean saveInvoice(Invoice invoice) {
        if (invoice == null) {
            return false;
        }

        if (invoice.getJudulInvoice() == null || invoice.getJudulInvoice().trim().isEmpty()) {
            return false;
        }

        invoice.addInvoice(invoice);
        return true;
    }

    /**
     * Controller for deleting an invoice based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects an invoice ID as a parameter.
     * @param invoiceId
     * @return true if invoice is deleted, false otherwise
     */
    public boolean deleteInvoice(Integer invoiceId) {
        if (invoiceId == null) {
            return false;
        }

        return Invoice.deleteInvoiceById(invoiceId);
    }

    /**
     * Controller for deleting an invoice based on the string ID input
     * Converts the string ID to Integer and calls the main delete method
     * @param invoiceIdStr String representation of invoice ID
     * @return true if invoice is deleted, false otherwise
     */
    public boolean deleteInvoice(String invoiceIdStr) {
        if (invoiceIdStr == null || invoiceIdStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            Integer invoiceId = Integer.parseInt(invoiceIdStr);
            return deleteInvoice(invoiceId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Controller for creating a new invoice object to be inserted
     * on the database. Acts as an intermediary from view and model.
     * @param judulInvoice title of the invoice
     * @param tanggal date of the invoice
     * @param barang item referenced by the invoice
     * @param hargaTotal total price
     * @param kuantitas quantity
     * @param idGudang warehouse ID
     * @return newly created Invoice object
     */
    public Invoice createInvoice(String judulInvoice, Date tanggal, Barang barang, Integer hargaTotal, Integer kuantitas, Integer idGudang) {
        return new Invoice(judulInvoice, tanggal, barang, hargaTotal, kuantitas, idGudang);
    }

    /**
     * Controller for getting an invoice by ID
     * @param invoiceId ID of the invoice to retrieve
     * @return Invoice object if found, null otherwise
     */
    public Invoice getInvoiceById(Integer invoiceId) {
        if (invoiceId == null) {
            return null;
        }
        return Invoice.getInvoiceById(invoiceId);
    }
    
    /**
     * Controller for getting an invoice by string ID
     * Converts the string ID to Integer and calls the main get method
     * @param invoiceIdStr String representation of invoice ID
     * @return Invoice object if found, null otherwise
     */
    public Invoice getInvoiceById(String invoiceIdStr) {
        if (invoiceIdStr == null || invoiceIdStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            Integer invoiceId = Integer.parseInt(invoiceIdStr);
            return getInvoiceById(invoiceId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Controller for getting invoices sorted by nominal amount
     * @return list of invoices sorted by price
     */
    public List<Invoice> getInvoicesSortedByNominal() {
        return Invoice.sortByNominal();
    }
    
    /**
     * Controller for getting invoices sorted by date
     * @return list of invoices sorted by date
     */
    public List<Invoice> getInvoicesSortedByDate() {
        return Invoice.sortByDate();
    }
}