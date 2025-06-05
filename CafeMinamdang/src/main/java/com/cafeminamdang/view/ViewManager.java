package com.cafeminamdang.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private static ViewManager instance;
    private Stage primaryStage;
    private Map<String, Pane> viewCache = new HashMap<>();

    private ViewManager(){
    }

    public static ViewManager getInstance(){
        if (instance == null){
            instance = new ViewManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void registerView(String name, Pane view){
        viewCache.put(name, view);
    }

    public void switchView(String name){
        if (primaryStage == null){
            throw new IllegalStateException("Primary stage not set");
        }

        if (!viewCache.containsKey(name)){
            throw new IllegalArgumentException("View not registered: " + name);
        }

        Pane root = viewCache.get(name);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
    }

    public void initializeView(String name) {
        if (primaryStage == null) {
            throw new IllegalStateException("Primary stage not set");
        }
        
        if (!viewCache.containsKey(name)) {
            throw new IllegalArgumentException("View not registered: " + name);
        }
        
        Pane root = viewCache.get(name);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
}
