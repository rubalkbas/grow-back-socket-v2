package com.nezztech.apuesta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.model.dto.PermisosRolesDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.Permiso2Entity;
import com.nezztech.apuesta.model.entity.PermisosRoles2Entity;
import com.nezztech.apuesta.model.entity.Rol2Entity;
import com.nezztech.apuesta.repository.IPermiso2Repository;
import com.nezztech.apuesta.repository.IRol2Repository;
import com.nezztech.apuesta.repository.IRolesPermiso2Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class PermisosRoles2Service implements IPermisosRoles2Service {

	@Autowired
	IRolesPermiso2Repository rolesPermiso;
	@Autowired
	IRol2Repository rolRepository;
	@Autowired
	IPermiso2Repository permisosReposritory;

	@Override
	public ResponseDTO save(PermisosRolesDTO permisosRoles) {
		ResponseDTO regresa = new ResponseDTO();

		try {
			Rol2Entity rolEncontrado = rolRepository.getById(permisosRoles.getIdRol());
			Permiso2Entity permisoEncontrado = permisosReposritory.getById(permisosRoles.getIdPermiso());
			PermisosRoles2Entity permisoRolNuevo = new PermisosRoles2Entity();
			permisoRolNuevo.setIdRol(rolEncontrado);
			permisoRolNuevo.setIdPermiso(permisoEncontrado);

			PermisosRoles2Entity permisoRol = rolesPermiso.save(permisoRolNuevo);

			if (permisoRol != null) {
				PermisosRolesDTO permisoRolDTO = toDTO(permisoRol);
				regresa.setDto(permisoRolDTO);
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

	public static PermisosRolesDTO toDTO(PermisosRoles2Entity entity) {
		PermisosRolesDTO dto = new PermisosRolesDTO();
		dto.setIdRolPermiso(entity.getIdRolPermiso());
		dto.setIdRol(entity.getIdRol().getIdRol());
		dto.setIdPermiso(entity.getIdPermiso().getIdPermiso());
		return dto;
	}

	@Override
	public ResponseDTO getAll() {
		ResponseDTO response = new ResponseDTO();

		try {
			List<PermisosRoles2Entity> permisos = rolesPermiso.findAll();
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
	public ResponseDTO delete(Integer idPermisoRol) {
		ResponseDTO response = new ResponseDTO();

		try {
			PermisosRoles2Entity permisoEncontrado = rolesPermiso.getById(idPermisoRol);
			if (!(permisoEncontrado == null)) {
				rolesPermiso.delete(permisoEncontrado);
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

	@Transactional
	@Override
	public ResponseDTO getAllPermisosByRol(Integer idRol) {
		ResponseDTO response = new ResponseDTO();

		try {
			List<PermisosRoles2Entity> permisosRoles = rolesPermiso.findByRol(idRol);

			response.setEstatus("OK");
			response.setMensaje("Registros encontrados");
			response.setLista(permisosRoles);
			
		} catch (Exception e) {
			response.setEstatus("Error");
			response.setMensaje("Ocurrió un error durante la búsqueda: " + e.getMessage());
		}

		return response;
	}

	@Override
	@Transactional
	public ResponseDTO deletePemisosByRol(Integer idRol) {
		ResponseDTO response = new ResponseDTO();
		try {
			rolesPermiso.deleteByRolId(idRol);
			response.setEstatus("OK");
			response.setMensaje("Registros eliminados");
		} catch (Exception e) {
			response.setEstatus("Error");
			response.setMensaje("Ocurrió un error durante la eliminación: " + e.getMessage());
		}
		return response;
	}

}
