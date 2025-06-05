package com.cafeminamdang.View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tambah extends Application {

    @Override
    public void start(Stage stage) {
        // Membuat StackPane untuk menambahkan latar belakang
        StackPane mainLayout = new StackPane();
        
        // Membuat GridPane untuk menampilkan gambar makanan dan deskripsi
        GridPane foodGrid = new GridPane();
        foodGrid.setHgap(20);
        foodGrid.setVgap(20);

        // Tombol untuk tambah item
        Button addButton = new Button("Tambah Item");
        addButton.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");
        addButton.setPrefSize(150, 40);

        // Menambahkan GridPane dan tombol ke VBox
        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().addAll(foodGrid, addButton);
        
        // Menambahkan contentLayout ke mainLayout (di atas background)
        mainLayout.getChildren().add(contentLayout);

        // Scene dan stage
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("Cafe Minamdang");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
