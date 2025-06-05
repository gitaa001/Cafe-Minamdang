package com.cafeminamdang.view;

import com.cafeminamdang.model.Resep;
import com.cafeminamdang.controller.ResepController;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.Node;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class ResepView implements BaseView {
    private BorderPane mainPane;
    private VBox listView;
    private VBox detailView;
    private VBox formView;
    private ObservableList<Resep> dataResep;
    private TableView<Resep> tableView;
    private Font helvetica = Font.font("file:resources/Poppins-SemiBold.ttf", 12);

    public ResepView(){
        mainPane = new BorderPane();
        mainPane.setPrefSize(800, 600);

        HBox header = createHeader();
        mainPane.setTop(header);

        listView = createListView();
        detailView = createDetailView();
        formView = createFormView();

        mainPane.setCenter(listView);

        HBox footer = createFooter();
        mainPane.setBottom(footer);
    }

    @Override
    public Pane getRoot(){
        return mainPane;
    }

    @Override
    public void refresh(){
        loadData();
    }

    /**
     * Refreshing the content of table view by
     * wrapping the recipes with an observable list
     * to satisfy tableview parameter.
     */
    private void loadData(){
        List<Resep> reseps = Resep.getAllResep();
        dataResep = FXCollections.observableArrayList(reseps); // Wraping list ke observable list
        if (tableView != null){
            tableView.setItems(dataResep); // Table view mempunyai parameter observable list
        }
    }

    private HBox createHeader(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 15, 15, 15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Node icon = createSvgIcon("M25.2387 4.16776L32.0431 6.71998L24.9957 9.4011L17.9483 6.71998L24.7527 4.16776C24.9089 4.10761 25.0825 4.10761 25.2474 4.16776H25.2387ZM11.8035 7.94024V17.582C11.6907 17.6163 11.5779 17.6507 11.465 17.6937L3.13314 20.8216C1.24978 21.5263 0 23.3223 0 25.316V35.5592C0 37.4669 1.13696 39.1942 2.90748 39.9676L11.2394 43.594C12.4892 44.1353 13.9038 44.1353 15.1536 43.594L24.9957 39.3059L34.8464 43.594C36.0962 44.1353 37.5109 44.1353 38.7606 43.594L47.0925 39.9676C48.8544 39.2028 50 37.4669 50 35.5592V25.3245C50 23.3223 48.7502 21.5349 46.8669 20.8216L38.535 17.6937C38.4222 17.6507 38.3093 17.6163 38.1965 17.582V7.94024C38.1965 5.93799 36.9467 4.15058 35.0634 3.43733L26.7315 0.30936C25.6206 -0.10312 24.3968 -0.10312 23.2859 0.30936L14.954 3.43733C13.0533 4.15058 11.8035 5.94658 11.8035 7.94024ZM34.0219 18.089L26.8703 20.7701V13.1048L34.0219 10.3893V18.089ZM13.4352 21.5521L20.2395 24.1043L13.1922 26.7768L6.14477 24.1043L12.9491 21.5521C13.1054 21.4919 13.2789 21.4919 13.4438 21.5521H13.4352ZM15.0668 39.1168V30.4805L22.2184 27.765V36.0061L15.0668 39.1168ZM36.5562 21.5521C36.7124 21.4919 36.886 21.4919 37.0509 21.5521L43.8552 24.1043L36.7992 26.7768L29.7518 24.1043L36.5562 21.5521ZM45.4088 36.1865L38.6738 39.1168V30.4805L45.8254 27.765V35.5592C45.8254 35.8342 45.6605 36.0748 45.4088 36.1865Z");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Menu");
        backButton.setOnAction(e -> {
            ViewManager.getInstance().switchView(null);
        });

        header.getChildren().addAll(icon, spacer, backButton);
        return header;
    }

    private VBox createListView(){
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Recipe List");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("Add Recipe");
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"); 
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;"));
        addButton.setOnAction(e -> showFormView(null));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"));
        controlBar.getChildren().addAll(titleLabel, spacer, addButton);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Penerapan callback function dengan melakukan binding suatu objek dengan getter pada modal
        TableColumn<Resep, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(
                () -> cellData.getValue().getIdResep()));
        idColumn.setPrefWidth(50);
        
        TableColumn<Resep, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getNamaResep()));
        nameColumn.setPrefWidth(200);
        
        TableColumn<Resep, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getDeskripsi()));
        descColumn.setPrefWidth(400);

        TableColumn<Resep, Void> actionsColumn = new TableColumn<>("Actions");
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
                
                viewBtn.setOnAction(event -> {
                    Resep resep = getTableView().getItems().get(getIndex());
                    showDetailView(resep);
                });
                
                editBtn.setOnAction(event -> {
                    Resep resep = getTableView().getItems().get(getIndex());
                    showFormView(resep);
                });
                
                deleteBtn.setOnAction(event -> {
                    Resep resep = getTableView().getItems().get(getIndex());
                    // deleteResep(resep);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
        
        tableView.getColumns().addAll(idColumn, nameColumn, descColumn, actionsColumn);
        
        // Set up data
        loadData();
        
        container.getChildren().addAll(controlBar, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        return container;
    }

    private VBox createDetailView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        // Holder (wrapper) buat konten resep yang asli 
        return container;
    }

    private VBox createFormView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label formTitle = new Label("Add New Recipe");
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

        Label instructionsLabel = new Label("Instructions:");
        TextArea instructionsArea = new TextArea();
        instructionsArea.setPrefRowCount(6);
        instructionsArea.setWrapText(true);

        // Menaruh UI berdasarkan grid dengan parameter (kolom,row)
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(descLabel, 0, 1);
        form.add(descArea, 1, 1);
        form.add(instructionsLabel, 0, 2);
        form.add(instructionsArea, 1, 2);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> showListView());

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill:white;");
        saveButton.setOnAction(e -> {
            if (nameField.getText().isEmpty()){
                showAlert("Error", "Recipe name is required");
                return;
            }

            Resep resep = (Resep) form.getUserData();
            if (resep == null){
                resep = new Resep();
            }

            resep.setNamaResep(nameField.getText());
            resep.setDeskripsi(descArea.getText());
            resep.setPreskripsi(instructionsArea.getText());

            boolean success = resep.save();

            if (success){
                showListView();
                loadData();
            } else {
                showAlert("Error", "Failed too save recipe!");
            }
        });

        buttonBar.getChildren().addAll(cancelButton, saveButton);
        
        container.getChildren().addAll(formTitle, form, buttonBar);
        return container;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #E43A3A;");

        Label copyright = new Label("\u00a9" + " 2025 Cafe Minamdang. All rights reserved.");
        copyright.setFont(Font.font("Arial", FontWeight.BOLD, 9));
        copyright.setTextFill(Color.WHITE);

        footer.getChildren().add(copyright);
        return footer;
    }

    private void showListView(){
        mainPane.setCenter(listView);
    }

    private void showDetailView(Resep resep){
        detailView.getChildren().clear();

        Label titleLabel = new Label(resep.getNamaResep());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox details = new VBox(15);
        details.setPadding(new Insets(20, 0, 0, 0));

        Label descTitle = new Label("Description");
        descTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label descText = new Label(resep.getDeskripsi());
        descText.setWrapText(true);

        Label instructionsTitle = new Label("Instructions");
        instructionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label instructionsText = new Label(resep.getPreskripsi());
        instructionsText.setWrapText(true);

        details.getChildren().addAll(descTitle, descText, instructionsTitle, instructionsText);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button backButton = new Button("Back to recipes");
        backButton.setOnAction(e -> showListView());

        Button editButton = new Button("Edit Recipe");
        editButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill:white;");
        editButton.setOnAction(e -> showFormView(resep));

        buttonBar.getChildren().addAll(backButton, editButton);

        detailView.getChildren().addAll(titleLabel, details, buttonBar);
        mainPane.setCenter(detailView);
    }

    private void showFormView(Resep resep){
        GridPane form = (GridPane) formView.getChildren().get(1);
        Label formTitle = (Label) formView.getChildren().get(0);

        form.setUserData(resep);

        TextField nameField = (TextField) form.getChildren().get(1);
        TextArea descArea = (TextArea) form.getChildren().get(3);
        TextArea instructionsArea = (TextArea) form.getChildren().get(5);

        if (resep == null){
            formTitle.setText("Add New Recipe");
            nameField.clear();
            descArea.clear();
            instructionsArea.clear();
        } else {
            formTitle.setText("Edit Recipe");
            nameField.setText(resep.getNamaResep());
            descArea.setText(resep.getDeskripsi());
            instructionsArea.setText(resep.getPreskripsi());
        }

        mainPane.setCenter(formView);
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
        alert.setContentText(message);
        alert.showAndWait();
    }
}
