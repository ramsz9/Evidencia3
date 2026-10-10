package com.tareas.util;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.tareas.dto.TareaDto;
import com.tareas.exception.BusinessException;

@Component
public class ValidationUtil {

    public void validate(TareaDto tareaDto) throws BusinessException {
        if (Strings.isBlank(tareaDto.getTitulo())) {
            throw BusinessException.builder()
                    .message("El título no debe estar vacío")
                    .build();
        }
        if (tareaDto.getPrioridad() == null || !List.of("BAJA", "MEDIA", "ALTA").contains(tareaDto.getPrioridad())) {
            throw BusinessException.builder()
                    .message("La prioridad debe ser BAJA, MEDIA o ALTA")
                    .build();
        }
    }

}
