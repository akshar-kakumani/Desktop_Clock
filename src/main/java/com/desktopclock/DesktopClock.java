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
import javafx.scene.image.Image;

public class DesktopClock extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private static final DateTimeFormatter TIME_FORMATTER_24H = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER_12H = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private boolean is24HourFormat = false;
    private Label timeLabel;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Application starting...");
        this.primaryStage = primaryStage;
        
        // Set application icon
        try {
            Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + e.getMessage());
        }
        
        // Create the time label
        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 200px; -fx-text-fill: white;");
        
        // Create the root container
        StackPane root = new StackPane(timeLabel);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
        
        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        
        // Calculate margins (1mm = ~3.78 pixels at 96 DPI)
        double margin = 3.78;
        
        // Create the scene with screen dimensions minus margins
        Scene scene = new Scene(root, 
            screenBounds.getWidth() - (2 * margin), 
            screenBounds.getHeight() - (2 * margin));
        scene.setFill(Color.TRANSPARENT);
        
        // Configure the stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setScene(scene);
        
        // Make window stay at bottom
        Platform.runLater(() -> {
            primaryStage.toBack();
            primaryStage.setIconified(false);
            primaryStage.setAlwaysOnTop(false);
        });
        
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
        
        // Position the window with margins
        primaryStage.setX(margin);
        primaryStage.setY(margin);
        
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
            timeLabel.setText(LocalTime.now().format(is24HourFormat ? TIME_FORMATTER_24H : TIME_FORMATTER_12H));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("Clock started.");
    }

    public void toggleTimeFormat() {
        is24HourFormat = !is24HourFormat;
        timeLabel.setText(LocalTime.now().format(is24HourFormat ? TIME_FORMATTER_24H : TIME_FORMATTER_12H));
    }

    public boolean is24HourFormat() {
        return is24HourFormat;
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