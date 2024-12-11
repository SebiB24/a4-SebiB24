module org.example.a4sebib24 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens a4 to javafx.fxml;
    exports a4;

    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafaker;

}
