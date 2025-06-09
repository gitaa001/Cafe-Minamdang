package com.cafeminamdang.Controller;

import com.cafeminamdang.Model.Resep;

import java.util.List;

public class ResepController {
    
    /**
     * Controller for getting all the recipe in database.
     * Acts as an intermediary from view and model.
     * @return list of recipes
     */
    public List<Resep> getAllResep(){
        return Resep.getAllResep();
    }

    /**
     * Controller for saving a recipe based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a recipe as a parameter.
     * @param resep
     * @return true if recipe is saved, false otherwise
     */
    public boolean saveResep(Resep resep){
        if (resep == null){
            return false;
        }

        if (resep.getNamaResep() == null || resep.getNamaResep().trim().isEmpty()){
            return false;
        }

        return resep.save();
    }

    /**
     * Controller for deleting a recipe based on the input on 
     * the view. Acts as an intermediary from view and model.
     * Expects a recipe as a parameter.
     * @param resep
     * @return true if recipe is deleted, false otherwise
     */
    public boolean deleteResep(Resep resep){
        if (resep == null){
            return false;
        }

        if (resep.getNamaResep() == null || resep.getNamaResep().trim().isEmpty()){
            return false;
        }

        return resep.delete();
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
    public Resep createResep(String namaResep, String deskripsi, String preskripsi){
        Resep resep = new Resep();
        resep.setNamaResep(namaResep);
        resep.setDeskripsi(deskripsi);
        resep.setPreskripsi(preskripsi);
        return resep;
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
    public Resep updateResep(String namaResep, String deskripsi, String preskripsi, Resep resep){
        resep.setNamaResep(namaResep);
        resep.setDeskripsi(deskripsi);
        resep.setPreskripsi(preskripsi);
        return resep;
    }
}
