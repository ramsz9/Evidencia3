package com.tareas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tareas.dto.TareaRsDto;
import com.tareas.exception.BusinessException;
import com.tareas.model.TareaModel;
import com.tareas.repository.IntegranteRepository;
import com.tareas.repository.TareaRepository;
import com.tareas.util.ValidationUtil;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private IntegranteRepository integranteRepository;

    private ValidationUtil validationUtil;
    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        validationUtil = new ValidationUtil();
        tareaService = new TareaService(tareaRepository, integranteRepository, validationUtil);
    }

    @Test
    void testUpdateEstadoExitoso() throws BusinessException {
        TareaModel tarea = TareaModel.builder()
                .id(1)
                .titulo("Tarea 1")
                .estado("PENDIENTE")
                .prioridad("MEDIA")
                .build();

        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(TareaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TareaRsDto result = tareaService.updateEstado(1, "EN_PROCESO");

        assertNotNull(result);
        assertEquals("Estado actualizado", result.message());
        assertEquals(1, result.id());
        assertEquals("EN_PROCESO", tarea.getEstado());
        verify(tareaRepository).save(tarea);
    }

    @Test
    void testUpdateEstadoInvalido() {
        assertThrows(BusinessException.class, () -> {
            tareaService.updateEstado(1, "ESTADO_INVENTADO");
        });
    }

    @Test
    void testUpdateEstadoTareaNoExiste() {
        when(tareaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            tareaService.updateEstado(99, "TERMINADA");
        });
    }

    @Test
    void testUpdatePrioridadExitosa() throws BusinessException {
        TareaModel tarea = TareaModel.builder()
                .id(1)
                .titulo("Tarea 1")
                .estado("PENDIENTE")
                .prioridad("BAJA")
                .build();

        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(TareaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TareaRsDto result = tareaService.updatePrioridad(1, "ALTA");

        assertNotNull(result);
        assertEquals("Prioridad actualizada", result.message());
        assertEquals(1, result.id());
        assertEquals("ALTA", tarea.getPrioridad());
        verify(tareaRepository).save(tarea);
    }

    @Test
    void testUpdatePrioridadInvalida() {
        assertThrows(BusinessException.class, () -> {
            tareaService.updatePrioridad(1, "URGENTE");
        });
    }

    @Test
    void testUpdatePrioridadTareaNoExiste() {
        when(tareaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            tareaService.updatePrioridad(99, "MEDIA");
        });
    }

    @Test
    void testUpdateEstadoYPrioridadExitoso() throws BusinessException {
        TareaModel tarea = TareaModel.builder()
                .id(1)
                .titulo("Tarea 1")
                .estado("PENDIENTE")
                .prioridad("BAJA")
                .build();

        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(TareaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TareaRsDto result = tareaService.updateEstadoYPrioridad(1, "TERMINADA", "ALTA");

        assertNotNull(result);
        assertEquals("Estado y prioridad actualizados", result.message());
        assertEquals("TERMINADA", tarea.getEstado());
        assertEquals("ALTA", tarea.getPrioridad());
    }

    @Test
    void testGetEstados() {
        List<String> estados = tareaService.getEstados();
        assertEquals(3, estados.size());
        assertEquals(List.of("PENDIENTE", "EN_PROCESO", "TERMINADA"), estados);
    }

    @Test
    void testGetPrioridades() {
        List<String> prioridades = tareaService.getPrioridades();
        assertEquals(3, prioridades.size());
        assertEquals(List.of("BAJA", "MEDIA", "ALTA"), prioridades);
    }
}
