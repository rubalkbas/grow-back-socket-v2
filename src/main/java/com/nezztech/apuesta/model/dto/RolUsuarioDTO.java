package com.nezztech.apuesta.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Getter
@Setter
@ToString
public class RolUsuarioDTO {
	
	/** id usuario */
	private Long idUsuario;
	
	private int idRol;
}
