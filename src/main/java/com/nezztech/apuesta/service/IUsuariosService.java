package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.CambiarContrasenaDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.dto.RolUsuarioDTO;
import com.nezztech.apuesta.model.dto.UsuarioDTO;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.security.entity.UsuarioInterno;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IUsuariosService {

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuariosSistema( );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuariosClientes( Integer idAdmin );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuarioClienteID( UsuarioInterno usuarioInterno );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO asignarRolUsuario(RolUsuarioDTO rolUsuario);

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO eliminarRolByUser(Long idUsuario);

	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO updateUsuario(UsuarioInterno usuarioInterno);


	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO cambiarPass(CambiarContrasenaDTO cambiarContrasena);
	
	
	public ResponseDTO delete( Integer idUsuario);
	
}
