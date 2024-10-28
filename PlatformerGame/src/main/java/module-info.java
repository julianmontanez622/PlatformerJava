module com.example.platformergame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.platformergame to javafx.fxml;
    exports com.example.platformergame;
}