package com.cafeminamdang.Controller;

import com.cafeminamdang.Model.Barang;

import java.util.List;

public class BarangController {
    
    /**
     * Controller for getting all the recipe in database.
     * Acts as an intermediary from view and model.
     * @return list of recipes
     */
    public List<Barang> getAllBarang(){
        return Barang.getAllBarang();
    }

    /**
     * Controller for saving a recipe based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a recipe as a parameter.
     * @param resep
     * @return true if recipe is saved, false otherwise
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
     * Controller for deleting a recipe based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a recipe as a parameter.
     * @param resep
     * @return true if recipe is deleted, false otherwise
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
     * Controller for creating a new recipe object to be inserted
     * on the database. Acts as an intermediary from view and model.
     * Expects namaResep, deskripsi, preskripsi as the parameter.
     * @param namaResep
     * @param deskripsi
     * @param preskripsi
     * @return newly created Resep object
     */
    public Barang createBarang(String namaBarang, String deskripsi, Integer kuantitas, Boolean isKonsinyasi, String idGudang){
        Barang barang = new Barang();
        
        // Ambil ID terakhir dari database
        String lastId = Barang.getLastBarangId(); // Misalnya "B012"
        int nextNumber = 1;

        if (lastId != null && lastId.matches("B\\d{3}")) {
            nextNumber = Integer.parseInt(lastId.substring(1)) + 1;
        }

        // Buat ID baru dengan format B###
        String newId = String.format("B%03d", nextNumber);
        barang.setIdBarang(newId);

        barang.setNamaBarang(namaBarang);
        barang.setDeskripsi(deskripsi);
        barang.setKuantitas(kuantitas);
        barang.setKonsinyasi(isKonsinyasi);
        barang.setIdGudang(idGudang);

        return barang;
    }


    /**
     * Controller for updating existing recipe object on the database. 
     * Acts as an intermediary from view and model. Expects namaResep, 
     * deskripsi, preskripsi as the parameter.
     * @param namaResep
     * @param deskripsi
     * @param preskripsi
     * @param resep
     * @return newly updated Resep object
     */
    public Barang updateBarang(String namaBarang, String deskripsi, Integer kuantitas, Boolean isKonsinyasi, String idGudang, Barang barang){
        barang.setNamaBarang(namaBarang);
        barang.setDeskripsi(deskripsi);
        barang.setKuantitas(kuantitas);
        barang.setKonsinyasi(isKonsinyasi);
        barang.setIdGudang(idGudang);
        return barang;
    }
}