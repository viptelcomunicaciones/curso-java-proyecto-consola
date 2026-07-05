package com.ejercicio2;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class registrarestudiantesOld {
    static String[] nombres = new String[100];
    static int[] edades = new int[100];
    static double[] notas = new double[100];

    static int contador = 0;

    static Scanner entrada = new Scanner(System.in).useLocale(Locale.US);

    public static void MostraMmenu() {
        System.out.println("\n *** Menu Principal ***");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Mostrar estudiantes registrados");
        System.out.println("3. Buscar estudiante por nombre");
        System.out.println("4. Promedio de notas");
        System.out.println("5. Salir del sistema");
    }

    public static void RegistrarEstudiante() {
        if (contador < nombres.length) {
            System.out.println("Ingresar nombre del estudiante");
            nombres[contador] = entrada.nextLine();
            System.out.println("Ingresar edad");
            edades[contador] = entrada.nextInt();
            System.out.println("Ingrese la nota");
            notas[contador] = entrada.nextDouble();
            contador++;
            entrada.nextLine();
            System.out.println(" Estudiante registrado correctamente.");
        } else {
            System.out.println(" Límite máximo de estudiantes alcanzado.");
        }
    }

    public static void MostrarEstudiante() {

        if (contador == 0) {
            System.out.println(" No hay estudiantes registrados.");
        }
        for (int i = 0; i < contador; i++) {
            System.out.printf("  %d. %s | Edad: %d | Nota: %.2f%n",
                    i + 1, nombres[i], edades[i], notas[i]);
        }
    }

    public static void BuscarEstudiante() {
        if (contador == 0) {
            System.out.println(" No hay estudiantes registrados.");
        }
        if (contador != 0) {
            System.out.println("\n Ingrese el nombre a buscar: ");
            String EstudianteBuscar = entrada.nextLine();
            boolean encontrado = false;
            for (int i = 0; i < contador; i++) {
                if (nombres[i].equalsIgnoreCase(EstudianteBuscar)) {
                    System.out.println("\n Registro encontrado:");
                    System.out.printf("  Nombre: %s | Edad: %d | Nota: %.2f%n",
                            nombres[i], edades[i], notas[i]);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println(" No se encontró ningún estudiante con ese nombre.");
            }
        }

    }

    public static void CalcularPromedio() {
        if (contador == 0) {
            System.out.println(" No hay estudiantes registrados para calcular el promedio.");
        }

        double suma = 0;

        for (int i = 0; i < contador; i++) {
            suma += notas[i];
        }
        double promedio = suma / contador;
        System.out.println("\n  Promedio : " + promedio);
    }

    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(" No se pudo limpiar la consola.");
        }
    }

    public static void salir() {
        limpiarConsola();
        System.out.println("\n Saliendo del sistema... Gracias por usar el sistema.");
    }

    public static void main(String[] args) {
        int opcion;
        do {
            MostraMmenu();
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1 -> RegistrarEstudiante();
                case 2 -> MostrarEstudiante();
                case 3 -> BuscarEstudiante();
                case 4 -> CalcularPromedio();
                case 5 -> salir();
                default -> {
                    limpiarConsola();
                    System.out.println(" Opción inválida. Intente nuevamente.");
                }
            }
            if (opcion != 5) {
                System.out.println("\nPresione ENTER para volver al menú principal...");
                entrada.nextLine();
                limpiarConsola();
            }
        } while (opcion != 5);
    }
}