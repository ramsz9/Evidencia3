package com.tareas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarea")
public class TareaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "titulo", columnDefinition = "varchar(120)")
    private String titulo;
    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;
    @Column(name = "estado", columnDefinition = "varchar(15)")
    private String estado;
    @Column(name = "prioridad", columnDefinition = "varchar(10)")
    private String prioridad;
    @Column(name = "integrante_id")
    private Integer integranteId;

}
