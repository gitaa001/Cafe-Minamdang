package com.cafeminamdang.View;

import com.cafeminamdang.Controller.BarangController;
import com.cafeminamdang.Model.Barang;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class BarangView implements BaseView {
    private final BarangController controller;
    private final BorderPane mainPane;
    private TableView<Barang> tableView;
    private ObservableList<Barang> dataBarang;

    public BarangView() {
        controller = new BarangController();
        mainPane = new BorderPane();
        mainPane.setPrefSize(1000, 700);

        mainPane.setTop(createHeader());
        mainPane.setCenter(createTableView());
        mainPane.setBottom(createFooter());
    }

    @Override
    public Pane getRoot() {
        return mainPane;
    }

    @Override
    public void refresh() {
        loadData();
    }

    private void loadData() {
        List<Barang> barangs = controller.getAllBarang();
        dataBarang = FXCollections.observableArrayList(barangs);
        tableView.setItems(dataBarang);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Label titleLabel = new Label("CafeMinamdang - Barang");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("Dashboard");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #E43A3A;");
        backButton.setOnAction(e -> ViewManager.getInstance().switchView("owner dashboard"));

        header.getChildren().addAll(titleLabel, spacer, backButton);
        return header;
    }

    private VBox createTableView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Barang List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("Add Barang");
        addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;");
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;"));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"));
        addButton.setOnAction(e -> ViewManager.getInstance().switchView("tambah barang"));

        controlBar.getChildren().addAll(title, spacer, addButton);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Barang, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDBarangProperty());

        TableColumn<Barang, String> namaColumn = new TableColumn<>("Nama Barang");
        namaColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getNamaBarang()));

        TableColumn<Barang, String> jenisColumn = new TableColumn<>("Konsinyasi");
        jenisColumn.setCellValueFactory(cellData -> new javafx.beans.property.ReadOnlyStringWrapper(
                cellData.getValue().getIsKonsinyasi() ? "Ya" : "Tidak"));

        TableColumn<Barang, String> stokColumn = new TableColumn<>("Stok");
        stokColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> String.valueOf(cellData.getValue().getKuantitas())));

        TableColumn<Barang, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                editBtn.setOnAction(e -> {
                    Barang barang = getTableView().getItems().get(getIndex());
                    ViewManager.getInstance().switchView("edit barang");
                });

                deleteBtn.setOnAction(e -> {
                    Barang barang = getTableView().getItems().get(getIndex());
                    boolean success = controller.deleteBarang(barang);
                    if (success) loadData();
                    else showAlert("Error", "Failed to delete barang!");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        tableView.getColumns().addAll(idColumn, namaColumn, jenisColumn, stokColumn, actionColumn);
        loadData();

        container.getChildren().addAll(controlBar, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        return container;
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
