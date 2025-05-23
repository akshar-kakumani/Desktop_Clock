# Desktop Clock

A lightweight, always-on-top, translucent digital clock for Windows desktop. This application provides a sleek and modern time display that seamlessly integrates with your desktop environment.

## Features

- Digital time display in HH:MM:SS format
- Modern, sleek font styling
- Transparent/translucent background
- Always-on-top behavior
- Undecorated window (no title bar or borders)
- System tray integration
- Adjustable opacity
- Windows startup integration
- Draggable window
- Minimal resource usage

## Requirements

- Java 17 or higher
- Maven 3.6 or higher
- Windows operating system

## Building the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the following Maven command to build the application:

```bash
mvn clean package
```

The executable JAR file will be created in the `target` directory.

## Running the Application

1. Double-click the JAR file in the `target` directory, or
2. Run the following command:

```bash
java -jar target/desktop-clock-1.0-SNAPSHOT.jar
```

## Usage

- The clock will appear in the top-right corner of your screen
- Drag the clock to reposition it
- Right-click the clock to access the system tray menu
- Use the system tray menu to:
  - Adjust opacity (10% to 100%)
  - Toggle Windows startup
  - Exit the application

## Development

The project uses:
- JavaFX for the user interface
- Maven for dependency management and building
- Java AWT for system tray integration

## License

This project is licensed under the MIT License - see the LICENSE file for details. 