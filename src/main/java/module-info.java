module org.example.snakeugeopgave {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.snakeugeopgave to javafx.fxml;
    exports org.example.snakeugeopgave;
}