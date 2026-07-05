# OpenCode Instructions - registroestudiantes

This is a Java 21 console-based application for student registration.

## Building and Running

1. **Compile:**
   ```bash
   mvn compile
   ```

2. **Execute:**
   ```bash
   java -cp target/classes com.ejercicio2.registrarestudiantes
   ```

## Development Notes

*   **Locale:** The application is explicitly configured with `Locale.US` (`Scanner(System.in).useLocale(Locale.US)`). When entering numeric data (like grades), ensure you use the dot (`.`) as the decimal separator.
*   **Structure:** Single-class application (`registrarestudiantes.java`) in package `com.ejercicio2`.
*   **Dependencies:** Standard Java SE 21. No external libraries required.
