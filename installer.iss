[Setup]
AppName=Desktop Clock
AppVersion=1.0.0
DefaultDirName={commonpf}\Desktop Clock
DefaultGroupName=Desktop Clock
OutputDir=installer
OutputBaseFilename=DesktopClockSetup
Compression=lzma
SolidCompression=yes
UninstallDisplayIcon={app}\icon.png

[Files]
; Main application files
Source: "target\DesktopClock.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "src\main\resources\icon.png"; DestDir: "{app}"; Flags: ignoreversion

; Include all JavaFX and other dependencies
Source: "target\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Desktop Clock"; Filename: "{app}\DesktopClock.exe"; IconFilename: "{app}\icon.png"
Name: "{commondesktop}\Desktop Clock"; Filename: "{app}\DesktopClock.exe"; IconFilename: "{app}\icon.png"

[Run]
Filename: "{app}\DesktopClock.exe"; Description: "Launch Desktop Clock"; Flags: postinstall nowait

[Code]
function InitializeSetup(): Boolean;
begin
  Result := True;
end; 