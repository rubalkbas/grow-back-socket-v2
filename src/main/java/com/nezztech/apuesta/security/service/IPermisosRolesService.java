package com.nezztech.apuesta.security.service;

import com.nezztech.apuesta.security.dto.PermisosRolesDTO;
import com.nezztech.apuesta.security.dto.ResponseDTO;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IPermisosRolesService {
	public ResponseDTO save(PermisosRolesDTO permisosRoles);
	public ResponseDTO getAll();
	public ResponseDTO delete(Integer idPermisoRol);
	public ResponseDTO getAllPermisosByRol(Integer idRol);
	public ResponseDTO deletePemisosByRol(Integer idRol);

	
}
