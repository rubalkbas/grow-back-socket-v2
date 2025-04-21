/**
 * 
 */
package com.nezztech.apuesta.security.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.exception.ServiceException;
import com.nezztech.apuesta.model.entity.Apalancamiento;
import com.nezztech.apuesta.notificacion.service.NotificacionCorreoService;
import com.nezztech.apuesta.repository.IApalancamientoRepository;
import com.nezztech.apuesta.security.dto.JwtDto;
import com.nezztech.apuesta.security.dto.LoginUsuario;
import com.nezztech.apuesta.security.dto.ResponseDTO;
import com.nezztech.apuesta.security.dto.SesionDto;
import com.nezztech.apuesta.security.entity.RolEntity;
import com.nezztech.apuesta.security.entity.Usuario;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.entity.UsuarioPrincipal;
import com.nezztech.apuesta.security.jwt.JwtProvider;
import com.nezztech.apuesta.security.service.IPermisosRolesService;
import com.nezztech.apuesta.security.service.UsuarioInternoService;


import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

	

	   @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UsuarioInternoService usuarioInternoService;

	    @Autowired
	    JwtProvider jwtProvider;
	    
		@Autowired
		NotificacionCorreoService correoService;
		
		@Autowired
		IPermisosRolesService permisosRoles;
				
	    private final com.nezztech.apuesta.util.JasyptEncryptionUtil jasyptEncryption;

	    public AuthController(com.nezztech.apuesta.util.JasyptEncryptionUtil jasyptEncryption) {
	        this.jasyptEncryption = jasyptEncryption;
	    }
	    
	    @PostMapping("/login")
	    public ResponseEntity<ResponseDTO<SesionDto>> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult)
	    throws ServiceException {
	    	
	    	ResponseDTO<SesionDto> response = new ResponseDTO<SesionDto>();
	    	SesionDto sesion = new SesionDto();	    	
	    	
	    	if(!usuarioInternoService.existsByEmail(loginUsuario.getCorreo())) {
	        	
	        	log.info("DATOS INCORRECTOS FAVOR DE VERIFICAR");
	        	
	    		response.setEstatus("ERROR");
	    		response.setMensaje("DATOS INCORRECTOS FAVOR DE VERIFICAR");
	    		response.setDto(new SesionDto());
	    		
	        	return new ResponseEntity<ResponseDTO<SesionDto>>(response, HttpStatus.BAD_REQUEST); 
	        	
	        }
	    	
	    	Optional<UsuarioInterno> UsuarioInterno2 = usuarioInternoService.getByEmail(loginUsuario.getCorreo());
	    	
        	String contrasenaDesencriptada = jasyptEncryption.desencriptar(UsuarioInterno2.get().getPass());
        	
	    	if(!contrasenaDesencriptada.equals(loginUsuario.getPass())) {
	    		
	    		log.info("DATOS INCORRECTOS FAVOR DE VERIFICAR");
	        	
	    		response.setEstatus("ERROR");
	    		response.setMensaje("DATOS INCORRECTOS FAVOR DE VERIFICAR");
	    		response.setDto(new SesionDto());
	    		
	        	return new ResponseEntity<ResponseDTO<SesionDto>>(response, HttpStatus.BAD_REQUEST);  
	        	
	    		
	    	}else if (UsuarioInterno2.get().getEstatus()== 0) {
				
	    		log.info("USUARIO NO APROBADO");
	        	
	    		response.setEstatus("ERROR");
	    		response.setMensaje("USUARIO NO APROBADO, COMUNIQUESE CON EL ADMINISTRADOR");
	    		response.setDto(new SesionDto());
	    		
	        	return new ResponseEntity<ResponseDTO<SesionDto>>(response, HttpStatus.BAD_REQUEST);  
			}
	     	 
	        UsuarioPrincipal usuarioPrincipal = new UsuarioPrincipal(
	        		UsuarioInterno2.get().getIdUsuario(),
	        		UsuarioInterno2.get().getNombre(), 
	        		UsuarioInterno2.get().getTipo(),
	        		//UsuarioInterno2.get().getPuesto(),
	        		//UsuarioInterno2.get().getVerificacion(),
	        		UsuarioInterno2.get().getCorreo(), 
	        		UsuarioInterno2.get().getPass(),
	        		UsuarioInterno2.get().getIdRol()
	        		, null
			);

	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                usuarioPrincipal,
	                null,
	                usuarioPrincipal.getAuthorities()
	        );

	        //ObtenerPermisos Rol
	        ResponseDTO responsePermisos = new ResponseDTO();
	        
	        responsePermisos= permisosRoles.getAllPermisosByRol(usuarioPrincipal.getIdRol().getIdRol());

			// Asignar permisos al usuario
	        usuarioPrincipal.setPermisos(responsePermisos.getLista());

	        SecurityContextHolder.getContext().setAuthentication(authToken);

	        String jwt = jwtProvider.generateToken(authToken);
	        JwtDto jwtDto = new JwtDto(jwt, usuarioPrincipal.getUsername(), usuarioPrincipal.getAuthorities());

	        sesion.setJwt(jwtDto);
	        sesion.setUsuario(usuarioPrincipal);

	        response.setLista(responsePermisos.getLista());
	        response.setEstatus("OK");
	        response.setMensaje("JWT Generado!");
	        response.setDto(sesion);
	        
	        
			

				
		        return new ResponseEntity<ResponseDTO<SesionDto>>(response, HttpStatus.OK);
		        
	    }   
	    
	    
	    @PostMapping("/nuevo")
	    public ResponseEntity<ResponseDTO<UsuarioInterno>> nuevo(@Valid @RequestBody Usuario nuevoUsuario) throws ServiceException, MessagingException, IOException {	    	
	    	
	    	ResponseDTO<UsuarioInterno> response = new ResponseDTO<UsuarioInterno>();
	    	
	    	log.info("INICIA REGISTRO  !!!");	
	    	log.info(" - ");
	    	log.info("VALIDACION CORREO !!!");
	       
	        if(usuarioInternoService.existsByEmail(nuevoUsuario.getCorreo())) {
	        	
	        	log.info("ESTE CORREO YA EXISTE");
	        	
	    		response.setEstatus("EXISTE");
	    		response.setMensaje("ESTE CORREO YA EXISTE !!!");
	    		response.setDto(new UsuarioInterno());
	        	return new ResponseEntity<ResponseDTO<UsuarioInterno>>(response, HttpStatus.BAD_REQUEST);        	
	        }
	  
	        UsuarioInterno usuario = new UsuarioInterno();   
	        
	        usuario.setNombre(nuevoUsuario.getNombre());	        	      
	        usuario.setCorreo(nuevoUsuario.getCorreo());
	        
	        //CODIFICACION DE CONTRASENA
            String encodedPassword = jasyptEncryption.encriptar(nuevoUsuario.getPass());            
	        usuario.setPass(encodedPassword);

	        usuario.setFecha(new Date());
	        usuario.setEstatus(1);
	    
	        usuario.setTotalDinero(0.0);
	        usuario.setMargenLibre(0.0);
	        usuario.setMargen(0.0);
	        
	        RolEntity rol = new RolEntity();
	        rol.setIdRol(nuevoUsuario.getIdRol().getIdRol());
	        usuario.setIdRol(rol);
	        
	        usuario.setRol(nuevoUsuario.getRol());
	        usuario.setTipo(nuevoUsuario.getRol());
	        
	        String consecitvo = generarIdAlias();
	        
	        usuario.setAlias("GCM-"+consecitvo);	
	        usuario.setIdAdmin(nuevoUsuario.getIdAdmin());
	        
	        log.info("EJECUTANDO QUERY REGISTRO !!!");
	        
	        UsuarioInterno nvoUsuario = usuarioInternoService.save(usuario);
	        
	        usuarioInternoService.generaApalancamientos(nvoUsuario.getIdUsuario());
	        
	        if(nvoUsuario.getEstatus()==1) {
				//correoService.envioPassNueva(nuevoUsuario.getPass(), nvoUsuario.getIdUsuario(), "Ingrese al sistema y actualice la contrasena si lo desea.");
	        }

	        
	        log.info("-");	        
	        
			response.setEstatus("OK");
			response.setMensaje("Guardado correcto");
			response.setDto(nvoUsuario);
			
			log.info("TERMINA REGISTRO USUARIO !!!");
	        
	        return new ResponseEntity<ResponseDTO<UsuarioInterno>>(response, HttpStatus.OK);
	    }    
	    
	    
	    private String generarIdAlias() {
	    	
	    	String alias = "";
	    	
	    	String ali = usuarioInternoService.generarIdAlias();
					
			 String[] partes = ali.split("-");
			 int numerico = Integer.parseInt(partes[1]);
			 
			 numerico = numerico + 1;
			 
			 alias = String.format("%04d", numerico);

	    	return alias;
	    	
	    }
	    
}
