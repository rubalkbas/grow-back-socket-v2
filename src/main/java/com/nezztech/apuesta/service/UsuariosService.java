package com.nezztech.apuesta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nezztech.apuesta.model.dto.CambiarContrasenaDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.dto.RolUsuarioDTO;
import com.nezztech.apuesta.model.dto.UsuarioDTO;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.model.entity.DineroEntity;
import com.nezztech.apuesta.model.entity.Permiso2Entity;
import com.nezztech.apuesta.security.entity.RolEntity;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.repositoy.IUsuarioRepository;
import com.nezztech.apuesta.security.repositoy.UsuarioInternoRepository;
import com.nezztech.apuesta.util.GenericasConstantes;
import com.nezztech.apuesta.util.JasyptEncryptionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class UsuariosService implements IUsuariosService {
	
	/** Repository Chat Service*/
	@Autowired
	private UsuarioInternoRepository usuarioInternoRepository;
	
	@Autowired
	private IUsuarioRepository repository;
	
	private final JasyptEncryptionUtil jasyptEncryption;
	
	public UsuariosService(JasyptEncryptionUtil jasyptEncryption) {
        this.jasyptEncryption = jasyptEncryption;
    }
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuariosSistema( ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<UsuarioInterno> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
			
			lista = usuarioInternoRepository.findAll();
			
			List<UsuarioInterno> lista2 = lista.stream()
                    .filter(usuario -> usuario.getIdRol().getIdRol() != 2)
                    .collect(Collectors.toList());
			
			lista2.forEach(usuarioInterno -> usuarioInterno.setPass(jasyptEncryption.desencriptar(usuarioInterno.getPass())));
			
			if(lista2.isEmpty()) {
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista2);
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuariosClientes( Integer idAdmin ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<UsuarioInterno> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
			
			lista = usuarioInternoRepository.findByIdAdmin(idAdmin);
			
			List<UsuarioInterno> lista2 = lista.stream()
                    .filter(usuario -> usuario.getIdRol().getIdRol() == 2)
                    .collect(Collectors.toList());
			
			lista2.forEach(usuarioInterno -> usuarioInterno.setPass(jasyptEncryption.desencriptar(usuarioInterno.getPass())));
			
			if(lista2.isEmpty()) {
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista2);
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaUsuarioClienteID( UsuarioInterno usuarioInterno ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {			
						
			log.info("-");
						
			Optional<UsuarioInterno> usuarioRecuperado = usuarioInternoRepository.findById( usuarioInterno.getIdUsuario() );
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			if(!usuarioRecuperado.isPresent()) {
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				usuarioEnvio.setPass(jasyptEncryption.desencriptar( usuarioEnvio.getPass() ));				
				
				responseDTO.setDto(usuarioEnvio);
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Override
	public ResponseDTO asignarRolUsuario(RolUsuarioDTO rolUsuario) {
		ResponseDTO regresa = new ResponseDTO();
		
		try {
			
			int resp = repository.asignarRol(rolUsuario);
			
			regresa.setEstatus(GenericasConstantes.OK);
			regresa.setMensaje(GenericasConstantes.REGISTROS_ACTUALIZADOS + " " + resp);
			
		} catch(DataAccessException e ) {
			
			log.info("ERROR : " + e.getMessage());
			regresa.setEstatus(GenericasConstantes.ERROR);
			regresa.setCodError(e.getMessage());
			regresa.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}		
		
		return regresa;
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Override
	public ResponseDTO eliminarRolByUser(Long idUsuario) {
	ResponseDTO regresa = new ResponseDTO();
		
		try {
			
			int resp = repository.eliminarRolByUser(idUsuario);
			
			regresa.setEstatus(GenericasConstantes.OK);
			regresa.setMensaje(GenericasConstantes.REGISTROS_ACTUALIZADOS + " " + resp);
			
		} catch(DataAccessException e ) {
			
			log.info("ERROR : " + e.getMessage());
			regresa.setEstatus(GenericasConstantes.ERROR);
			regresa.setCodError(e.getMessage());
			regresa.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}		
		
		return regresa;
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Override
	public ResponseDTO updateUsuario(UsuarioInterno usuarioInterno) {
		
		ResponseDTO regresa = new ResponseDTO();
		
		try {
			
			int resp = repository.updateUsuario(usuarioInterno);
			
			regresa.setEstatus(GenericasConstantes.OK);
			regresa.setMensaje(GenericasConstantes.REGISTROS_ACTUALIZADOS + " " + resp);
			
		} catch(DataAccessException e ) {
			
			log.info("ERROR : " + e.getMessage());
			regresa.setEstatus(GenericasConstantes.ERROR);
			regresa.setCodError(e.getMessage());
			regresa.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}		
		
		return regresa;
		
	}
	
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Override
	public ResponseDTO cambiarPass(CambiarContrasenaDTO cambiarContrasena) {
	ResponseDTO regresa = new ResponseDTO();
		
		try {
			UsuarioDTO oUsuarioDTO = repository.findByPk(cambiarContrasena.getIdUsuario());
			
        	String contrasenaDesencriptada = jasyptEncryption.desencriptar(oUsuarioDTO.getPass());
			if(contrasenaDesencriptada.equals(cambiarContrasena.getPassAntigua())) {
				
		        //CODIFICACION DE CONTRASENA
	            String encodedPassword = jasyptEncryption.encriptar(cambiarContrasena.getPassNueva());
	            cambiarContrasena.setPassNueva(encodedPassword);
				int resp = repository.updatePass(cambiarContrasena);
				
				regresa.setEstatus(GenericasConstantes.OK);
				regresa.setMensaje("Se actualizo correctamente la Contrasena");
			}else {
				regresa.setEstatus(GenericasConstantes.ERROR);
				regresa.setMensaje("La Contrasena Antigua no es correcta");
			}

		} catch(DataAccessException e ) {
			
			log.info("ERROR : " + e.getMessage());
			regresa.setEstatus(GenericasConstantes.ERROR);
			regresa.setCodError(e.getMessage());
			regresa.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}		
		
		return regresa;
	}
	
	@Override
	public ResponseDTO delete(Integer idUsuario) {
		
		ResponseDTO response = new ResponseDTO();

		try {
			
			UsuarioInterno usuarioInterno = usuarioInternoRepository.getById(idUsuario);
			log.info(usuarioInterno.getNombre());
			
			if (!(usuarioInterno==null)) {
				
				usuarioInternoRepository.delete(usuarioInterno);
				
				response.setEstatus(GenericasConstantes.OK);
				response.setMensaje("Se elimino correctamente el usuario");
				
			} else {
				
				response.setEstatus(GenericasConstantes.ERROR);
				response.setMensaje("el usuario no pudo eliminarse ya que no existe");
				
			}
			
		} catch (Exception e) {
			response.setEstatus("Error");
			response.setCodError("Ocurrió un error durante la eliminación: " + e.getMessage());
			response.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
		}

		return response;
		
	}

}
