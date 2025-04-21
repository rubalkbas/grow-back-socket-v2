/**
 * 
 */
package com.nezztech.apuesta.security.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.model.entity.Apalancamiento;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.repository.IApalancamientoRepository;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.repositoy.UsuarioInternoRepository;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Service
@Transactional
public class UsuarioInternoService {

	@Autowired
	UsuarioInternoRepository usuarioRepository;
	
	@Autowired
	private IApalancamientoRepository iApalancamientoRepository;
	
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByCorreo(email);
	}
	
	public UsuarioInterno save(UsuarioInterno usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Optional<UsuarioInterno> getByEmail(String email) {
		return usuarioRepository.findByCorreo(email);
	}
	
	public String generarIdAlias() {
		
		String id = "";
		
		List<UsuarioInterno> lista = usuarioRepository.findAll();
		
		UsuarioInterno user = lista.stream()
        .max(Comparator.comparing(UsuarioInterno::getIdUsuario))
        .orElse(null);
		
		return user.getAlias();
	}
	
	public void generaApalancamientos(Integer idUsuario) {
    	
        List<String> simbolos = Arrays.asList("CRIPTO", "DIVISA", "FONDOS", "ACCIONES", "MATERIAS");

        for (String simbolo : simbolos) {
        	
        	Apalancamiento apalancamiento = new Apalancamiento();
        	
        	apalancamiento.setSimbolo(simbolo);
        	apalancamiento.setApalancamiento1Gana(1.0);
        	apalancamiento.setApalancamiento1pierde(1.0);
        	apalancamiento.setApalancamiento2Gana(1.0);
        	apalancamiento.setApalancamiento2pierde(1.0);
        	apalancamiento.setIdUsuario(idUsuario);
        	
        	iApalancamientoRepository.save(apalancamiento);        	
        	
        }    	
    	
    }

}
