module org.example.pt2024_30421_marinescu_george_assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.pt2024_30421_marinescu_george_assignment_2 to javafx.fxml;
    exports org.example.pt2024_30421_marinescu_george_assignment_2;
    exports controller;
    opens controller to javafx.fxml;
}