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
        
        MenuView menuView = new MenuView();
        viewManager.registerView("menu", menuView);
        
        viewManager.initializeView("menu");
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}