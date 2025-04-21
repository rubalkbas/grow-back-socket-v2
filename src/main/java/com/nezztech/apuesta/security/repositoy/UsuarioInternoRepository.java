/**
 * 
 */
package com.nezztech.apuesta.security.repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nezztech.apuesta.security.entity.UsuarioInterno;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface UsuarioInternoRepository  extends JpaRepository<UsuarioInterno, Integer> {
	
	 boolean existsByCorreo(String email);
	 
	 Optional<UsuarioInterno> findByCorreo(String email);	
	 
	 List<UsuarioInterno> findByIdAdmin( Integer idAdmin );

}
