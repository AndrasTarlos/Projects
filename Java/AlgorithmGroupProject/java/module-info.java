module App {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires jol.core;
    opens App to javafx.graphics;
    exports App;
    exports App.SortClasses;
    opens App.SortClasses to javafx.graphics;
    exports App.Excel;
    opens App.Excel to javafx.graphics;
    exports App.Utils;
    opens App.Utils to javafx.graphics;
}