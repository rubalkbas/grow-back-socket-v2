/**
 * 
 */
package com.nezztech.apuesta.security.dto;

import java.io.Serializable;

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
public class LoginUsuario implements Serializable {

	private static final long serialVersionUID = 482016261392380420L;
	
	@NotBlank
    private String correo;
	
    @NotBlank
    private String pass;	
    
}
