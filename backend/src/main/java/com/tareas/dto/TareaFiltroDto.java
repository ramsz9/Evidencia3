package com.tareas.dto;

import com.tareas.model.IntegranteModel;
import com.tareas.model.TareaModel;

public record TareaFiltroDto(
    Integer id,
    String titulo,
    String descripcion,
    String estado,
    String prioridad,
    Integer integranteId,
    String responsableNombre
) {
    public static TareaFiltroDto from(TareaModel tarea, IntegranteModel integrante) {
        String nombreCompleto = "Sin asignar";
        if (integrante != null) {
            String nombre = integrante.getNombre() != null ? integrante.getNombre() : "";
            String apellido = integrante.getApellido() != null ? integrante.getApellido() : "";
            nombreCompleto = (nombre + " " + apellido).trim();
            if (nombreCompleto.isEmpty()) {
                nombreCompleto = "Sin nombre";
            }
        }
        return new TareaFiltroDto(
            tarea.getId(),
            tarea.getTitulo(),
            tarea.getDescripcion(),
            tarea.getEstado(),
            tarea.getPrioridad(),
            tarea.getIntegranteId(),
            nombreCompleto
        );
    }
}
