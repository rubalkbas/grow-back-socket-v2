package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.dto.RolDTO;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IRolService {
	public ResponseDTO save(RolDTO rol);
	public ResponseDTO getAll();
	public ResponseDTO updateEstatus(RolDTO rol);
	public ResponseDTO delete(int idRol);
}
