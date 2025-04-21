/**
 * 
 */
package com.nezztech.apuesta.websocket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nezztech.apuesta.websocket.model.entity.UsuarioInterno;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface UsuarioInternoRepository  extends JpaRepository<UsuarioInterno, Integer> {
	 
	 Optional<UsuarioInterno> findByCorreoUsuario(String email);		 
  
}
