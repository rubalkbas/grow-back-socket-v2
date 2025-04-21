package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.PermisosRolesDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IPermisosRoles2Service {
	public ResponseDTO save(PermisosRolesDTO permisosRoles);
	public ResponseDTO getAll();
	public ResponseDTO delete(Integer idPermisoRol);
	public ResponseDTO getAllPermisosByRol(Integer idRol);
	public ResponseDTO deletePemisosByRol(Integer idRol);

	
}
