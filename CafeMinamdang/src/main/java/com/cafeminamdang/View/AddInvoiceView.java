package com.cafeminamdang.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Main class for launching AddInvoiceView as a standalone JavaFX window,
 * styled to match InvoiceView.
 */
public class AddInvoiceView extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(700, 550);

        // Header
        HBox header = createHeader(primaryStage);
        mainPane.setTop(header);

        // Form
        VBox formView = createFormView(primaryStage);
        mainPane.setCenter(formView);

        // Footer
        HBox footer = createFooter();
        mainPane.setBottom(footer);

        Scene scene = new Scene(mainPane);
        primaryStage.setTitle("Add Invoice - Cafe Minamdang");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Node createSvgIcon(String svgPath) {
        SVGPath path = new SVGPath();
        path.setContent(svgPath);
        path.setFill(Color.WHITE);
        path.setScaleX(0.5);
        path.setScaleY(0.5);
        return path;
    }

    private HBox createHeader(Stage stage) {
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

        Button closeButton = new Button("Close");
        closeButton.setFont(loadFont("Thin-SemiBold"));
        closeButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #E43A3A; -fx-text-fill: white;"));
        closeButton.setOnAction(e -> stage.close());

        header.getChildren().addAll(icon, titleLabel, spacer, closeButton);
        return header;
    }

    private VBox createFormView(Stage stage) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 40, 30, 40));
        container.setAlignment(Pos.CENTER);

        Label formTitle = new Label("Add New Invoice");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label("Invoice Title:");
        TextField titleField = new TextField();
        titleField.setPrefWidth(250);

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Label qtyLabel = new Label("Quantity:");
        TextField qtyField = new TextField();
        qtyField.setPromptText("Enter quantity");

        Label priceLabel = new Label("Unit Price:");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter unit price");

        Label warehouseLabel = new Label("Warehouse ID:");
        TextField warehouseField = new TextField();
        warehouseField.setPromptText("Enter warehouse ID");

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

        HBox buttonBar = new HBox(14);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill:white;");
        saveButton.setOnAction(e -> {
            try {
                String title = titleField.getText().trim();
                if (title.isEmpty()) {
                    showAlert("Validation Error", "Title must not be empty.");
                    return;
                }
                Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                int quantity;
                try {
                    quantity = Integer.parseInt(qtyField.getText().trim());
                    if (quantity <= 0) {
                        showAlert("Validation Error", "Quantity must be greater than 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Validation Error", "Invalid quantity.");
                    return;
                }

                BigDecimal unitPrice;
                try {
                    unitPrice = new BigDecimal(priceField.getText().trim());
                    if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
                        showAlert("Validation Error", "Unit price must be greater than zero.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Validation Error", "Invalid unit price.");
                    return;
                }

                int warehouseId;
                try {
                    warehouseId = Integer.parseInt(warehouseField.getText().trim());
                    if (warehouseId <= 0) {
                        showAlert("Validation Error", "Warehouse ID must be greater than zero.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Validation Error", "Invalid warehouse ID.");
                    return;
                }

                // Save the invoice (call your controller here)
                com.cafeminamdang.Controller.InvoiceController invoiceController = new com.cafeminamdang.Controller.InvoiceController();
                com.cafeminamdang.Model.Invoice invoice = invoiceController.createInvoice(title, date, warehouseId, unitPrice, quantity);
                boolean success = invoiceController.saveInvoice(invoice);

                if (success) {
                    showAlert("Success", "Invoice saved successfully!");
                    stage.close();
                } else {
                    showAlert("Error", "Failed to save invoice.");
                }
            } catch (Exception ex) {
                showAlert("Error", "An error occurred: " + ex.getMessage());
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
        Label copyright = new Label("\u00a9 2025 Cafe Minamdang. All rights reserved.");
        copyright.setFont(loadFont("Thin-Semibold"));
        copyright.setTextFill(Color.WHITE);
        footer.getChildren().add(copyright);
        return footer;
    }

    private Font loadFont(String Type) {
        String basePath = "/fonts/";
        try {
            switch (Type) {
                case "Title":
                    Font poppins = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 24);
                    return poppins != null ? poppins : Font.getDefault();
                case "Thin-SemiBold":
                    Font poppinsSemi = Font.loadFont(getClass().getResource(basePath + "Poppins-SemiBold.ttf").toExternalForm(), 10);
                    return poppinsSemi != null ? poppinsSemi : Font.getDefault();
                default:
                    Font poppinsReg = Font.loadFont(getClass().getResource(basePath + "Poppins-Regular.ttf").toExternalForm(), 10);
                    return poppinsReg != null ? poppinsReg : Font.getDefault();
            }
        } catch (Exception e) {
            return new Font("System", Type.equals("Title") ? 24 : 10);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}