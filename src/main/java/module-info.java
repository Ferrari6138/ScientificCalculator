module com.example.calculadoracientifica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.scripting;


    opens com.example.calculadoracientifica to javafx.fxml;
    exports com.example.calculadoracientifica;
    exports com.example.calculadoracientifica.controller;
    opens com.example.calculadoracientifica.controller to javafx.fxml;
}