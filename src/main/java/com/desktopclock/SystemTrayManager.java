package com.desktopclock;

import javafx.application.Platform;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemTrayManager {
    private static TrayIcon trayIcon;
    private static DesktopClock app;

    public static void setupSystemTray(DesktopClock application) {
        app = application;
        
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported");
            return;
        }

        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage(
                SystemTrayManager.class.getResource("/icon.png")
            );
            
            PopupMenu popup = new PopupMenu();
            
            Menu opacityMenu = new Menu("Opacity");
            for (int i = 1; i <= 10; i++) {
                MenuItem item = new MenuItem(i * 10 + "%");
                final double opacity = i * 0.1;
                item.addActionListener(e -> Platform.runLater(() -> app.setOpacity(opacity)));
                opacityMenu.add(item);
            }
            
            MenuItem startupItem = new MenuItem("Start with Windows");
            startupItem.addActionListener(e -> toggleStartup());
            
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(e -> {
                tray.remove(trayIcon);
                Platform.exit();
            });
            
            popup.add(opacityMenu);
            popup.addSeparator();
            popup.add(startupItem);
            popup.addSeparator();
            popup.add(exitItem);
            
            trayIcon = new TrayIcon(image, "Desktop Clock", popup);
            trayIcon.setImageAutoSize(true);
            
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Failed to create system tray icon: " + e.getMessage());
        }
    }
    
    private static void toggleStartup() {
        try {
            String startupFolder = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
            Path shortcutPath = Paths.get(startupFolder, "DesktopClock.lnk");
            
            if (Files.exists(shortcutPath)) {
                Files.delete(shortcutPath);
            } else {
                // Create shortcut using PowerShell
                String jarPath = new File(DesktopClock.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getPath();
                
                String psCommand = String.format(
                    "$WshShell = New-Object -ComObject WScript.Shell; " +
                    "$Shortcut = $WshShell.CreateShortcut('%s'); " +
                    "$Shortcut.TargetPath = 'javaw'; " +
                    "$Shortcut.Arguments = '-jar \"%s\"'; " +
                    "$Shortcut.WorkingDirectory = '%s'; " +
                    "$Shortcut.Save()",
                    shortcutPath.toString(),
                    jarPath,
                    new File(jarPath).getParent()
                );
                
                Runtime.getRuntime().exec(new String[]{"powershell", "-Command", psCommand});
            }
        } catch (Exception e) {
            System.err.println("Failed to toggle startup: " + e.getMessage());
        }
    }
} 