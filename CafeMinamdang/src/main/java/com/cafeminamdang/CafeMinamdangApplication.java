package com.cafeminamdang;

import com.cafeminamdang.view.BarangView;
import com.cafeminamdang.view.MenuView;
import com.cafeminamdang.view.OwnerDashboard;
import com.cafeminamdang.view.ResepView;
import com.cafeminamdang.view.ViewManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class CafeMinamdangApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Cafe Minamdang");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setPrimaryStage(primaryStage);
        
        BarangView barangView = new BarangView();
        viewManager.registerView("branch manager dashboard", barangView.getRoot());
        
        MenuView menuView = new MenuView();
        viewManager.registerView("menu", menuView.getRoot());
        
        ResepView resepView = new ResepView();
        viewManager.registerView("resep", resepView.getRoot());

        OwnerDashboard ownerDashboard = new OwnerDashboard();
        viewManager.registerView("owner dashboard", ownerDashboard.getRoot());
        
        viewManager.initializeView("menu");
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}