package com.nezztech.apuesta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.dto.RolDTO;
import com.nezztech.apuesta.model.entity.Rol2Entity;
import com.nezztech.apuesta.repository.IRol2Repository;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Service
public class RolService implements IRolService {

	@Autowired
	IRol2Repository rolRepository;

	@Override
	public ResponseDTO save(RolDTO rol) {
		ResponseDTO regresa = new ResponseDTO();
		Rol2Entity rolNuevo = new Rol2Entity();
		rolNuevo.setNombreRol(rol.getNombreRol());
		rolNuevo.setEstatus(rol.getEstatus());
		rolNuevo.setFechaCreacion(LocalDateTime.now());
		rolNuevo.setFechaAct(LocalDateTime.now());
		try {
			Rol2Entity savedRol = rolRepository.save(rolNuevo);

			if (savedRol != null) {
				regresa.setDto(savedRol);
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
	        List<Rol2Entity> roles = rolRepository.findAll();
	        
	        List<Rol2Entity> filteredRoles = roles.stream()
	            .filter(role -> role.getIdRol() != 1 && role.getIdRol() != 2)
	            .collect(Collectors.toList());

	        response.setEstatus("OK");
	        response.setMensaje("Registros encontrados");
	        response.setLista(filteredRoles);
	    } catch (Exception e) {
	        response.setEstatus("Error");
	        response.setMensaje("Ocurrió un error durante la búsqueda: " + e.getMessage());
	    }

	    return response;
	}

	@Override
	public ResponseDTO updateEstatus(RolDTO rol) {
		ResponseDTO regresa = new ResponseDTO();
		Rol2Entity rolNuevo = new Rol2Entity();
		rolNuevo.setIdRol(rol.getIdRol());
		rolNuevo.setNombreRol(rol.getNombreRol());
		rolNuevo.setEstatus(rol.getEstatus());
		rolNuevo.setFechaAct(LocalDateTime.now());
		try {
			Rol2Entity savedRol = rolRepository.save(rolNuevo);

			if (savedRol != null) {
				regresa.setDto(savedRol);
				regresa.setEstatus("OK");
				regresa.setMensaje("Se Actualizo el Rol");
			} else {
				regresa.setEstatus("Error");
				regresa.setMensaje("No se Actualizo el Rol");
			}
		} catch (Exception e) {
			regresa.setEstatus("Error");
			regresa.setMensaje("Ocurrió un error durante la inserción: " + e.getMessage());
		}
		return regresa;
	}

	@Override
	public ResponseDTO delete(int idRol) {
		ResponseDTO response = new ResponseDTO();

		try {
			if (rolRepository.existsById(idRol)) {
				rolRepository.deleteById(idRol);
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
