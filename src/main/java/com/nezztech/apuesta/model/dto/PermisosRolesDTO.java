package com.nezztech.apuesta.model.dto;

import java.io.Serializable;
import java.util.List;

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
public class PermisosRolesDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idRolPermiso;

	private Integer idPermiso;

	private Integer idRol;

}
