module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires commons.math3;

    opens com.example.application to javafx.fxml;
    exports com.example.application;
    exports com.example.application.interfaces;
    opens com.example.application.interfaces to javafx.fxml;
    exports com.example.application.series;
    opens com.example.application.series to javafx.fxml;
}