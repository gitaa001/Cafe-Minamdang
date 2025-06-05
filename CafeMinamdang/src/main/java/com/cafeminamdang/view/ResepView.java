package com.cafeminamdang.view;

import com.cafeminamdang.model.Resep;
import com.cafeminamdang.controller.ResepController;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

public class ResepView implements BaseView {
    private BorderPane mainPane;
    private VBox listView;
    private VBox detailView;
    private VBox formView;
    private ObservableList<Resep> dataResep;
    private TableView<Resep> tableView;

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

    private void loadData(){
        List<Resep> reseps = Resep.getAllResep();
        dataResep = FXCollections.observableArrayList(reseps);
        if (tableView != null){
            tableView.setItems(dataResep);
        }
    }

    private HBox createHeader(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 15, 15, 15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Label title = new Label("Cafe Minamdang - Resep Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Menu");
        backButton.setOnAction(e -> {
            ViewManager.getInstance().switchView(null);
        });

        header.getChildren().addAll(title, spacer, backButton);
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

        Button addButton = new Button("New Recipe");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); 
        addButton.setOnAction(e -> showFormView(null));
        controlBar.getChildren().addAll(titleLabel, spacer, addButton);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

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

        Label copyright = new Label("© 2025 Cafe Minamdang. All rights reserved.");
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

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
