package com.cafeminamdang.View;

import com.cafeminamdang.Model.Invoice;
import com.cafeminamdang.Controller.InvoiceController;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
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
import java.util.Date;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class InvoiceView implements BaseView {
    private InvoiceController invoiceController;
    private BorderPane mainPane;
    private VBox listView;
    private VBox detailView;
    private VBox formView;
    private ObservableList<Invoice> dataInvoice;
    private TableView<Invoice> tableView;

    public InvoiceView(){
        invoiceController = new InvoiceController();

        mainPane = new BorderPane();
        mainPane.setPrefSize(1000, 700);

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
        List<Invoice> invoices = invoiceController.getAllInvoices();
        dataInvoice = FXCollections.observableArrayList(invoices);
        if (tableView != null){
            tableView.setItems(dataInvoice);
        }
    }

    private HBox createHeader(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 15, 15, 15));
        header.setStyle("-fx-background-color: #E43A3A;");

        Label titleLabel = new Label("CafeMinamdang");
        titleLabel.setFont(loadFont("Title"));
        titleLabel.setTextFill(Color.WHITE);

        Node icon = createSvgIcon("M25.2387 4.16776L32.0431 6.71998L24.9957 9.4011L17.9483 6.71998L24.7527 4.16776C24.9089 4.10761 25.0825 4.10761 25.2474 4.16776H25.2387ZM11.8035 7.94024V17.582C11.6907 17.6163 11.5779 17.6507 11.465 17.6937L3.13314 20.8216C1.24978 21.5263 0 23.3223 0 25.316V35.5592C0 37.4669 1.13696 39.1942 2.90748 39.9676L11.2394 43.594C12.4892 44.1353 13.9038 44.1353 15.1536 43.594L24.9957 39.3059L34.8464 43.594C36.0962 44.1353 37.5109 44.1353 38.7606 43.594L47.0925 39.9676C48.8544 39.2028 50 37.4669 50 35.5592V25.3245C50 23.3223 48.7502 21.5349 46.8669 20.8216L38.535 17.6937C38.4222 17.6507 38.3093 17.6163 38.1965 17.582V7.94024C38.1965 5.93799 36.9467 4.15058 35.0634 3.43733L26.7315 0.30936C25.6206 -0.10312 24.3968 -0.10312 23.2859 0.30936L14.954 3.43733C13.0533 4.15058 11.8035 5.94658 11.8035 7.94024ZM34.0219 18.089L26.8703 20.7701V13.1048L34.0219 10.3893V18.089ZM13.4352 21.5521L20.2395 24.1043L13.1922 26.7768L6.14477 24.1043L12.9491 21.5521C13.1054 21.4919 13.2789 21.4919 13.4438 21.5521H13.4352ZM15.0668 39.1168V30.4805L22.2184 27.765V36.0061L15.0668 39.1168ZM36.5562 21.5521C36.7124 21.4919 36.886 21.4919 37.0509 21.5521L43.8552 24.1043L36.7992 26.7768L29.7518 24.1043L36.5562 21.5521ZM45.4088 36.1865L38.6738 39.1168V30.4805L45.8254 27.765V35.5592C45.8254 35.8342 45.6605 36.0748 45.4088 36.1865Z");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = createIconButton("M1.5 0C2.32969 0 3 0.670312 3 1.5V17.25C3 17.6625 3.3375 18 3.75 18H22.5C23.3297 18 24 18.6703 24 19.5C24 20.3297 23.3297 21 22.5 21H3.75C1.67812 21 0 19.3219 0 17.25V1.5C0 0.670312 0.670312 0 1.5 0ZM7.5 9C8.32969 9 9 9.67031 9 10.5V13.5C9 14.3297 8.32969 15 7.5 15C6.67031 15 6 14.3297 6 13.5V10.5C6 9.67031 6.67031 9 7.5 9ZM13.5 6V13.5C13.5 14.3297 12.8297 15 12 15C11.1703 15 10.5 14.3297 10.5 13.5V6C10.5 5.17031 11.1703 4.5 12 4.5C12.8297 4.5 13.5 5.17031 13.5 6ZM16.5 7.5C17.3297 7.5 18 8.17031 18 9V13.5C18 14.3297 17.3297 15 16.5 15C15.6703 15 15 14.3297 15 13.5V9C15 8.17031 15.6703 7.5 16.5 7.5ZM22.5 3V13.5C22.5 14.3297 21.8297 15 21 15C20.1703 15 19.5 14.3297 19.5 13.5V3C19.5 2.17031 20.1703 1.5 21 1.5C21.8297 1.5 22.5 2.17031 22.5 3Z", 54, Color.WHITE, "#E43A3A", "#FF6B6B", "Dashboard");
        backButton.setOnAction(e -> {
            ViewManager.getInstance().switchView("owner dashboard");
        });

        header.getChildren().addAll(icon, titleLabel, spacer, backButton);
        return header;
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

    private VBox createListView(){
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Invoice List");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox filterBar = new HBox(10);
        filterBar.setAlignment(Pos.CENTER_RIGHT);
        
        Button sortByDateBtn = new Button("Sort by Date");
        sortByDateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        sortByDateBtn.setOnAction(e -> {
            dataInvoice = FXCollections.observableArrayList(invoiceController.getInvoicesSortedByDate());
            tableView.setItems(dataInvoice);
        });
        
        Button sortByPriceBtn = new Button("Sort by Price");
        sortByPriceBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        sortByPriceBtn.setOnAction(e -> {
            dataInvoice = FXCollections.observableArrayList(invoiceController.getInvoicesSortedByNominal());
            tableView.setItems(dataInvoice);
        });
        
        filterBar.getChildren().addAll(sortByDateBtn, sortByPriceBtn);

        Button addButton = new Button("Add Invoice");
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"); 
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;"));
        addButton.setOnAction(e -> showFormView(null));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"));
        
        controlBar.getChildren().addAll(titleLabel, spacer, filterBar, addButton);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Table columns for invoices
        TableColumn<Invoice, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idInvoiceProperty().asObject());
        idColumn.setPrefWidth(60);
        
        TableColumn<Invoice, String> titleColumn = new TableColumn<>("Invoice Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().judulInvoiceProperty());
        titleColumn.setPrefWidth(150);
        
        TableColumn<Invoice, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        dateColumn.setCellFactory(column -> new TableCell<Invoice, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });
        dateColumn.setPrefWidth(100);

        TableColumn<Invoice, BigDecimal> priceColumn = new TableColumn<>("Unit Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        priceColumn.setCellFactory(column -> new TableCell<Invoice, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Rp " + String.format("%,.2f", item));
                }
            }
        });
        priceColumn.setPrefWidth(120);
        
        TableColumn<Invoice, Integer> qtyColumn = new TableColumn<>("Qty");
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().kuantitasProperty().asObject());
        qtyColumn.setPrefWidth(60);

        TableColumn<Invoice, Integer> warehouseColumn = new TableColumn<>("Warehouse ID");
        warehouseColumn.setCellValueFactory(cellData -> cellData.getValue().idGudangProperty().asObject());
        warehouseColumn.setPrefWidth(100);

        TableColumn<Invoice, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(150);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(5, viewBtn, deleteBtn);
            
            {
                viewBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                
                viewBtn.setOnAction(event -> {
                    Invoice invoice = getTableView().getItems().get(getIndex());
                    showDetailView(invoice);
                });
                
                deleteBtn.setOnAction(event -> {
                    Invoice invoice = getTableView().getItems().get(getIndex());
                    deleteInvoice(invoice);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
        
        tableView.getColumns().addAll(idColumn, titleColumn, dateColumn, priceColumn, qtyColumn, warehouseColumn, actionsColumn);
        
        // Load data
        loadData();
        
        container.getChildren().addAll(controlBar, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        return container;
    }

    private Node createSvgIcon(String svgPath) {
        SVGPath path = new SVGPath();
        path.setContent(svgPath);
        path.setFill(Color.WHITE);

        path.setScaleX(0.5);
        path.setScaleY(0.5);
        
        return path;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #E43A3A;");

        Label copyright = new Label("\u00a9" + " 2025 Cafe Minamdang. All rights reserved.");
        copyright.setFont(loadFont("Thin-Semibold"));
        copyright.setTextFill(Color.WHITE);

        footer.getChildren().add(copyright);
        return footer;
    }

    private VBox createDetailView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        // Will be populated in showDetailView method
        return container;
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

    private VBox createFormView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label formTitle = new Label("Add New Invoice");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 0, 0, 0));

        // Invoice Title
        Label titleLabel = new Label("Invoice Title:");
        TextField titleField = new TextField();
        titleField.setPrefWidth(300);

        // Date picker
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // Quantity
        Label qtyLabel = new Label("Quantity:");
        TextField qtyField = new TextField();
        qtyField.setPromptText("Enter quantity");

        // Price
        Label priceLabel = new Label("Unit Price:");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter unit price");

        // Warehouse
        Label warehouseLabel = new Label("Warehouse ID:");
        TextField warehouseField = new TextField();
        warehouseField.setPromptText("Enter warehouse ID");

        // Add fields to form
        form.add(titleLabel, 0, 0);
        form.add(titleField, 1, 0);
        form.add(dateLabel, 0, 1);
        form.add(datePicker, 1, 1);
        form.add(qtyLabel, 0, 2);
        form.add(qtyField, 1, 2);
        form.add(priceLabel, 0, 3);
        form.add(priceField, 1, 3);
        form.add(warehouseLabel, 0, 4);
        form.add(warehouseField, 1, 4);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> showListView());

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill:white;");
        saveButton.setOnAction(e -> {
            try {
                // Validate inputs
                String title = titleField.getText().trim();
                if (title.isEmpty()) {
                    showAlert("Error", "Please enter an invoice title");
                    return;
                }
                Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                
                int quantity;
                try {
                    quantity = Integer.parseInt(qtyField.getText().trim());
                    if (quantity <= 0) {
                        showAlert("Error", "Quantity must be greater than zero");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid quantity");
                    return;
                }
                
                BigDecimal unitPrice;
                try {
                    unitPrice = new BigDecimal(priceField.getText().trim());
                    if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
                        showAlert("Error", "Unit price must be greater than zero");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid unit price");
                    return;
                }
                
                int warehouseId;
                try {
                    warehouseId = Integer.parseInt(warehouseField.getText().trim());
                    if (warehouseId <= 0) {
                        showAlert("Error", "Warehouse ID must be greater than zero");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid warehouse ID");
                    return;
                }
                
                // Create and save invoice
                Invoice invoice = invoiceController.createInvoice(
                    title, date, warehouseId, unitPrice, quantity
                );
                
                boolean success = invoiceController.saveInvoice(invoice);

                if (success) {
                    showListView();
                    loadData();
                } else {
                    showAlert("Error", "Failed to save invoice");
                }
            } catch (Exception ex) {
                showAlert("Error", "An error occurred: " + ex.getMessage());
            }
        });

        buttonBar.getChildren().addAll(cancelButton, saveButton);
        
        container.getChildren().addAll(formTitle, form, buttonBar);
        return container;
    }

    private void deleteInvoice(Invoice invoice) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete Invoice");
        confirmDialog.setHeaderText("Delete " + invoice.getJudulInvoice() + "?");
        confirmDialog.setContentText("This action cannot be undone.");

        DialogPane dialogPane = confirmDialog.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #F8F8F8;" +
            "-fx-font-family: 'Arial';"
        );

        Label headLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headLabel != null) {
            headLabel.setFont(loadFont("Thin-SemiBold"));
        }

        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setFont(loadFont("Thin-SemiBold"));
        }

        dialogPane.getButtonTypes().stream()
            .map(dialogPane::lookupButton)
            .forEach(button -> {
                button.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;");
                ((Button)button).setFont(loadFont("Thin-SemiBold"));
            });

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            boolean success = invoiceController.deleteInvoice(invoice.getIDInvoice());
            if (success) {
                loadData();
            } else {
                showAlert("Error", "Failed to delete invoice!");
            }
        }
    }

    private void showListView(){
        mainPane.setCenter(listView);
    }

    private void showDetailView(Invoice invoice){
        detailView.getChildren().clear();

        Label titleLabel = new Label(invoice.getJudulInvoice());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Format date for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(invoice.getTanggalInvoice());

        VBox details = new VBox(15);
        details.setPadding(new Insets(20, 0, 0, 0));

        // Invoice info grid
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(10);
        
        Label idLabel = new Label("Invoice ID:");
        idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label idValue = new Label(invoice.getIDInvoice().toString());
        
        Label dateLabel = new Label("Date:");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label dateValue = new Label(formattedDate);
        
        Label qtyLabel = new Label("Quantity:");
        qtyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label qtyValue = new Label(invoice.getKuantitasInvoice().toString());
        
        Label priceLabel = new Label("Unit Price:");
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label priceValue = new Label("Rp " + String.format("%,.2f", invoice.getUnitPrice()));
        
        Label warehouseLabel = new Label("Warehouse ID:");
        warehouseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label warehouseValue = new Label(invoice.getIDGudang().toString());
        
        infoGrid.add(idLabel, 0, 0);
        infoGrid.add(idValue, 1, 0);
        infoGrid.add(dateLabel, 0, 1);
        infoGrid.add(dateValue, 1, 1);
        infoGrid.add(qtyLabel, 0, 2);
        infoGrid.add(qtyValue, 1, 2);
        infoGrid.add(priceLabel, 0, 3);
        infoGrid.add(priceValue, 1, 3);
        infoGrid.add(warehouseLabel, 0, 4);
        infoGrid.add(warehouseValue, 1, 4);

        details.getChildren().addAll(infoGrid);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        Button backButton = new Button("Back to Invoices");
        backButton.setOnAction(e -> showListView());

        Button deleteButton = new Button("Delete Invoice");
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill:white;");
        deleteButton.setOnAction(e -> {
            deleteInvoice(invoice);
            showListView();
        });

        buttonBar.getChildren().addAll(backButton, deleteButton);

        detailView.getChildren().addAll(titleLabel, details, buttonBar);
        mainPane.setCenter(detailView);
    }

    private void showFormView(Invoice invoice) {
        GridPane form = (GridPane) formView.getChildren().get(1);
        Label formTitle = (Label) formView.getChildren().get(0);
        formTitle.setText("Add New Invoice");
        // Clear all form fields
        TextField titleField = (TextField) form.getChildren().get(1);
        DatePicker datePicker = (DatePicker) form.getChildren().get(3);
        TextField qtyField = (TextField) form.getChildren().get(5);
        TextField priceField = (TextField) form.getChildren().get(7);
        TextField warehouseField = (TextField) form.getChildren().get(9);

        titleField.clear();
        datePicker.setValue(null);
        qtyField.clear();
        priceField.clear();
        warehouseField.clear();
    }
}