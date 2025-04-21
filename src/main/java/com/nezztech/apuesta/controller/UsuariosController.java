package com.nezztech.apuesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.model.dto.CambiarContrasenaDTO;
import com.nezztech.apuesta.model.dto.EliminarRolDTO;
import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.dto.RolUsuarioDTO;
import com.nezztech.apuesta.model.dto.UsuarioDTO;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.service.IUsuariosService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuariosController {
	
	/**  */
	@Autowired
	private IUsuariosService iUsuariosService;
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@GetMapping(path = "/consultaUsuariosSistema")
	public ResponseEntity<ResponseDTO> consultaUsuariosSistema( ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iUsuariosService.consultaUsuariosSistema();	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@GetMapping(path = "/consultaUsuariosClientes/{idAdmin}")
	public ResponseEntity<ResponseDTO> consultaUsuariosClientes( @PathVariable Integer idAdmin ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iUsuariosService.consultaUsuariosClientes( idAdmin );	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaUsuarioClienteID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaUsuarioClienteID( @RequestBody UsuarioInterno usuarioInterno ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iUsuariosService.consultaUsuarioClienteID(usuarioInterno);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PutMapping("/asignaRol")
	public ResponseEntity<ResponseDTO> asignarRol( @RequestBody RolUsuarioDTO rolUsuario)  {
    	
		log.info("-- INICIA ACTUALIZA ESTATUS USUARIO --");
    	
		ResponseDTO response = iUsuariosService.asignarRolUsuario( rolUsuario);

		log.info("-- FIN ACTUALIZA ESTATUS USUARIO --");
		
        return ResponseEntity.ok(response);

	}
    
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
    @PutMapping("/eliminarRol")
	public ResponseEntity<ResponseDTO> eliminarRol(@RequestBody EliminarRolDTO eliminarRol)  {
    	
		log.info("-- INICIA ELIMINAR ROL - USUARIO --");
    	
		ResponseDTO response = iUsuariosService.eliminarRolByUser(eliminarRol.getIdUsuario());

		log.info("-- FIN ELIMINAR ROL - USUARIO --");
		
        return ResponseEntity.ok(response);

	}
    
    
    /**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
    @PutMapping("/actualiza-usuario")
	public ResponseEntity<ResponseDTO> updateUsuario( @RequestBody UsuarioInterno usuarioInterno)  {
    	
		log.info("-- INICIA ACTUALIZA USUARIO --");
    	
		ResponseDTO response = iUsuariosService.updateUsuario( usuarioInterno);

		log.info("-- FIN ACTUALIZA USUARIO --");
		
        return ResponseEntity.ok(response);

	}
    
   
    
    /**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
    @PutMapping("/actualiza-cotrasena")
	public ResponseEntity<ResponseDTO> updateContrasena( @RequestBody CambiarContrasenaDTO cambiarContrasenaDTO)  {
    	
		log.info("-- INICIA ACTUALIZA CONTRASENA USUARIO --");
    	
		ResponseDTO response = iUsuariosService.cambiarPass( cambiarContrasenaDTO);

		log.info("-- FIN ACTUALIZA CONTRASENA USUARIO --");
		
        return ResponseEntity.ok(response);

	}
    
    
    @DeleteMapping("/deleteUsuario/{idUsuario}")
    public ResponseEntity<ResponseDTO> deleteUsuario(@PathVariable Integer idUsuario) {
    	
        ResponseDTO response = iUsuariosService.delete(idUsuario);
        
        if ("OK".equals(response.getEstatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
    }
    

}
