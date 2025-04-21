package com.nezztech.apuesta.model.dto;

import java.io.Serializable;

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
public class PermisosDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPermiso;
	
	private String nombre;
}
