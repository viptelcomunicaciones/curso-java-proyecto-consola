package com.ejercicio2;

import java.util.ArrayList;
import java.util.List;

public class EstudianteManager {
    public record Estudiante(String nombre, int edad, double nota) {}

    private static List<Estudiante> estudiantes = new ArrayList<>();

    public static void registrar(String nombre, int edad, double nota) {
        estudiantes.add(new Estudiante(nombre, edad, nota));
    }

    public static List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    public static Estudiante buscar(String nombre) {
        return estudiantes.stream()
                .filter(e -> e.nombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public static double calcularPromedio() {
        if (estudiantes.isEmpty()) return 0.0;
        return estudiantes.stream()
                .mapToDouble(Estudiante::nota)
                .average()
                .orElse(0.0);
    }
}
