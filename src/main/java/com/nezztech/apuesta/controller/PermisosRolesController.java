package com.nezztech.apuesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.model.dto.PermisosRolesDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.service.IPermisosRoles2Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@RestController
@RequestMapping("/permisos-roles")
public class PermisosRolesController {
	@Autowired
	IPermisosRoles2Service rolesPermisoService;
	

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePermisoRol(@RequestBody PermisosRolesDTO permiso) {
        ResponseDTO response = rolesPermisoService.save(permiso);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllPermisosRoles() {
        ResponseDTO response = rolesPermisoService.getAll();
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePermisosRoles(@PathVariable Integer id) {
        ResponseDTO response = rolesPermisoService.delete(id);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/rol/{id}")
    public ResponseEntity<ResponseDTO> getByRoll(@PathVariable int id) {
        ResponseDTO response = rolesPermisoService.getAllPermisosByRol(id);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/rol/{id}")
    public ResponseEntity<ResponseDTO> deletePermisosByRol(@PathVariable Integer id) {
        ResponseDTO response = rolesPermisoService.deletePemisosByRol(id);
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
