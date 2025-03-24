module ar.edu.unnoba.comp.compliadoresjflextp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jflex;

    opens ar.edu.unnoba.comp.jflextp to javafx.fxml;
    exports ar.edu.unnoba.comp.jflextp;
}