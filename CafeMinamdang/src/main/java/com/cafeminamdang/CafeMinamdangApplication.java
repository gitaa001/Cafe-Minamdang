package com.cafeminamdang;

import com.cafeminamdang.View.ResepView;
import com.cafeminamdang.View.ViewManager;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CafeMinamdangApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        // primaryStage.setTitle("Cafe Minamdang");
        // primaryStage.setWidth(1000);
        // primaryStage.setHeight(700);
        
        // ViewManager viewManager = ViewManager.getInstance();
        // viewManager.setPrimaryStage(primaryStage);
        
        // ResepView resepView = new ResepView();
        // viewManager.registerView("resep", resepView.getRoot());
        
        // resepView.initialize();
        // viewManager.initializeView("resep");
        
        // primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}