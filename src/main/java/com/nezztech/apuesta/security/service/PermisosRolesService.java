package com.nezztech.apuesta.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.security.dto.PermisosRolesDTO;
import com.nezztech.apuesta.security.dto.ResponseDTO;
import com.nezztech.apuesta.security.entity.PermisoEntity;
import com.nezztech.apuesta.security.entity.PermisosRolesEntity;
import com.nezztech.apuesta.security.entity.RolEntity;
import com.nezztech.apuesta.security.repositoy.IPermisoRepository;
import com.nezztech.apuesta.security.repositoy.IRolRepository;
import com.nezztech.apuesta.security.repositoy.IRolesPermisoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class PermisosRolesService implements IPermisosRolesService {

	@Autowired
	IRolesPermisoRepository rolesPermiso;
	@Autowired
	IRolRepository rolRepository;
	@Autowired
	IPermisoRepository permisosReposritory;

	@Override
	public ResponseDTO save(PermisosRolesDTO permisosRoles) {
		ResponseDTO regresa = new ResponseDTO();

		try {
			RolEntity rolEncontrado = rolRepository.getById(permisosRoles.getIdRol());
			PermisoEntity permisoEncontrado = permisosReposritory.getById(permisosRoles.getIdPermiso());
			PermisosRolesEntity permisoRolNuevo = new PermisosRolesEntity();
			permisoRolNuevo.setIdRol(rolEncontrado);
			permisoRolNuevo.setIdPermiso(permisoEncontrado);

			PermisosRolesEntity permisoRol = rolesPermiso.save(permisoRolNuevo);

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

	public static PermisosRolesDTO toDTO(PermisosRolesEntity entity) {
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
			List<PermisosRolesEntity> permisos = rolesPermiso.findAll();
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
			PermisosRolesEntity permisoEncontrado = rolesPermiso.getById(idPermisoRol);
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
			List<PermisosRolesEntity> permisosRoles = rolesPermiso.findByRol(idRol);

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
