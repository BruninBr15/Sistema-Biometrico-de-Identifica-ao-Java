module org.unip.sistemabiometrico {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.unip.sistemabiometrico to javafx.fxml;
    exports org.unip.sistemabiometrico;
}