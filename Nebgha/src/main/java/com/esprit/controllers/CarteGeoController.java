package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CarteGeoController {

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    void initialize() {
        // Initialize the WebEngine
        webEngine = webView.getEngine();

        // Load an initial URL (e.g., Google homepage)
        loadURL("https://www.google.com");
    }

    private void loadURL(String url) {
        // Load the specified URL into the WebView
        webEngine.load(url);
    }
}
