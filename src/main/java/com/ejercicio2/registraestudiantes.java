package com.ejercicio2;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorDeviceConfiguration;

public class registraestudiantes {
    public static void main(String[] args) throws IOException {
        // Crear terminal con ventana Swing
        SwingTerminalFrame terminal = new SwingTerminalFrame(
                "Sistema de Registro de Estudiantes",
                new TerminalSize(100, 50),
                TerminalEmulatorDeviceConfiguration.getDefault(),
                SwingTerminalFontConfiguration.getDefault(),
                TerminalEmulatorColorConfiguration.getDefault(),
                TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
        terminal.setVisible(true);

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        Window window = new BasicWindow("");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Título centrado
        panel.addComponent(new Label("  SISTEMA DE REGISTRO DE ESTUDIANTES  "));
        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(new Label("  ------------------------------------  "));
        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        panel.addComponent(new Button("Registrar Estudiante", () -> {
            Window registroWindow = new BasicWindow("Registrar Estudiante");
            Panel p = new Panel(new GridLayout(2));
            TextBox nameBox = new TextBox().setPreferredSize(new TerminalSize(25, 1));
            TextBox ageBox = new TextBox().setPreferredSize(new TerminalSize(25, 1));
            TextBox gradeBox = new TextBox().setPreferredSize(new TerminalSize(25, 1));

            p.addComponent(new Label("Nombre:"));
            p.addComponent(nameBox);
            p.addComponent(new Label("Edad:"));
            p.addComponent(ageBox);
            p.addComponent(new Label("Nota:"));
            p.addComponent(gradeBox);

            p.addComponent(new Button("Guardar", () -> {
                try {
                    EstudianteManager.registrar(nameBox.getText(), Integer.parseInt(ageBox.getText()),
                            Double.parseDouble(gradeBox.getText()));
                    registroWindow.close();
                } catch (Exception e) {
                    MessageDialog.showMessageDialog(gui, "Error", "Datos inválidos");
                }
            }));
            p.addComponent(new Button("Cancelar", registroWindow::close));
            registroWindow.setComponent(p);
            gui.addWindow(registroWindow);
        }));

        panel.addComponent(new Button("Mostrar Estudiantes", () -> {
            StringBuilder sb = new StringBuilder();
            for (EstudianteManager.Estudiante e : EstudianteManager.getEstudiantes()) {
                sb.append(e.nombre()).append(" | Edad: ").append(e.edad()).append(" | Nota: ").append(e.nota())
                        .append("\n");
            }
            MessageDialog.showMessageDialog(gui, "Estudiantes", sb.length() == 0 ? "No hay registros" : sb.toString());
        }));

        panel.addComponent(new Button("Buscar Estudiante", () -> {
            Window buscarWindow = new BasicWindow("Buscar Estudiante");
            Panel p = new Panel(new GridLayout(2));
            TextBox nameBox = new TextBox().setPreferredSize(new TerminalSize(25, 1));
            p.addComponent(new Label("Nombre:"));
            p.addComponent(nameBox);
            p.addComponent(new Button("Buscar", () -> {
                EstudianteManager.Estudiante e = EstudianteManager.buscar(nameBox.getText());
                if (e != null) {
                    String info = String.format("Nombre: %s | Edad: %d | Nota: %.2f", e.nombre(), e.edad(), e.nota());
                    MessageDialog.showMessageDialog(gui, "Estudiante Encontrado", info);
                } else {
                    MessageDialog.showMessageDialog(gui, "Resultado",
                            "No se encontró ningún estudiante con ese nombre.");
                }
            }));
            p.addComponent(new Button("Cancelar", buscarWindow::close));
            buscarWindow.setComponent(p);
            gui.addWindow(buscarWindow);
        }));

        panel.addComponent(new Button("Calcular Promedio", () -> {
            double promedio = EstudianteManager.calcularPromedio();
            String msg = EstudianteManager.getEstudiantes().isEmpty()
                    ? "No hay estudiantes registrados para calcular el promedio."
                    : String.format("El promedio de notas es: %.2f", promedio);
            MessageDialog.showMessageDialog(gui, "Promedio de Notas", msg);
        }));

        panel.addComponent(new Button("Salir", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);

        screen.stopScreen();
    }
}