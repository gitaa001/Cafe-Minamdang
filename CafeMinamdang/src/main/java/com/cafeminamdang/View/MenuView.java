package com.cafeminamdang.View;

import com.cafeminamdang.Model.Penjualan;
import com.cafeminamdang.Controller.PenjualanController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

public class MenuView implements BaseView {
    private PenjualanController penjualanController;
    private BorderPane mainPane;
    private VBox loginCard;

    public MenuView() {
        penjualanController = new PenjualanController();
        mainPane = new BorderPane();
        mainPane.setPrefSize(1000, 700);

        HBox header = createHeader();
        mainPane.setTop(header);

        loginCard = createLoginCard();
        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getChildren().add(loginCard);
        centerPane.setStyle("-fx-background-image: url('/bg.jpg'); -fx-background-size: cover;");

        mainPane.setCenter(centerPane);

        HBox footer = createFooter();
        mainPane.setBottom(footer);
    }

    @Override
    public Pane getRoot() {
        return mainPane;
    }

    @Override
    public void refresh() {
        // No dynamic data for login
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 15, 15, 15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Label titleLabel = new Label("CafeMinamdang");
        titleLabel.setFont(loadFont("Title"));
        titleLabel.setTextFill(Color.WHITE);

        Node icon = createSvgIcon("M25.2387 4.16776L32.0431 6.71998L24.9957 9.4011L17.9483 6.71998L24.7527 4.16776C24.9089 4.10761 25.0825 4.10761 25.2474 4.16776H25.2387ZM11.8035 7.94024V17.582C11.6907 17.6163 11.5779 17.6507 11.465 17.6937L3.13314 20.8216C1.24978 21.5263 0 23.3223 0 25.316V35.5592C0 37.4669 1.13696 39.1942 2.90748 39.9676L11.2394 43.594C12.4892 44.1353 13.9038 44.1353 15.1536 43.594L24.9957 39.3059L34.8464 43.594C36.0962 44.1353 37.5109 44.1353 38.7606 43.594L47.0925 39.9676C48.8544 39.2028 50 37.4669 50 35.5592V25.3245C50 23.3223 48.7502 21.5349 46.8669 20.8216L38.535 17.6937C38.4222 17.6507 38.3093 17.6163 38.1965 17.582V7.94024C38.1965 5.93799 36.9467 4.15058 35.0634 3.43733L26.7315 0.30936C25.6206 -0.10312 24.3968 -0.10312 23.2859 0.30936L14.954 3.43733C13.0533 4.15058 11.8035 5.94658 11.8035 7.94024ZM34.0219 18.089L26.8703 20.7701V13.1048L34.0219 10.3893V18.089ZM13.4352 21.5521L20.2395 24.1043L13.1922 26.7768L6.14477 24.1043L12.9491 21.5521C13.1054 21.4919 13.2789 21.4919 13.4438 21.5521H13.4352ZM15.0668 39.1168V30.4805L22.2184 27.765V36.0061L15.0668 39.1168ZM36.5562 21.5521C36.7124 21.4919 36.886 21.4919 37.0509 21.5521L43.8552 24.1043L36.7992 26.7768L29.7518 24.1043L36.5562 21.5521ZM45.4088 36.1865L38.6738 39.1168V30.4805L45.8254 27.765V35.5592C45.8254 35.8342 45.6605 36.0748 45.4088 36.1865Z");

        header.getChildren().addAll(icon, titleLabel);
        return header;
    }

