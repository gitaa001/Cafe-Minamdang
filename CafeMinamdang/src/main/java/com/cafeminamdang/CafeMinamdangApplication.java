package com.cafeminamdang;
import com.cafeminamdang.View.BarangView;
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
        
        BarangView barangView = new BarangView();
        viewManager.registerView("barang", barangView.getRoot());
        
        viewManager.initializeView("barang");
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}