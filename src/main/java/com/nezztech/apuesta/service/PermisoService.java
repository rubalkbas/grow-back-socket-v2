package com.nezztech.apuesta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nezztech.apuesta.model.dto.PermisosDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.Permiso2Entity;
import com.nezztech.apuesta.repository.IPermiso2Repository;

import io.micrometer.core.instrument.dropwizard.DropwizardClock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class PermisoService implements IPermisoService{

	@Autowired
	IPermiso2Repository permisoRepository;
	
	@Override
	public ResponseDTO save(PermisosDTO permiso) {
		ResponseDTO regresa = new ResponseDTO();
		Permiso2Entity permisoNuevo = new Permiso2Entity();
		permisoNuevo.setNombre(permiso.getNombre());
		try {
			Permiso2Entity savedPermiso = permisoRepository.save(permisoNuevo);

			if (savedPermiso != null) {
				regresa.setDto(savedPermiso);
				regresa.setEstatus("OK");
				regresa.setMensaje("Registro insertado");
			} else {
				regresa.setEstatus("Error");
				regresa.setMensaje("Registro no insertado");
			}
		} catch (Exception e) {
			regresa.setEstatus("Error");
			regresa.setMensaje("Ocurrió un error durante la inserción: " + e.getMessage());
		}
		return regresa;
	}

	@Override
	public ResponseDTO getAll() {
	    ResponseDTO response = new ResponseDTO();

	    try {
	        List<Permiso2Entity> permisos = permisoRepository.findAll();
	        response.setEstatus("OK");
	        response.setMensaje("Registros encontrados");
	        response.setLista(permisos);
	    } catch (Exception e) {
	        response.setEstatus("Error");
	        response.setMensaje("Ocurrió un error durante la búsqueda: " + e.getMessage());
	    }

	    return response;
	}

	@Override
	public ResponseDTO update(PermisosDTO permiso) {
		ResponseDTO regresa = new ResponseDTO();
		Permiso2Entity permisoNuevo = new Permiso2Entity();
		permisoNuevo.setIdPermiso(permiso.getIdPermiso());
		permisoNuevo.setNombre(permiso.getNombre());
		try {
			Permiso2Entity savedPermiso = permisoRepository.save(permisoNuevo);

			if (savedPermiso != null) {
				regresa.setDto(savedPermiso);
				regresa.setEstatus("OK");
				regresa.setMensaje("Registro Actualizado");
			} else {
				regresa.setEstatus("Error");
				regresa.setMensaje("Registro no Actualizado");
			}
		} catch (Exception e) {
			regresa.setEstatus("Error");
			regresa.setMensaje("Ocurrió un error durante la Actualizacion: " + e.getMessage());
		}
		return regresa;
	}

	@Override
	public ResponseDTO delete(Integer idPermiso) {
		ResponseDTO response = new ResponseDTO();

		try {
			Permiso2Entity permisoEncontrado = permisoRepository.getById(idPermiso);
			log.info(permisoEncontrado.getNombre());
			if (!(permisoEncontrado==null)) {
				permisoRepository.delete(permisoEncontrado);
				response.setEstatus("OK");
				response.setMensaje("Registro eliminado");
			} else {
				response.setEstatus("Error");
				response.setMensaje("Registro no encontrado");
			}
		} catch (Exception e) {
			response.setEstatus("Error");
			response.setMensaje("Ocurrió un error durante la eliminación: " + e.getMessage());
		}

		return response;
		
	}


}
