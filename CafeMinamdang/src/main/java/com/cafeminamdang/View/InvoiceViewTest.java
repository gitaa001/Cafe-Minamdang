package com.cafeminamdang.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InvoiceViewTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        InvoiceView invoiceView = new InvoiceView();
        
        Scene scene = new Scene(invoiceView.getRoot());
        primaryStage.setTitle("Invoice View Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

