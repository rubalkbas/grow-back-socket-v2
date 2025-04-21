/**
 * 
 */
package com.nezztech.apuesta.security.dto;

import com.nezztech.apuesta.security.entity.UsuarioPrincipal;

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
public class SesionDto {
	
	UsuarioPrincipal usuario;
	
	JwtDto jwt;
}
