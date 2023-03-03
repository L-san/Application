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
    private ResourceBundle resources;

    @FXML
    private URL location;
    final NumberAxis x = new NumberAxis();
    final NumberAxis y = new NumberAxis();

    @FXML
    private LineChart<Number, Number> chart = new LineChart<>(x, y);

    @FXML
    private RadioButton accuracyButton;

    @FXML
    private TextField accuracyField;
    @FXML
    private TextField zField;

    @FXML
    private RadioButton elementsButton;

    @FXML
    private TextField elementsField;

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
    private Text zText;

    @FXML
    private ToggleButton switcherToggleButton;

    private int cnt = 0;

    @FXML
    void initialize() {
        accuracyField.setDisable(true);
        elementsField.setDisable(true);
        ToggleGroup group = new ToggleGroup();
        accuracyButton.setToggleGroup(group);
        elementsButton.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) group.getSelectedToggle();
                if (accuracyButton.getText().equals(button.getText())) {
                    isAccuracy();
                }
                if (elementsButton.getText().equals(button.getText())) {
                    isElements();
                }
            }
        });
        entryButton.setOnAction(actionEvent -> {
            if (isNotInputEmpty()) {
                double R = Double.parseDouble(radiusField.getText().trim());
                double lambda = Double.parseDouble(lengthWaveField.getText().trim());
                double L = Double.parseDouble(lengthField.getText().trim());
                double z = Double.parseDouble(zField.getText().trim());
                double n = Double.parseDouble(nField.getText().trim());
                boolean isR = switcherToggleButton.isSelected();

                if (!elementsField.getText().isEmpty()) {
                    try {
                        int N = Integer.parseInt(elementsField.getText().trim());
                        CalculatorTools calculatorTools = new CalculatorTools(R, lambda, n, L, 10, N, 0);
                        ArrayList<Point> points = calculatorTools.calculate(z, isR);
                        printLineChart(points, z);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!accuracyField.getText().isEmpty()) {
                    double accuracy = Double.parseDouble(accuracyField.getText().trim());
                    CalculatorTools calculatorTools = new CalculatorTools(R, lambda, n, L, 10, 0, accuracy);
                    ArrayList<Point> points = calculatorTools.calculate(z, isR);
                    printLineChart(points, z);
                }
            }
        });
        clearButton.setOnAction(actionEvent -> {
            clearChart();
        });
        switcherToggleButton.setOnAction(actionEvent -> {
            if (switcherToggleButton.isSelected()){
                 zText.setText("r = ");
                 chart.getXAxis().setLabel("z, мкм");
            }else{
                zText.setText("z = ");
                chart.getXAxis().setLabel("r, мкм");
            }
        });
    }

    private boolean isNotInputEmpty() {
        if (!nField.getText().trim().isEmpty() && !radiusField.getText().trim().isEmpty() &&
                !lengthWaveField.getText().trim().isEmpty() && !lengthField.getText().trim().isEmpty()) {
            return !accuracyField.getText().trim().isEmpty() | !elementsField.getText().trim().isEmpty();
        }
        return false;
    }

    private void isElements() {
        accuracyField.setDisable(true);
        accuracyField.clear();
        elementsField.setDisable(false);
    }

    private void isAccuracy() {
        elementsField.setDisable(true);
        elementsField.clear();
        accuracyField.setDisable(false);
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
