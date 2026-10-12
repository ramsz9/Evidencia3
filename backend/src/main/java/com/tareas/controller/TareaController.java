package com.tareas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tareas.dto.TareaConsultaDto;
import com.tareas.dto.TareaDto;
import com.tareas.dto.TareaEstadoDto;
import com.tareas.dto.TareaEstadoPrioridadDto;
import com.tareas.dto.TareaFiltroDto;
import com.tareas.dto.TareaPrioridadDto;
import com.tareas.dto.TareaRsDto;
import com.tareas.exception.BusinessException;
import com.tareas.service.TareaService;
import com.tareas.util.ValidationUtil;

@Controller
@RequestMapping(value = "/tarea")
public class TareaController {

    private final TareaService tareaService;
    private final ValidationUtil validationUtil;

    public TareaController(TareaService tareaService, ValidationUtil validationUtil) {
        this.tareaService = tareaService;
        this.validationUtil = validationUtil;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TareaDto tareaDto) throws BusinessException {
        validationUtil.validate(tareaDto);
        TareaRsDto rsDto = tareaService.create(tareaDto);
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/integrante/{integranteId}")
    public ResponseEntity<?> assign(@PathVariable Integer id, @PathVariable Integer integranteId) throws BusinessException {
        TareaRsDto rsDto = tareaService.assign(id, integranteId);
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> updateEstado(@PathVariable Integer id, @PathVariable String estado) throws BusinessException {
        validationUtil.validateEstado(estado);
        TareaRsDto rsDto = tareaService.updateEstado(id, estado);
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> updateEstadoBody(@PathVariable Integer id, @RequestBody TareaEstadoDto dto) throws BusinessException {
        validationUtil.validateEstado(dto.estado());
        TareaRsDto rsDto = tareaService.updateEstado(id, dto.estado());
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/prioridad/{prioridad}")
    public ResponseEntity<?> updatePrioridad(@PathVariable Integer id, @PathVariable String prioridad) throws BusinessException {
        validationUtil.validatePrioridad(prioridad);
        TareaRsDto rsDto = tareaService.updatePrioridad(id, prioridad);
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/prioridad")
    public ResponseEntity<?> updatePrioridadBody(@PathVariable Integer id, @RequestBody TareaPrioridadDto dto) throws BusinessException {
        validationUtil.validatePrioridad(dto.prioridad());
        TareaRsDto rsDto = tareaService.updatePrioridad(id, dto.prioridad());
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/estado-prioridad")
    public ResponseEntity<?> updateEstadoPrioridad(@PathVariable Integer id, @RequestBody TareaEstadoPrioridadDto dto) throws BusinessException {
        validationUtil.validateEstado(dto.estado());
        validationUtil.validatePrioridad(dto.prioridad());
        TareaRsDto rsDto = tareaService.updateEstadoYPrioridad(id, dto.estado(), dto.prioridad());
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<?> getEstados() {
        return new ResponseEntity<>(tareaService.getEstados(), HttpStatus.OK);
    }

    @GetMapping("/prioridades")
    public ResponseEntity<?> getPrioridades() {
        return new ResponseEntity<>(tareaService.getPrioridades(), HttpStatus.OK);
    }

    @GetMapping("/filtro")
    public ResponseEntity<?> filtrar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer integranteId,
            @RequestParam(required = false) Boolean sinAsignar) throws BusinessException {
        List<TareaFiltroDto> tareas = tareaService.filtrarTareas(estado, integranteId, sinAsignar);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping("/filtro/estado/{estado}")
    public ResponseEntity<?> filtrarPorEstado(@PathVariable String estado) throws BusinessException {
        List<TareaFiltroDto> tareas = tareaService.filtrarPorEstado(estado);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping("/filtro/responsable/{integranteId}")
    public ResponseEntity<?> filtrarPorResponsable(@PathVariable Integer integranteId) throws BusinessException {
        List<TareaFiltroDto> tareas = tareaService.filtrarPorResponsable(integranteId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping("/filtro/sin-asignar")
    public ResponseEntity<?> filtrarSinAsignar() throws BusinessException {
        List<TareaFiltroDto> tareas = tareaService.filtrarSinAsignar();
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer integranteId) throws BusinessException {
        List<TareaConsultaDto> tareas = tareaService.findAll(estado, integranteId);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws BusinessException {
        TareaConsultaDto tarea = tareaService.findById(id);
        return new ResponseEntity<>(tarea, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws BusinessException {
        TareaRsDto rsDto = tareaService.delete(id);
        return new ResponseEntity<>(rsDto, HttpStatus.OK);
    }

}

