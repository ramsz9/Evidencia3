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