    private VBox createLoginCard() {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40, 40, 40, 40));
        card.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 30;");

        Label welcome = new Label("Selamat Datang Di Cafe Minamandang");
        welcome.setFont(loadFont("Title"));
        welcome.setTextFill(Color.web("#000000"));

        Label loginAs = new Label("login sebagai");
        loginAs.setFont(loadFont("Thin-SemiBold"));
        loginAs.setTextFill(Color.web("#000000"));

        
        ComboBox<String> authorityBox = new ComboBox<>();
        authorityBox.getItems().addAll("Business Owner", "Branch Manager", "Purchasing");
        authorityBox.setPromptText("pilih otoritas Anda");
        authorityBox.setPrefWidth(250);
        authorityBox.setStyle("-fx-font-size: 14;");
        
        Button loginBtn = new Button("Login");
        loginBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        loginBtn.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white; -fx-background-radius: 10;");
        loginBtn.setPrefWidth(250);
        loginBtn.setPrefHeight(40);
        
        Label gudang = new Label("gudang");
        gudang.setFont(loadFont("Thin-SemiBold"));
        gudang.setTextFill(Color.web("#000000"));

        List<Integer> uniqueWh = new ArrayList<>();
        List<Penjualan> penjualans = penjualanController.getAllPenjualan();
        
        ComboBox<String> gudangChoiceBox = new ComboBox<>();
        gudangChoiceBox.setPromptText("pilih gudang Anda");
        gudangChoiceBox.setPrefWidth(250);
        gudangChoiceBox.setStyle("-fx-font-size: 14;");
        
        for (Penjualan penjualan : penjualans){
            int warehouseId = penjualan.getIdGudang();
            if(!uniqueWh.contains(warehouseId)){
                uniqueWh.add(warehouseId);
                gudangChoiceBox.getItems().add("Warehouse " + warehouseId);
            }
        }

        loginBtn.setOnAction(event -> {
            String selected = authorityBox.getValue();
            Integer selectedGudang = null;
            
            if (gudangChoiceBox.getValue() != null) {
                try {
                    String warehouseStr = gudangChoiceBox.getValue().replace("Warehouse ", "");
                    selectedGudang = Integer.parseInt(warehouseStr);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Gudang tidak valid!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
            
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Silakan pilih otoritas Anda!", ButtonType.OK);
                alert.showAndWait();
                return;
            } else if ((selected.equals("Branch Manager") || selected.equals("Purchasing")) && selectedGudang == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Silakan pilih gudang Anda!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            
            ViewManager viewManager = ViewManager.getInstance();
            switch (selected) {
                case "Business Owner":
                    viewManager.setRole(Role.BUSINESS_OWNER);
                    viewManager.switchView("owner dashboard");
                    break;
                case "Branch Manager":
                    viewManager.setRole(Role.BRANCH_MANAGER);
                    viewManager.setIdGudang(selectedGudang);
                    viewManager.switchView("barang");
                    break;
                case "Purchasing":
                    viewManager.setRole(Role.PURCHASING);
                    viewManager.setIdGudang(selectedGudang);
                    viewManager.switchView("purchasing");
                    break;
                default:
                    break;
            }
        });

        card.getChildren().addAll(welcome, loginAs, authorityBox, gudang, gudangChoiceBox, loginBtn);
        return card;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #E43A3A;");

        Label copyright = new Label("\u00a9 2025 Cafe Minamdang. All rights reserved.");
        copyright.setFont(loadFont("Thin-Semibold"));
        copyright.setTextFill(Color.WHITE);

        footer.getChildren().add(copyright);
        return footer;
    }

    private Font loadFont(String type) {
        String basePath = "/fonts/";
        try {
            switch (type) {
                case "Title":
                    Font poppins = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 24);
                    return poppins != null ? poppins : Font.getDefault();
                case "Thin-SemiBold":
                    Font poppinsThin = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 10);
                    return poppinsThin != null ? poppinsThin : Font.getDefault();
                default:
                    Font poppinsReg = Font.loadFont(getClass().getResource(basePath + "Poppins-Regular.ttf").toExternalForm(), 10);
                    return poppinsReg != null ? poppinsReg : Font.getDefault();
            }
        } catch (Exception e) {
            return Font.getDefault();
        }
    }

    private Node createSvgIcon(String svgPath) {
        SVGPath path = new SVGPath();
        path.setContent(svgPath);
        path.setFill(Color.WHITE);
        path.setScaleX(0.5);
        path.setScaleY(0.5);
        return path;
    }
}