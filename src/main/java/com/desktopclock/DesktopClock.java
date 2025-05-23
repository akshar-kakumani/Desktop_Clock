package com.desktopclock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DesktopClock extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Label timeLabel;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Application starting...");
        this.primaryStage = primaryStage;
        
        // Create the time label
        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 48px; -fx-text-fill: white;");
        
        // Create the root container
        StackPane root = new StackPane(timeLabel);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");
        
        // Create the scene
        Scene scene = new Scene(root, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        
        // Configure the stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        
        // Add mouse event handlers for dragging
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        // Add right-click context menu
        SystemTrayManager.setupSystemTray(this);
        
        // Start the clock update
        startClock();
        
        // Position the window in the top-right corner
        positionWindow();
        
        // Show the stage
        System.out.println("Showing primary stage...");
        primaryStage.show();
        System.out.println("Primary stage shown.");
    }
    
    private void positionWindow() {
        System.out.println("Positioning window...");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMaxX() - 320); // 300 (width) + 20 (margin)
        primaryStage.setY(20); // 20 pixels from top
        System.out.println("Window positioned at: " + primaryStage.getX() + ", " + primaryStage.getY());
    }
    
    private void startClock() {
        System.out.println("Starting clock...");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLabel.setText(LocalTime.now().format(TIME_FORMATTER));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("Clock started.");
    }

    public void setOpacity(double opacity) {
        System.out.println("Setting opacity to: " + opacity);
        if (primaryStage != null) {
            primaryStage.setOpacity(opacity);
        }
    }

    public void exit() {
        Platform.exit();
    }

    public static void main(String[] args) {
        System.out.println("Launching application...");
        launch(args);
    }
} 