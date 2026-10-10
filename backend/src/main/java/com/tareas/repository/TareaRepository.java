package com.tareas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tareas.model.TareaModel;

@Repository
public interface TareaRepository extends JpaRepository<TareaModel, Integer> {
    List<TareaModel> findByEstado(String estado);
    List<TareaModel> findByIntegranteId(Integer integranteId);
    List<TareaModel> findByEstadoAndIntegranteId(String estado, Integer integranteId);
}
