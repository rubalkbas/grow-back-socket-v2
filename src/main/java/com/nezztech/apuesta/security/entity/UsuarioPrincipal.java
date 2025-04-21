/**
 * 
 */
package com.nezztech.apuesta.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class UsuarioPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String nombre;
	//private String puesto;
	private String correo;
	private String tipo;
	private String pass;
	//private Boolean verificacion;
	private RolEntity idRol;
	private List<?> permisos;
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(int id, String nombre, String tipo,
			//String puesto,
			//,Boolean verificacion ,
			 String correo, String pass, RolEntity idRol, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		//this.puesto = puesto;
		this.correo = correo;
		this.pass = pass;
		this.idRol = idRol;
		//this.verificacion=verificacion;
		this.authorities = authorities;
	}

	public static UsuarioPrincipal build(UsuarioInterno usuario) {
		GrantedAuthority auth = new SimpleGrantedAuthority(usuario.getTipo());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(auth);

		return new UsuarioPrincipal(usuario.getIdUsuario(),usuario.getNombre(), usuario.getTipo(),
				//usuario.getPuesto(),
				//usuario.getVerificacion(),
				usuario.getCorreo(), usuario.getPass(), usuario.getIdRol(), authorities );
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public String getUsername() {
		return nombre;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
