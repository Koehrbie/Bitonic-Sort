@echo off
REM Kompiliert das Projekt von der Projektwurzel aus.
pushd "%~dp0\source"
javac -sourcepath .. Main.java
popd
if %errorlevel% neq 0 (
    echo.
    echo Kompilierung fehlgeschlagen.
    exit /b %errorlevel%
)
echo Kompilierung erfolgreich.
