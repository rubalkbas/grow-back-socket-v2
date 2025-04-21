package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.PermisosDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IPermisoService {
	public ResponseDTO save(PermisosDTO permiso);
	public ResponseDTO getAll();
	public ResponseDTO update(PermisosDTO permiso);
	public ResponseDTO delete(Integer idPermiso);
}
