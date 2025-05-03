module org.example.snakeugeopgave {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.snakeugeopgave to javafx.fxml;
    exports org.example.snakeugeopgave;
}