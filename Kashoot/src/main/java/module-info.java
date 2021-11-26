module org.openjfx.kashoot {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.kashoot to javafx.fxml;
    exports org.openjfx.kashoot;
}
