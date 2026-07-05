# OpenCode Instructions - registroestudiantes

This is a Java 21 console-based application for student registration, refactored to use **Lanterna 3.1.1** with a Swing-based TUI (Text User Interface) with real buttons and windows.

## Building and Running

1. **Compile and Execute (single command):**
   ```bash
   mvn clean compile exec:java
   ```

2. **Or step by step:**
   ```bash
   mvn compile
   mvn exec:java
   ```

## Project Structure

*   **Data layer:** `EstudianteManager.java` — uses `record Estudiante`, `ArrayList`, and Streams API.
*   **UI layer:** `registraestudiantes.java` — Lanterna `SwingTerminalFrame` with buttons, dialogs, and input forms.
*   **Package:** `com.ejercicio2`.

## Development Notes

*   **Locale:** Scanner uses `Locale.US` — use dot (`.`) as decimal separator for grades.
*   **Lanterna version:** 3.1.1 (note: `setForceSwingTerminal()` does NOT exist in this version).
*   **Terminal:** Uses `SwingTerminalFrame` directly — avoids Windows terminal auto-detection issues (`stty.exe`).
*   **Compilation / class naming:** Filename must match the public class name exactly (case-sensitive on some systems). Run `Rename-Item` if needed.
*   **Dependencies:** Lanterna 3.1.1 (added to `pom.xml`). Standard Java SE 21.

## Commands

```bash
# Full clean build + run
mvn clean compile exec:java

# Compile only
mvn compile
```