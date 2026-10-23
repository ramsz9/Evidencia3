package com.tareas.util;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.tareas.dto.TareaDto;
import com.tareas.exception.BusinessException;

@Component
public class ValidationUtil {

    public static final List<String> ESTADOS_VALIDOS = List.of("PENDIENTE", "EN_PROCESO", "TERMINADA");
    public static final List<String> PRIORIDADES_VALIDAS = List.of("BAJA", "MEDIA", "ALTA");

    public void validate(TareaDto tareaDto) throws BusinessException {
        if (Strings.isBlank(tareaDto.getTitulo())) {
            throw BusinessException.builder()
                    .message("El título no debe estar vacío")
                    .build();
        }
        validatePrioridad(tareaDto.getPrioridad());
    }

    public void validateEstado(String estado) throws BusinessException {
        if (Strings.isBlank(estado) || !ESTADOS_VALIDOS.contains(estado.trim().toUpperCase())) {
            throw BusinessException.builder()
                    .message("El estado proporcionado no es válido. Use: PENDIENTE, EN_PROCESO o TERMINADA")
                    .build();
        }
    }

    public void validatePrioridad(String prioridad) throws BusinessException {
        if (Strings.isBlank(prioridad) || !PRIORIDADES_VALIDAS.contains(prioridad.trim().toUpperCase())) {
            throw BusinessException.builder()
                    .message("La prioridad debe ser BAJA, MEDIA o ALTA")
                    .build();
        }
    }

}
