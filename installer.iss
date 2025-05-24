[Setup]
AppName=Desktop Clock
AppVersion=1.0.0
DefaultDirName={pf}\Desktop Clock
DefaultGroupName=Desktop Clock
OutputDir=installer
OutputBaseFilename=DesktopClockSetup
Compression=lzma
SolidCompression=yes
UninstallDisplayIcon={app}\icon.png

[Files]
Source: "target\DesktopClock.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "src\main\resources\icon.png"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{group}\Desktop Clock"; Filename: "{app}\DesktopClock.exe"; IconFilename: "{app}\icon.png"
Name: "{commondesktop}\Desktop Clock"; Filename: "{app}\DesktopClock.exe"; IconFilename: "{app}\icon.png"

[Run]
Filename: "{app}\DesktopClock.exe"; Description: "Launch Desktop Clock"; Flags: postinstall nowait 