package com.cafeminamdang.Controller;

import java.util.List;

import com.cafeminamdang.Model.Barang;
import com.cafeminamdang.Model.Invoice;

public class BarangController {
    
    /**
     * Controller for getting all the inventory item in database.
     * Acts as an intermediary from view and model.
     * @return list of inventory items
     */
    public List<Barang> getAllBarang(){
        return Barang.getAllBarang();
    }

    /**
     * Controller for saving a inventory item based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a inventory item as a parameter.
     * @param barang
     * @return true if inventory item is saved, false otherwise
     */
    public boolean saveBarang(Barang barang){
        if (barang == null){
            return false;
        }

        if (barang.getNamaBarang() == null || barang.getNamaBarang().trim().isEmpty()){
            return false;
        }

        return barang.save();
    }

    /**
     * Controller for deleting a inventory item based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a inventory item as a parameter.
     * @param Barang
     * @return true if inventory item is deleted, false otherwise
     */
    public boolean deleteBarang(Barang barang){
        if (barang == null){
            return false;
        }

        if (barang.getNamaBarang() == null || barang.getNamaBarang().trim().isEmpty()){
            return false;
        }

        return barang.delete();
    }

    /**
     * Controller for creating a new inventory item object to be inserted
     * on the database. Acts as an intermediary from view and model.
     * Expects namaBarang, deskripsi, preskripsi as the parameter.
     * @param namaBarang
     * @param deskripsi
     * @param preskripsi
     * @return newly created Barang object
     */
    public Barang createBarang(String namaBarang, String deskripsi, Integer kuantitas, Boolean isKonsinyasi, Integer idGudang){
        Barang barang = new Barang();
        barang.setNamaBarang(namaBarang);
        barang.setDeskripsi(deskripsi);
        barang.setKuantitas(kuantitas);
        barang.setKonsinyasi(isKonsinyasi);
        barang.setIdGudang(idGudang);
        return barang;
    }


    /**
     * Controller for updating existing inventory item object on the database. 
     * Acts as an intermediary from view and model. Expects namaBarang, 
     * deskripsi, preskripsi as the parameter.
     * @param namaBarang
     * @param deskripsi
     * @param preskripsi
     * @param resep
     * @return newly updated Resep object
     */
    public Barang updateBarang(String namaBarang, String deskripsi, Integer kuantitas, Boolean isKonsinyasi, Integer idGudang, Barang barang){
        barang.setNamaBarang(namaBarang);
        barang.setDeskripsi(deskripsi);
        barang.setKuantitas(kuantitas);
        barang.setKonsinyasi(isKonsinyasi);
        barang.setIdGudang(idGudang);
        return barang;
    }

    /**
     * Controller for getting all the inventory item in database from spesific Warehouse.
     * Acts as an intermediary from view and model.
     * @param IDGudang
     * @return
     */
    public List<Barang> getSpesifiedWhBarang(int IDGudang){
        return Barang.getAllBarangFromSpesificWh(IDGudang);
    }
}