# Desktop Clock

A modern, customizable desktop clock application built with JavaFX. This application provides a sleek, always-on-top clock display with various customization options.

## Features

- Always-on-top display
- Customizable appearance:
  - Font size
  - Font color
  - Background color
  - Background opacity
  - Font family
- Draggable window
- System tray integration
- Settings persistence
- Modern, clean interface

## Requirements

- Java 17 or higher
- Windows operating system

## Installation

### Option 1: Using the Installer (Recommended)

1. Download the latest `DesktopClockSetup.exe` from the releases
2. Run the installer
3. Follow the installation wizard
4. Launch the application from the desktop shortcut or start menu

### Option 2: Manual Installation

1. Download the latest release JAR file
2. Ensure you have Java 17 or higher installed
3. Run the application using:
   ```bash
   java -jar DesktopClock.jar
   ```

## Building from Source

### Prerequisites

- JDK 17 or higher
- Maven 3.6 or higher
- Inno Setup (for creating the installer)

### Build Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/akshar-kakumani/Desktop_Clock.git
   cd DesktopClock
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

3. Create the installer (optional):
   ```bash
   iscc installer.iss
   ```

The build process will:
- Compile the source code
- Package all dependencies
- Create an executable JAR file
- Generate a Windows executable
- Create an installer (if Inno Setup is installed)

## Usage

1. Launch the application
2. The clock will appear on your desktop
3. Right-click the clock to access the settings menu
4. Drag the clock to position it anywhere on your screen
5. Use the system tray icon to minimize/maximize the application

## Customization

The clock can be customized through the settings menu:
- Adjust font size (12-72px)
- Change font color
- Modify background color
- Set background opacity (0-100%)
- Choose from various font families

## Development

### Project Structure

```
DesktopClock/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── desktopclock/
│   │   │           ├── DesktopClock.java
│   │   │           ├── ClockController.java
│   │   │           └── Settings.java
│   │   └── resources/
│   │       ├── clock.fxml
│   │       └── icon.png
├── pom.xml
├── installer.iss
└── README.md
```

### Dependencies

- JavaFX 17
- JNA Platform (for system tray support)
- Launch4j (for Windows executable creation)
- Inno Setup (for installer creation)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- JavaFX team for the excellent UI framework
- JNA team for the system tray integration
- All contributors and users of the application 
