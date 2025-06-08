package com.cafeminamdang.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private Role role = null;
    private static ViewManager instance;
    private Stage primaryStage;
    private Map<String, BaseView> viewCache = new HashMap<>();
    private int currentGudang = 0;

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

    public void registerView(String name, BaseView view){
        viewCache.put(name, view);
    }

    public void switchView(String name){
        if (primaryStage == null) {
                throw new IllegalStateException("Primary stage not set");
        }
        
        BaseView view = viewCache.get(name);
        
        if (view == null) {
            switch (name) {
                case "barang":
                    view = new BarangView();
                    viewCache.put(name, view);
                    break;
                case "resep":
                    view = new ResepView();
                    viewCache.put(name, view);
                    break;
                case "owner dashboard":
                    view = new OwnerDashboard();
                    viewCache.put(name, view);
                    break;
                default:
                    throw new IllegalArgumentException("No view registered with name: " + name);
            }
        }
        
        Pane root = view.getRoot();
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(root);
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(root);
        }

        view.refresh();

        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void initializeView(String name) {
        if (primaryStage == null) {
            throw new IllegalStateException("Primary stage not set");
        }
        
        if (!viewCache.containsKey(name)) {
            throw new IllegalArgumentException("View not registered: " + name);
        }
        
        BaseView root = viewCache.get(name);
        Scene scene = new Scene(root.getRoot());
        primaryStage.setScene(scene);
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return role;
    }

    public int getIdGudang(){
        return currentGudang;
    }

    public void setIdGudang(int IdGudang){
        System.out.println(IdGudang);
        this.currentGudang = IdGudang;
    }
}