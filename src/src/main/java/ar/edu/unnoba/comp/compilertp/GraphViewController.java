package ar.edu.unnoba.comp.compilertp;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;


public class GraphViewController {

    @FXML
    private StackPane imageView;

    public void initialize() {
        WebView webView = new WebView();
        imageView.getChildren().add(webView);
        String svgPath = GraphViewController.class.getClassLoader().getResource("graph.svg").toExternalForm();
        webView.getEngine().load(svgPath);
    }
}