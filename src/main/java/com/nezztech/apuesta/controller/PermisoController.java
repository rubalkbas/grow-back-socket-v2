package com.nezztech.apuesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.model.dto.PermisosDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.service.IPermisoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@RestController
@RequestMapping("/permiso")
public class PermisoController {


	@Autowired
	IPermisoService permisoService;
	

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePermiso(@RequestBody PermisosDTO permiso) {
        ResponseDTO response = permisoService.save(permiso);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updatePermiso(@RequestBody PermisosDTO permisosDTO) {
        ResponseDTO response = permisoService.update(permisosDTO);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePermiso(@PathVariable Integer id) {
        ResponseDTO response = permisoService.delete(id);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllPermisos() {
        ResponseDTO response = permisoService.getAll();
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
