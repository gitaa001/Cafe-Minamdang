package com.cafeminamdang.controller;

import java.util.List;

import com.cafeminamdang.Model.Penjualan;

public class PenjualanController {

    /**
     * Controller for getting all sales in database.
     * Acts as an intermediary from view and model.
     * @return list of sales
     */
    public List<Penjualan> getAllPenjualan(){
        return Penjualan.getAllPenjualan();
    }
}