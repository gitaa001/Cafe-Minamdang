package com.cafeminamdang.View;

import com.cafeminamdang.Controller.BarangController;
import com.cafeminamdang.Model.Barang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Node;

import java.util.List;
import java.util.Optional;

public class BarangView implements BaseView {
    private final BarangController barangController;
    private final BorderPane mainPanel;
    private VBox listView;
    private VBox detailView;
    private VBox formView;
    private ObservableList<Barang> dataBarang;
    private TableView<Barang> tableView;

    public BarangView() {
        barangController = new BarangController();

        mainPanel = new BorderPane();
        mainPanel.setPrefSize(1000, 700);

        HBox header = createHeader();
        mainPanel.setTop(header);

        listView = createListView();
        detailView = createDetailView();
        formView = createFormView();

        mainPanel.setCenter(listView);

        HBox footer = createFooter();
        mainPanel.setBottom(footer);
    }

    @Override
    public Pane getRoot() {
        return mainPanel;
    }

    @Override
    public void refresh() {
        loadData();
    }

    private void loadData() {
        List<Barang> barangs = barangController.getAllBarang();
        dataBarang = FXCollections.observableArrayList(barangs);
        tableView.setItems(dataBarang);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Label titleLabel = new Label("CafeMinamdang - Barang");
        titleLabel.setFont(loadFont("Title"));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);

        Node icon = createSvgIcon("M25.2387 4.16776L32.0431 6.71998L24.9957 9.4011L17.9483 6.71998L24.7527 4.16776C24.9089 4.10761 25.0825 4.10761 25.2474 4.16776H25.2387ZM11.8035 7.94024V17.582C11.6907 17.6163 11.5779 17.6507 11.465 17.6937L3.13314 20.8216C1.24978 21.5263 0 23.3223 0 25.316V35.5592C0 37.4669 1.13696 39.1942 2.90748 39.9676L11.2394 43.594C12.4892 44.1353 13.9038 44.1353 15.1536 43.594L24.9957 39.3059L34.8464 43.594C36.0962 44.1353 37.5109 44.1353 38.7606 43.594L47.0925 39.9676C48.8544 39.2028 50 37.4669 50 35.5592V25.3245C50 23.3223 48.7502 21.5349 46.8669 20.8216L38.535 17.6937C38.4222 17.6507 38.3093 17.6163 38.1965 17.582V7.94024C38.1965 5.93799 36.9467 4.15058 35.0634 3.43733L26.7315 0.30936C25.6206 -0.10312 24.3968 -0.10312 23.2859 0.30936L14.954 3.43733C13.0533 4.15058 11.8035 5.94658 11.8035 7.94024ZM34.0219 18.089L26.8703 20.7701V13.1048L34.0219 10.3893V18.089ZM13.4352 21.5521L20.2395 24.1043L13.1922 26.7768L6.14477 24.1043L12.9491 21.5521C13.1054 21.4919 13.2789 21.4919 13.4438 21.5521H13.4352ZM15.0668 39.1168V30.4805L22.2184 27.765V36.0061L15.0668 39.1168ZM36.5562 21.5521C36.7124 21.4919 36.886 21.4919 37.0509 21.5521L43.8552 24.1043L36.7992 26.7768L29.7518 24.1043L36.5562 21.5521ZM45.4088 36.1865L38.6738 39.1168V30.4805L45.8254 27.765V35.5592C45.8254 35.8342 45.6605 36.0748 45.4088 36.1865Z");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #E43A3A;");
        backButton.setOnAction(e -> ViewManager.getInstance().switchView("owner dashboard"));

