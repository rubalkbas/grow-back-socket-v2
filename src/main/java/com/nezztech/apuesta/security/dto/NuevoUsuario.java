/**
 * 
 */
package com.nezztech.apuesta.security.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class NuevoUsuario implements Serializable {

    /**
	 * SERIAL
	 */
	private static final long serialVersionUID = 8640153550939223590L;

    @NotBlank
    private String alias;
    
    @Email
    private String correo;
    
    @NotBlank
    private String contrasena;
    
    @NotBlank
    private String razonSocial;
    
    @NotBlank
    private String rfc;
     
    @NotBlank
    private Integer idPerfil;   
    
    @NotBlank
    private Integer regimen;  
	
}
