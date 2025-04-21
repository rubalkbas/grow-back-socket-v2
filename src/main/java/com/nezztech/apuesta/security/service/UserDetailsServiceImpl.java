/**
 * 
 */
package com.nezztech.apuesta.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.entity.UsuarioPrincipal;
 
/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioInternoService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		UsuarioInterno usuario= usuarioService.getByEmail(nombreUsuario).get();

		return UsuarioPrincipal.build(usuario);
	}
}
