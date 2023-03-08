package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    public ToggleButton changeScheme;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    final NumberAxis x = new NumberAxis();
    final NumberAxis y = new NumberAxis();

    @FXML
    private LineChart<Number, Number> chart = new LineChart<>(x, y);
    @FXML
    private TextField rElementsField;

    @FXML
    private TextField ikField;

    @FXML
    private TextField zElementsField;

    @FXML
    private Button entryButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField lengthField;

    @FXML
    private TextField lengthWaveField;

    @FXML
    private TextField nField;

    @FXML
    private TextField radiusField;

    @FXML
    private Text ikText;

    @FXML
    private ToggleButton switcherToggleButton;

    private int cnt = 0;

    @FXML
    void initialize() {
        entryButton.setOnAction(actionEvent -> {
            if (isNotInputEmpty()) {
                double R = Double.parseDouble(radiusField.getText().trim());
                double lambda = Double.parseDouble(lengthWaveField.getText().trim());
                double L = Double.parseDouble(lengthField.getText().trim());
                int ik = Integer.parseInt(ikField.getText().trim());
                double n = Double.parseDouble(nField.getText().trim());
                boolean isR = switcherToggleButton.isSelected();
                if (!zElementsField.getText().isEmpty()) {
                    try {
                        int zElements = Integer.parseInt(zElementsField.getText().trim());
                        CalculatorTools calculatorTools = new CalculatorTools(R, lambda, n, L, 10, 0, 1e-4);
                        ArrayList<Point> points = calculatorTools.calculate(ik, isR, zElements);
                        printLineChart(points, ik);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!rElementsField.getText().isEmpty()) {
                    int rElements = Integer.parseInt(rElementsField.getText().trim());
                    CalculatorTools calculatorTools = new CalculatorTools(R, lambda, n, L, 10, 0, 1e-4);
                    ArrayList<Point> points = calculatorTools.calculate(ik, isR, rElements);
                    printLineChart(points, ik);
                }
            }
        });
        clearButton.setOnAction(actionEvent -> {
            clearChart();
        });
        switcherToggleButton.setOnAction(actionEvent -> {
            if (switcherToggleButton.isSelected()) {
                ikText.setText("i = ");
            } else {
                ikText.setText("k = ");
            }
        });
        changeScheme.setOnAction(actionEvent -> {
            if (changeScheme.isSelected()) {
                changeScheme.setText("Неявная схема");
            } else {
                changeScheme.setText("Схема Кранка-Николсона");
            }
        });
    }

    private boolean isNotInputEmpty() {
        if (!nField.getText().trim().isEmpty() && !radiusField.getText().trim().isEmpty() &&
                !lengthWaveField.getText().trim().isEmpty() && !lengthField.getText().trim().isEmpty()) {
            return !zElementsField.getText().trim().isEmpty() | !rElementsField.getText().trim().isEmpty();
        }
        return false;
    }

    private void printLineChart(ArrayList<Point> points, double z) {
        XYChart.Series<Number, Number> series = new XYChart.Series();
        series.setName("z = " + z);
        points.forEach((point) -> {
            double x = point.getX() * 1e6;
            double y = point.getY() * 1;
            series.getData().add(new XYChart.Data(x, y));
        });
        chart.setStyle("-fx-font-size: " + 14 + "px;");
        chart.setStyle("");
        chart.getData().add(series);
        chart.setCreateSymbols(false);
    }

    private void clearChart() {
        chart.getData().clear();
    }
}
