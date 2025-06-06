package com.cafeminamdang.view;

import com.cafeminamdang.model.Penjualan;
import com.cafeminamdang.controller.PenjualanController;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale.Category;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.AreaChart;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class OwnerDashboard implements BaseView {
    private PenjualanController penjualanController;
    private BorderPane mainPane;
    private VBox centerView;

    public OwnerDashboard(){
        penjualanController = new PenjualanController();

        mainPane = new BorderPane();
        mainPane.setPrefSize(800, 600);

        HBox header = createHeader();
        mainPane.setTop(header);

        Node dashboardView = createDashboardView();
        mainPane.setCenter(dashboardView);

        HBox footer = createFooter();
        mainPane.setBottom(footer);
    }

    @Override
    public Pane getRoot(){
        return mainPane;
    }

    @Override
    public void refresh(){
        createDashboardView();
    }

    private VBox createCenterView(){
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #F5F5F5;"); 
        // … populate or leave blank as placeholder …
        return container;
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

        Button backButton = new Button("Menu");
        backButton.setOnAction(e -> {
            ViewManager.getInstance().switchView(null);
        });

        header.getChildren().addAll(icon,titleLabel, spacer, backButton);
        return header;
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
                    return new Font("System", 10);
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

    private Node createDashboardView(){
        GridPane dashboard = new GridPane();
        dashboard.setPadding(new Insets(20));
        dashboard.setHgap(10);
        dashboard.setVgap(10);

        LineChart<String, Number> salesChart1 = createSalesChart();
        AreaChart<String, Number> salesChart2 = createAreaChart();
        BarChart<String, Number> salesChart3 = createBarChart();
        PieChart salesChart4 = createPieChart();

        dashboard.add(salesChart1, 0, 0);
        dashboard.add(salesChart2, 1, 0);
        dashboard.add(salesChart3, 0, 1);
        dashboard.add(salesChart4, 1, 1);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        dashboard.getColumnConstraints().addAll(column1, column2);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        dashboard.getRowConstraints().addAll(row1, row2);
        
        return dashboard;
    }

    private BarChart<String, Number> createBarChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Sales (Rp)");

        BarChart<String, Number> salesChart = new BarChart<>(xAxis, yAxis);
        salesChart.setTitle("SalesData");

        // Instansiasi titik (series)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        List<Penjualan> penjualans = penjualanController.getAllPenjualan();

        // plotting titik-titik dari data yang ada
        for (Penjualan penjualan : penjualans){
            String date = penjualan.getTanggalPenjualan().toString();
            int totalSales = penjualan.getTotalPenjualan();
            series.getData().add(new XYChart.Data<>(date, totalSales));
        }

        // Memasukkan data ke lineChart
        salesChart.getData().add(series);

        return salesChart;
    }

    private AreaChart<String, Number> createAreaChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Sales (Rp)");

        AreaChart<String, Number> salesChart = new AreaChart<>(xAxis, yAxis);
        salesChart.setTitle("SalesData");

        // Instansiasi titik (series)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        List<Penjualan> penjualans = penjualanController.getAllPenjualan();

        // plotting titik-titik dari data yang ada
        for (Penjualan penjualan : penjualans){
            String date = penjualan.getTanggalPenjualan().toString();
            int totalSales = penjualan.getTotalPenjualan();
            series.getData().add(new XYChart.Data<>(date, totalSales));
        }

        // Memasukkan data ke lineChart
        salesChart.getData().add(series);

        return salesChart;
    }

    private LineChart<String, Number> createSalesChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Sales (Rp)");

        LineChart<String, Number> salesChart = new LineChart<>(xAxis, yAxis);
        salesChart.setTitle("SalesData");

        // Instansiasi titik (series)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        List<Penjualan> penjualans = penjualanController.getAllPenjualan();

        // plotting titik-titik dari data yang ada
        for (Penjualan penjualan : penjualans){
            String date = penjualan.getTanggalPenjualan().toString();
            int totalSales = penjualan.getTotalPenjualan();
            series.getData().add(new XYChart.Data<>(date, totalSales));
        }

        // Memasukkan data ke lineChart
        salesChart.getData().add(series);

        return salesChart;
    }

    private PieChart createPieChart(){
        PieChart salesChart = new PieChart();
        salesChart.setTitle("SalesData");

        // Instansiasi titik (series)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        List<Penjualan> penjualans = penjualanController.getAllPenjualan();

        // plotting titik-titik dari data yang ada
        for (Penjualan penjualan : penjualans){
            String date = penjualan.getTanggalPenjualan().toString();
            int totalSales = penjualan.getTotalPenjualan();
            salesChart.getData().add(new PieChart.Data(date, totalSales));
        }

        return salesChart;
    }
}
