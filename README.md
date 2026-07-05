# Registro de Estudiantes

Aplicación Java de consola profesional con interfaz gráfica de terminal (TUI) para gestionar el registro de estudiantes, desarrollada como proyecto académico del módulo Java Junior Developer.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Tecnologías](#tecnologías)
- [Instalación y Configuración](#instalación-y-configuración)
- [Comandos](#comandos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Funcionalidades](#funcionalidades)
- [Guía de Desarrollo](#guía-de-desarrollo)
- [Licencia](#licencia)

---

## Descripción

Este proyecto comenzó como una aplicación de consola básica con `Scanner` y `System.out` para gestionar estudiantes (registrar, listar, buscar, calcular promedio). Fue migrada a una **Interfaz de Usuario de Terminal (TUI)** profesional utilizando la biblioteca **Lanterna 3.1.1**, que permite crear ventanas, botones, formularios y cuadros de diálogo dentro de la terminal.

### Evolución del Proyecto

| Versión | Descripción |
|---------|-------------|
| v1.0.0 | Aplicación de consola básica (Scanner + arrays estáticos) |
| v2.0.0 | Migración a TUI con Lanterna, botones y formularios |
| v2.0.1 | Refactorización con records, Streams y separación de capas |

---

## Requisitos

- **Java 21** (JDK 21 o superior)
- **Apache Maven 3.9+** (para compilar y ejecutar)
- Sistema operativo: Windows / Linux / macOS (con entorno gráfico para la ventana Swing)

---

## Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java SE | 21 | Lenguaje base |
| Apache Maven | 3.9+ | Gestión de dependencias y build |
| Lanterna | 3.1.1 | Biblioteca TUI para interfaz gráfica en terminal |
| Swing (AWT) | — | Ventana de terminal emulada por Lanterna |

---

## Instalación y Configuración

### 1. Verificar requisitos

```bash
java -version
mvn -version
```

### 2. Clonar o ubicar el proyecto

```bash
cd "ruta/del/proyecto/registroestudiantes"
```

### 3. Compilar y ejecutar

```bash
mvn clean compile exec:java
```

---

## Comandos

| Comando | Descripción |
|---------|-------------|
| `mvn clean` | Limpia la carpeta `target/` (archivos compilados previos) |
| `mvn compile` | Compila el código fuente |
| `mvn exec:java` | Ejecuta la aplicación |
| `mvn clean compile exec:java` | Limpia, compila y ejecuta en un solo paso |

---

## Estructura del Proyecto

```
registroestudiantes/
├── pom.xml                          # Configuración Maven (dependencias, plugins)
├── AGENTS.md                        # Instrucciones para sesiones OpenCode
├── README.md                        # Esta documentación
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ejercicio2/
│   │   │           ├── registraestudiantes.java    # Clase principal (UI con Lanterna)
│   │   │           ├── registraestudiantesOld.java # Versión original de consola
│   │   │           └── EstudianteManager.java      # Lógica de negocio y datos
│   │   └── resources/                              # Recursos (actualmente vacío)
│   └── test/
│       └── java/                                   # Tests (pendiente)
└── target/                                         # Archivos compilados (generado)
```

### Descripción de Archivos Clave

| Archivo | Rol |
|---------|-----|
| `registraestudiantes.java` | Interfaz de usuario con Lanterna (`SwingTerminalFrame`, botones, ventanas) |
| `EstudianteManager.java` | Capa de negocio: `record Estudiante`, `ArrayList`, Streams API |
| `registraestudiantesOld.java` | Versión original preservada como referencia académica |
| `pom.xml` | Dependencias (Lanterna 3.1.1) y plugin exec para ejecución directa |

---

## Funcionalidades

La aplicación ofrece un menú principal con 5 opciones:

### 1. Registrar Estudiante
Ventana con formulario de 3 campos:
- **Nombre** — campo de texto ancho (25 caracteres)
- **Edad** — campo numérico angosto (6 caracteres)
- **Nota** — campo numérico angosto (6 caracteres)

Botones: **Guardar** (registra y cierra) / **Cancelar** (cierra sin guardar)

### 2. Mostrar Estudiantes
Cuadro de diálogo que lista todos los estudiantes registrados en formato:
```
Nombre | Edad: XX | Nota: X.XX
```

### 3. Buscar Estudiante
Ventana con campo de texto para ingresar nombre. Al buscar, muestra los datos del estudiante si existe, o un mensaje "No encontrado".

### 4. Calcular Promedio
Cuadro de diálogo que muestra el promedio de notas de todos los estudiantes registrados.

### 5. Salir
Cierra la ventana y finaliza la aplicación.

---

## Guía de Desarrollo

### Cómo se realizó la migración de consola a TUI

La migración de `registraestudiantesOld.java` (consola básica) a `registraestudiantes.java` (TUI profesional) siguió estos pasos:

#### Paso 1: Separación de capas

Se creó `EstudianteManager.java` para aislar la lógica de negocio:

```java
// Antes (consola): datos y UI mezclados
static String[] nombres = new String[100];
static Scanner entrada = new Scanner(System.in);

// Después (TUI): capa separada con record inmutable
public record Estudiante(String nombre, int edad, double nota) {}
private static List<Estudiante> estudiantes = new ArrayList<>();
```

#### Paso 2: Añadir dependencia Lanterna

En `pom.xml`:

```xml
<dependency>
    <groupId>com.googlecode.lanterna</groupId>
    <artifactId>lanterna</artifactId>
    <version>3.1.1</version>
</dependency>
```

#### Paso 3: Crear la ventana principal

```java
SwingTerminalFrame terminal = new SwingTerminalFrame(
    "Título", new TerminalSize(100, 50),
    TerminalEmulatorDeviceConfiguration.getDefault(),
    SwingTerminalFontConfiguration.getDefault(),
    TerminalEmulatorColorConfiguration.getDefault(),
    TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
```

#### Paso 4: Migrar cada función

Cada opción del menú (1-5) se convirtió en un `Button` de Lanterna con su `ActionListener`:

| Opción original | Implementación TUI |
|----------------|-------------------|
| `Scanner.nextLine()` → nombre | `TextBox()` en `GridLayout(2)` |
| `System.out.println()` | `MessageDialog.showMessageDialog()` |
| `switch-case` del menú | `Button` con lambda `() -> {}` |

### Cómo añadir una nueva funcionalidad

1. **Agregar método en `EstudianteManager`** (ej. `contarEstudiantes()`)
2. **Añadir botón en `registraestudiantes.java`**:
   ```java
   panel.addComponent(new Button("Contar Estudiantes", () -> {
       int total = EstudianteManager.getEstudiantes().size();
       MessageDialog.showMessageDialog(gui, "Total", "Hay " + total + " estudiantes.");
   }));
   ```
3. **Compilar y probar**: `mvn clean compile exec:java`

### Consideraciones para Windows

- Lanterna 3.1.1 **no tiene** `setForceSwingTerminal()`.
- Para Windows, usar `SwingTerminalFrame` directamente evita errores con `stty.exe`.
- Si usas `DefaultTerminalFactory`, puede fallar al intentar detectar terminales Unix.
- Compilar con `mvn compile` y ejecutar con `mvn exec:java` (no `java` directo, porque necesita el classpath de Lanterna).

### API de Lanterna utilizada

| Clase | Uso |
|-------|-----|
| `SwingTerminalFrame` | Ventana principal de la aplicación |
| `TerminalScreen` | Pantalla donde se renderiza la TUI |
| `MultiWindowTextGUI` | Gestor de ventanas (permite abrir múltiples ventanas) |
| `BasicWindow` | Ventana hija (formularios, búsquedas) |
| `Panel` | Contenedor de componentes |
| `Button` | Botón con acción al hacer clic |
| `TextBox` | Campo de texto para entrada de datos |
| `Label` | Etiqueta de texto (estática) |
| `EmptySpace` | Espaciador vertical/horizontal |
| `MessageDialog` | Cuadro de diálogo emergente |
| `LinearLayout` | Layout vertical u horizontal |
| `GridLayout` | Layout en cuadrícula (para formularios) |

---

## Licencia

Proyecto académico — uso educativo.
