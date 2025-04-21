package com.nezztech.apuesta.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Getter
@Setter
public class CambiarContrasenaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	
	private String passAntigua;
	
	private String passNueva;

}
