package com.cafeminamdang;

import com.cafeminamdang.View.MenuView;
import com.cafeminamdang.View.ViewManager;

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