package com.nezztech.apuesta.security.repositoy;

import java.util.List;

import com.nezztech.apuesta.model.dto.CambiarContrasenaDTO;
import com.nezztech.apuesta.model.dto.RolUsuarioDTO;
import com.nezztech.apuesta.model.dto.UsuarioDTO;
import com.nezztech.apuesta.security.entity.UsuarioInterno;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IUsuarioRepository {
	

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public UsuarioDTO findByPk(Long pUsuarioId);

	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public int updateUsuario( UsuarioInterno usuarioInterno);

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public int asignarRol(RolUsuarioDTO rolUsuario);

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public int eliminarRolByUser(Long idUsuario);

	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public int updatePass( CambiarContrasenaDTO cambiarContrasenaDTO);
	
}
