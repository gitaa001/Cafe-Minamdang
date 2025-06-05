package com.cafeminamdang.view;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private static ViewManager instance;
    private Stage primaryStage;
    private Map<String, Pane> viewCache = new HashMap<>();

    private ViewManager(){
    }

    public static synchronized ViewManager getInstance(){
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

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), primaryStage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            // fade out, fade in
            primaryStage.setScene(scene);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
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
