module com.desktopclock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    
    opens com.desktopclock to javafx.fxml, javafx.graphics;
    exports com.desktopclock;
} 