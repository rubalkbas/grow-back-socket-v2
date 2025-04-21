package com.nezztech.apuesta.model.dto;

import java.io.Serializable;
import java.util.Date;
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
public class UsuarioDTO implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** id usuario */
	private Long idUsuario;
	
	/** nombre completo */
	private String nombreCompleto;
	
	/** correo */
	private String correo;
	
	/** pass */
	private String pass;
	
	/** puesto */
	private String puesto;
	
	/** fecha */
	private Date fecha;
	
	/** pass */
	private int estatus;

}
