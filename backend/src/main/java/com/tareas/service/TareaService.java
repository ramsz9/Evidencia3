package com.tareas.service;

import org.springframework.stereotype.Service;

import com.tareas.dto.TareaDto;
import com.tareas.dto.TareaRsDto;
import com.tareas.exception.BusinessException;
import com.tareas.model.TareaModel;
import com.tareas.repository.IntegranteRepository;
import com.tareas.repository.TareaRepository;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final IntegranteRepository integranteRepository;

    public TareaService(TareaRepository tareaRepository, IntegranteRepository integranteRepository) {
        this.tareaRepository = tareaRepository;
        this.integranteRepository = integranteRepository;
    }

    public TareaRsDto create(TareaDto dto) throws BusinessException {
        validateIntegrante(dto.getIntegranteId());
        TareaModel model = dto.toModel();
        model.setEstado("PENDIENTE");
        Integer id = tareaRepository.save(model).getId();
        return new TareaRsDto("Tarea registrada", id);
    }

    public TareaRsDto assign(Integer id, Integer integranteId) throws BusinessException {
        TareaModel model = tareaRepository.findById(id).orElse(null);
        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea no existe")
                    .build();
        }
        validateIntegrante(integranteId);
        model.setIntegranteId(integranteId);
        tareaRepository.save(model);
        return new TareaRsDto("Tarea asignada", id);
    }

    private void validateIntegrante(Integer integranteId) throws BusinessException {
        if (integranteId != null && !integranteRepository.existsById(integranteId)) {
            throw BusinessException.builder()
                    .message("El integrante no existe")
                    .build();
        }
    }

}
