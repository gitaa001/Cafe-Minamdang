package com.cafeminamdang.view;

import com.cafeminamdang.model.Resep;
import com.cafeminamdang.controller.ResepController;

import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
        header.setStyle("-fx-background-color: #FF8055;");

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
}
