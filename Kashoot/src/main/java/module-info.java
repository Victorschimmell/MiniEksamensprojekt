module org.openjfx.kashoot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens org.openjfx.kashoot to javafx.fxml;
    exports org.openjfx.kashoot;
}
