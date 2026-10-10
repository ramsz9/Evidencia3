package com.tareas.dto;

import com.tareas.model.TareaModel;

public record TareaConsultaDto(
    Integer id, 
    String titulo, 
    String descripcion, 
    String estado, 
    String prioridad, 
    Integer integranteId
) {
    public static TareaConsultaDto from(TareaModel m) {
        return new TareaConsultaDto(
            m.getId(), 
            m.getTitulo(), 
            m.getDescripcion(), 
            m.getEstado(), 
            m.getPrioridad(), 
            m.getIntegranteId()
        );
    }
}