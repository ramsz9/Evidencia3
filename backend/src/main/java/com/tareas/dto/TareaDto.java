package com.tareas.dto;

import com.tareas.model.TareaModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TareaDto {

    private String titulo;
    private String descripcion;
    private String prioridad;
    private Integer integranteId;

    public TareaModel toModel() {
        return TareaModel.builder()
                .titulo(this.titulo)
                .descripcion(this.descripcion)
                .prioridad(this.prioridad)
                .integranteId(this.integranteId)
                .build();
    }

}
