// Desktop Clock Application - A lightweight time display tool
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
    public void init() {
        System.out.println("Initializing application...");
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Starting application...");
        this.primaryStage = primaryStage;
        
        try {
            // Set application icon
            try {
                Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
                if (icon.isError()) {
                    System.err.println("Failed to load icon: Image loading error");
                } else {
                    primaryStage.getIcons().add(icon);
                    System.out.println("Icon loaded successfully");
                }
            } catch (Exception e) {
                System.err.println("Failed to load icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Create the time label
            timeLabel = new Label();
            timeLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 200px; -fx-text-fill: white;");
            
            // Create the root container
            StackPane root = new StackPane(timeLabel);
            root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
            
            // Get screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            System.out.println("Screen dimensions: " + screenBounds);
            
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
                try {
                    primaryStage.toBack();
                    primaryStage.setIconified(false);
                    primaryStage.setAlwaysOnTop(false);
                    System.out.println("Window positioned successfully");
                } catch (Exception e) {
                    System.err.println("Error positioning window: " + e.getMessage());
                    e.printStackTrace();
                }
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
            System.out.println("Setting up system tray...");
            SystemTrayManager.setupSystemTray(this);
            
            // Start the clock update
            startClock();
            
            // Position the window with margins
            primaryStage.setX(margin);
            primaryStage.setY(margin);
            
            // Show the stage
            primaryStage.show();
            System.out.println("Application started successfully");
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
    }
    
    private void startClock() {
        try {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                try {
                    timeLabel.setText(LocalTime.now().format(is24HourFormat ? TIME_FORMATTER_24H : TIME_FORMATTER_12H));
                } catch (Exception e) {
                    System.err.println("Error updating clock: " + e.getMessage());
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            System.out.println("Clock started successfully");
        } catch (Exception e) {
            System.err.println("Error starting clock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void toggleTimeFormat() {
        try {
            is24HourFormat = !is24HourFormat;
            timeLabel.setText(LocalTime.now().format(is24HourFormat ? TIME_FORMATTER_24H : TIME_FORMATTER_12H));
            System.out.println("Time format toggled to: " + (is24HourFormat ? "24-hour" : "12-hour"));
        } catch (Exception e) {
            System.err.println("Error toggling time format: " + e.getMessage());
        }
    }

    public boolean is24HourFormat() {
        return is24HourFormat;
    }

    public void setOpacity(double opacity) {
        try {
            if (primaryStage != null) {
                primaryStage.setOpacity(opacity);
                System.out.println("Opacity set to: " + opacity);
            }
        } catch (Exception e) {
            System.err.println("Error setting opacity: " + e.getMessage());
        }
    }

    public void exit() {
        System.out.println("Exiting application...");
        Platform.exit();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Launching application...");
            launch(args);
        } catch (Exception e) {
            System.err.println("Error launching application: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 