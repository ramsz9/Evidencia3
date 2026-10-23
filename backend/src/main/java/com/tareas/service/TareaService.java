package com.tareas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tareas.dto.TareaConsultaDto;
import com.tareas.dto.TareaDto;
import com.tareas.dto.TareaFiltroDto;
import com.tareas.dto.TareaRsDto;
import com.tareas.exception.BusinessException;
import com.tareas.model.IntegranteModel;
import com.tareas.model.TareaModel;
import com.tareas.repository.IntegranteRepository;
import com.tareas.repository.TareaRepository;

import com.tareas.util.ValidationUtil;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final IntegranteRepository integranteRepository;
    private final ValidationUtil validationUtil;

    private static final List<String> ESTADOS_VALIDOS = ValidationUtil.ESTADOS_VALIDOS;

    public TareaService(TareaRepository tareaRepository, IntegranteRepository integranteRepository, ValidationUtil validationUtil) {
        this.tareaRepository = tareaRepository;
        this.integranteRepository = integranteRepository;
        this.validationUtil = validationUtil;
    }

    public TareaRsDto create(TareaDto dto) throws BusinessException {
        validateIntegrante(dto.getIntegranteId());
        TareaModel model = dto.toModel();
        model.setEstado("PENDIENTE");
        Integer id = tareaRepository.save(model).getId();
        return new TareaRsDto("Tarea registrada", id);
    }

    public TareaRsDto update(Integer id, TareaDto dto) throws BusinessException {
        TareaModel model = tareaRepository.findById(id).orElse(null);
        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        validateIntegrante(dto.getIntegranteId());
        if (dto.getEstado() != null) {
            String estado = dto.getEstado().toUpperCase();
            if (!ESTADOS_VALIDOS.contains(estado)) {
                throw BusinessException.builder()
                        .message("El estado proporcionado no es válido. Use: PENDIENTE, EN_PROCESO o TERMINADA")
                        .build();
            }
            model.setEstado(estado);
        }

        model.setTitulo(dto.getTitulo());
        model.setDescripcion(dto.getDescripcion());
        model.setPrioridad(dto.getPrioridad());
        model.setIntegranteId(dto.getIntegranteId());
        tareaRepository.save(model);

        return new TareaRsDto("Tarea modificada", id);
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

    public List<TareaConsultaDto> findAll(String estado, Integer integranteId) throws BusinessException {
        String filtroEstado = (estado != null) ? estado.toUpperCase() : null;

        if (filtroEstado != null && !ESTADOS_VALIDOS.contains(filtroEstado)) {
            throw BusinessException.builder()
                    .message("El estado proporcionado no es válido. Use: PENDIENTE, EN_PROCESO o TERMINADA")
                    .build();
        }

        if (integranteId != null) {
            validateIntegrante(integranteId);
        }

        List<TareaModel> tareas;

        if (filtroEstado != null && integranteId != null) {
            tareas = tareaRepository.findByEstadoAndIntegranteId(filtroEstado, integranteId);
        } else if (filtroEstado != null) {
            tareas = tareaRepository.findByEstado(filtroEstado);
        } else if (integranteId != null) {
            tareas = tareaRepository.findByIntegranteId(integranteId);
        } else {
            tareas = tareaRepository.findAll();
        }

        return tareas.stream()
                .map(TareaConsultaDto::from)
                .toList();
    }

    public TareaConsultaDto findById(Integer id) throws BusinessException {
        TareaModel model = tareaRepository.findById(id).orElse(null);

        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        return TareaConsultaDto.from(model);
    }

    public TareaRsDto delete(Integer id) throws BusinessException {
        if (!tareaRepository.existsById(id)) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        tareaRepository.deleteById(id);
        return new TareaRsDto("Tarea eliminada", id);
    }

    public TareaRsDto updateEstado(Integer id, String estado) throws BusinessException {
        validationUtil.validateEstado(estado);
        TareaModel model = tareaRepository.findById(id).orElse(null);
        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        model.setEstado(estado.trim().toUpperCase());
        tareaRepository.save(model);

        return new TareaRsDto("Estado actualizado", id);
    }

    public TareaRsDto updatePrioridad(Integer id, String prioridad) throws BusinessException {
        validationUtil.validatePrioridad(prioridad);
        TareaModel model = tareaRepository.findById(id).orElse(null);
        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        model.setPrioridad(prioridad.trim().toUpperCase());
        tareaRepository.save(model);

        return new TareaRsDto("Prioridad actualizada", id);
    }

    public TareaRsDto updateEstadoYPrioridad(Integer id, String estado, String prioridad) throws BusinessException {
        validationUtil.validateEstado(estado);
        validationUtil.validatePrioridad(prioridad);
        TareaModel model = tareaRepository.findById(id).orElse(null);
        if (model == null) {
            throw BusinessException.builder()
                    .message("La tarea con ID " + id + " no existe")
                    .build();
        }

        model.setEstado(estado.trim().toUpperCase());
        model.setPrioridad(prioridad.trim().toUpperCase());
        tareaRepository.save(model);

        return new TareaRsDto("Estado y prioridad actualizados", id);
    }

    public List<String> getEstados() {
        return ValidationUtil.ESTADOS_VALIDOS;
    }

    public List<String> getPrioridades() {
        return ValidationUtil.PRIORIDADES_VALIDAS;
    }

    public List<TareaFiltroDto> filtrarTareas(String estado, Integer integranteId, Boolean sinAsignar) throws BusinessException {
        String filtroEstado = (estado != null && !estado.isBlank()) ? estado.trim().toUpperCase() : null;
        if (filtroEstado != null) {
            validationUtil.validateEstado(filtroEstado);
        }

        if (integranteId != null) {
            validateIntegrante(integranteId);
        }

        List<TareaModel> tareas;

        if (Boolean.TRUE.equals(sinAsignar)) {
            if (filtroEstado != null) {
                tareas = tareaRepository.findByEstadoAndIntegranteIdIsNull(filtroEstado);
            } else {
                tareas = tareaRepository.findByIntegranteIdIsNull();
            }
        } else if (filtroEstado != null && integranteId != null) {
            tareas = tareaRepository.findByEstadoAndIntegranteId(filtroEstado, integranteId);
        } else if (filtroEstado != null) {
            tareas = tareaRepository.findByEstado(filtroEstado);
        } else if (integranteId != null) {
            tareas = tareaRepository.findByIntegranteId(integranteId);
        } else {
            tareas = tareaRepository.findAll();
        }

        return tareas.stream()
                .map(this::mapToFiltroDto)
                .toList();
    }

    public List<TareaFiltroDto> filtrarPorEstado(String estado) throws BusinessException {
        return filtrarTareas(estado, null, false);
    }

    public List<TareaFiltroDto> filtrarPorResponsable(Integer integranteId) throws BusinessException {
        return filtrarTareas(null, integranteId, false);
    }

    public List<TareaFiltroDto> filtrarSinAsignar() throws BusinessException {
        return filtrarTareas(null, null, true);
    }

    private TareaFiltroDto mapToFiltroDto(TareaModel tarea) {
        IntegranteModel integrante = null;
        if (tarea.getIntegranteId() != null) {
            integrante = integranteRepository.findById(tarea.getIntegranteId()).orElse(null);
        }
        return TareaFiltroDto.from(tarea, integrante);
    }

    private void validateIntegrante(Integer integranteId) throws BusinessException {
        if (integranteId != null && !integranteRepository.existsById(integranteId)) {
            throw BusinessException.builder()
                    .message("El integrante no existe")
                    .build();
        }
    }
}