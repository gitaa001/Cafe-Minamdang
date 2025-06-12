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
        
        if (resep.getNamaResep() == null || resep.getNamaResep().trim().isEmpty()) return false;
        String[] words = resep.getNamaResep().trim().split("\\s+");
        if (words.length > 20) return false;

        if (!resep.getNamaResep().matches("^[a-zA-Z\\s]+$")) {
            System.out.println("Failed regex check for name: " + resep.getNamaResep());
            return false;
        }
        resep.setNamaResep(resep.getNamaResep().trim());

        if (resep.getDeskripsi() == null || resep.getDeskripsi().trim().isEmpty()) return false;

        String[] deskripsi = resep.getDeskripsi().trim().split("\\s+");
        if (deskripsi.length >= 100) return false;

        if (!resep.getDeskripsi().matches("^[a-zA-Z\\s]+$")) return false;
        resep.setDeskripsi(resep.getDeskripsi().trim());
  
        if (resep.getPreskripsi() == null || resep.getPreskripsi().trim().isEmpty()) return false;

        String[] preskripsi = resep.getPreskripsi().trim().split("\\s+");
        if (preskripsi.length > 500) return false;
 
        if (resep.getPreskripsi() == null || resep.getPreskripsi().trim().isEmpty()){
            return false;
        }
        resep.setPreskripsi(resep.getPreskripsi().trim());

        System.out.println("tes9");
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