        header.getChildren().addAll(icon, titleLabel, spacer, backButton);
        return header;
    }

    private VBox createListView(){
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Barang List");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("Add Barang");
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"); 
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;"));
        addButton.setOnAction(e -> showFormView(null)); // Resep baru makanya passing null
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"));
        controlBar.getChildren().addAll(titleLabel, spacer, addButton);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Penerapan callback function dengan melakukan binding suatu objek dengan getter pada modal
        TableColumn<Barang, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDBarangProperty());

        TableColumn<Barang, String> namaColumn = new TableColumn<>("Nama Barang");
        namaColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getNamaBarang()));

        
        TableColumn<Barang, String> stockColumn = new TableColumn<>("Stok");
        stockColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> String.valueOf(cellData.getValue().getKuantitas())));

        TableColumn<Barang, String> jenisColumn = new TableColumn<>("Konsinyasi");
        jenisColumn.setCellValueFactory(cellData -> new javafx.beans.property.ReadOnlyStringWrapper(
                cellData.getValue().getIsKonsinyasi() ? "Ya" : "Tidak"));

        TableColumn<Barang, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(150);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(5, viewBtn, editBtn, deleteBtn);
            
            {
                viewBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                editBtn.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                
                // getTableView().getItems.get(getIndex()); mendapatkan item pada index row tersebut
                viewBtn.setOnAction(event -> {
                    Barang resep = getTableView().getItems().get(getIndex());
                    showDetailView(resep);
                });
                
                editBtn.setOnAction(event -> {
                    Barang resep = getTableView().getItems().get(getIndex());
                    showFormView(resep);
                });
                
                deleteBtn.setOnAction(event -> {
                    Barang resep = getTableView().getItems().get(getIndex());
                    deleteResep(resep);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
        
        tableView.getColumns().addAll(idColumn, namaColumn, stockColumn, jenisColumn, actionsColumn);
        
        // Set up data
        loadData();
        
        container.getChildren().addAll(controlBar, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        return container;
    }

    private VBox createDetailView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        // Holder (wrapper) buat konten resep yang asli *bakal di populate sama konten masing-masing di showDetailView
        return container;
    }

    private VBox createFormView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label formTitle = new Label("Add New Barang");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 0, 0, 0));

        Label nameLabel = new Label("Recipe Name:");
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);

        Label descLabel = new Label("Description:");
        TextArea descArea = new TextArea();
        descArea.setPrefRowCount(3);
        descArea.setWrapText(true);

        Label stockLabel = new Label("Stock:"); //format awalnya integer, harus parsing?
        Spinner<Integer> stockSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0); // min 0, max tak terbatas, default 0
        stockSpinner.setEditable(true);
        stockSpinner.setPrefWidth(300);

        Label konsinyasiLabel = new Label("Konsinyasi:");
        CheckBox konsinyasiCheckBox = new CheckBox("Apakah barang konsinyasi?");

        Label idGudangLabel = new Label("ID Gudang:");
        TextArea idGudangArea = new TextArea();
        idGudangArea.setPrefRowCount(6);
        idGudangArea.setWrapText(true);

        // Menaruh UI berdasarkan grid dengan parameter (kolom,row)
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(descLabel, 0, 1);
        form.add(descArea, 1, 1);
        form.add(stockLabel, 0, 2);
        form.add(stockSpinner, 1, 2);
        form.add(konsinyasiLabel, 0, 3);
        form.add(konsinyasiCheckBox, 1, 3);
        form.add(idGudangLabel, 0, 4);
        form.add(idGudangArea, 1, 4);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> showListView());

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill:white;");
        saveButton.setOnAction(e -> {
            // Mengambil data dari passing parameter pada showFormView(Resep resep)
            Barang barang = (Barang) form.getUserData();
            if (barang == null){
                barang = barangController.createBarang(nameField.getText(), descArea.getText(), stockSpinner.getValue(), konsinyasiCheckBox.isSelected(), idGudangArea.getText());
            } else {
                // Update resep
                barang = barangController.updateBarang(nameField.getText(), descArea.getText(), stockSpinner.getValue(), konsinyasiCheckBox.isSelected(), idGudangArea.getText(), barang);
            }

            boolean success = barangController.saveResep(barang);

            if (success){
                showListView();
                loadData();
            } else {
                showAlert("Error", "Failed to save recipe!");
            }
        });

        buttonBar.getChildren().addAll(cancelButton, saveButton);
        
        container.getChildren().addAll(formTitle, form, buttonBar);
        return container;
    }

    private void deleteResep(Barang barang) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete Barang");
        confirmDialog.setHeaderText("Delete " + barang.getNamaBarang() + "?");
        confirmDialog.setContentText("This action cannot be undone.");

        DialogPane dialogPane = confirmDialog.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #F8F8F8;" +
            "-fx-font-family: 'Arial';"
        );

        Label headLabel = (Label) dialogPane.lookup(".header-panel .label");
        headLabel.setFont(loadFont("Thin-SemiBold"));


        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setFont(loadFont("Thin-SemiBold"));
        }

        dialogPane.getButtonTypes().stream().map(dialogPane::lookupButton).forEach(button -> {button.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;");
            ((Button)button).setFont(loadFont("Thin-SemiBold"));
        });

        // Berdasarkan dokumentasi showAndWait berfungsi untuk melakukan blocking eksekusi dengan menunggu hasil masukan 
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            boolean success = barangController.deleteBarang(barang);
            if (success) {
                loadData();
            } else {
                showAlert("Error", "Failed to delete recipe!");
            }
        }
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #E43A3A;");

        Label copyright = new Label("\u00a9 2025 Cafe Minamdang. All rights reserved.");
        copyright.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
        copyright.setTextFill(Color.WHITE);

        footer.getChildren().add(copyright);
        return footer;
    }

    private void showListView(){
        mainPanel.setCenter(listView);
    }

    private void showDetailView(Barang barang){
        detailView.getChildren().clear();

        Label titleLabel = new Label(barang.getNamaBarang());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox details = new VBox(15);
        details.setPadding(new Insets(20, 0, 0, 0));

        Label descTitle = new Label("Description");
        descTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label descText = new Label(barang.getDeskripsi());
        descText.setWrapText(true);

        Label stockTitle = new Label("Quantity");
        stockTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label stock = new Label(String.valueOf(barang.getKuantitas()));
        stock.setWrapText(true);

        Label jenisTitle = new Label("Konsinyasi");
        jenisTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label konsinyasi = new Label(barang.getIsKonsinyasi() ? "Ya" : "Bukan");
        konsinyasi.setWrapText(true);

        Label idGudangTitle = new Label("ID Gudang");
        idGudangTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label idGudang = new Label(barang.getIDGudang());
        idGudang.setWrapText(true);

        details.getChildren().addAll(descTitle, descText, stockTitle, jenisTitle, idGudangTitle);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button backButton = new Button("Back to list barang");
        backButton.setOnAction(e -> showListView());

        Button editButton = new Button("Edit Barang");
        editButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill:white;");
        editButton.setOnAction(e -> showFormView(barang));

        buttonBar.getChildren().addAll(backButton, editButton);

        detailView.getChildren().addAll(titleLabel, details, buttonBar);
        mainPanel.setCenter(detailView);
    }

    private void showFormView(Barang barang){
        // Ambil children dari formView
        // container.getChildren().addAll(formTitle, form, buttonBar); *Acuan di createFormView
        GridPane form = (GridPane) formView.getChildren().get(1);
        Label formTitle = (Label) formView.getChildren().get(0); 

        // Kalo edit bakal ada resep di masukin ke node form
        form.setUserData(barang);

        // form.add(nameLabel, 0, 0);
        // form.add(nameField, 1, 0);
        // form.add(descLabel, 0, 1);
        // form.add(descArea, 1, 1);
        // form.add(instructionsLabel, 0, 2);
        // form.add(instructionsArea, 1, 2); *Acuan di createFormView
        TextField nameField = (TextField) form.getChildren().get(1);
        TextArea descArea = (TextArea) form.getChildren().get(3);
        Spinner<Integer> stockArea = (Spinner<Integer>) form.getChildren().get(5);
        CheckBox konsinyasiArea = (CheckBox) form.getChildren().get(9);
        TextArea idGudangArea = (TextArea) form.getChildren().get(5);

        if (barang == null){
            formTitle.setText("Add New Recipe");
            nameField.clear();
            descArea.clear();
            stockArea.getValueFactory().setValue(0);
            konsinyasiArea.setSelected(false);
            idGudangArea.clear();
        } else {
            formTitle.setText("Edit Recipe");
            nameField.setText(barang.getNamaBarang());
            descArea.setText(barang.getDeskripsi());
            stockArea.getValueFactory().setValue(barang.getKuantitas());
            konsinyasiArea.setSelected(barang.getIsKonsinyasi());
            idGudangArea.setText(barang.getIDGudang());
        }

        mainPanel.setCenter(formView);
    }

    private Node createSvgIcon(String svgPath) {
        SVGPath path = new SVGPath();
        path.setContent(svgPath);
        path.setFill(Color.WHITE);

        path.setScaleX(0.5);
        path.setScaleY(0.5);
        
        return path;
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        
        DialogPane dialogPane = alert.getDialogPane();

        Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setFont(loadFont("Thin-SemiBold"));
            headerLabel.setTextFill(Color.WHITE);
        }   
            
        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setFont(loadFont("Thin-SemiBold"));
        }
        alert.showAndWait();
    }

    private Font loadFont(String Type) {
        String basePath = "/fonts/";
        switch (Type) {
            case "Title":
                try {
                    Font poppins = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 24);
                    return poppins != null ? poppins : Font.getDefault();
                } catch (Exception e){
                    e.printStackTrace();
                    return new Font("System", 24);
                }
            case "Thin-SemiBold":
                try {
                    Font poppins = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 10);
                    return poppins != null ? poppins : Font.getDefault();
                } catch (Exception e){
                    e.printStackTrace();
                    return new Font("System", 16);
                }
            default:
                try {
                    Font poppins = Font.loadFont(getClass().getResource(basePath + "Poppins-Regular.ttf").toExternalForm(), 10);
                    return poppins != null ? poppins : Font.getDefault();
                } catch (Exception e){
                    e.printStackTrace();
                    return new Font("System", 10);
                }
        }
    }   

    private Button createIconButton(String svgPath, double size, Color iconColor, String btnColor, String hoverColor, String text){
        SVGPath path = new SVGPath();
        path.setContent(svgPath);
        path.setFill(iconColor);

        StackPane iconContainer = new StackPane();
        iconContainer.getChildren().add(path);

        Button button = new Button(text);
        button.setFont(loadFont("Thin-SemiBold"));
        button.setTextFill(iconColor);
        button.setGraphic(iconContainer);
        button.setGraphicTextGap(10);
        String buttonStyle = 
            "-fx-background-color: " + btnColor + ";" +
            "-fx-min-height: " + size + "px;" +
            "-fx-padding: 0 15px 0 15px;"; 
        
        button.setStyle(buttonStyle);
        
        String hoverStyle = 
            "-fx-background-color: " + hoverColor + ";" +
            "-fx-min-height: " + size + "px;" +
            "-fx-padding: 0 15px 0 15px;" +
            "-fx-cursor: hand;";
        
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));
        
        return button;
    }

}
