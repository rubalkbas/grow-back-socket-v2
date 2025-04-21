package com.nezztech.apuesta.websocket.repository;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.model.entity.UsuarioInterno;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsuarioRepository {
	
	@Autowired
	public EntityManager entityManager;	
	
	//private static final String SQL_USUARIO_ACTUALIZA_CONSTRASENA = "UPDATE interna.usuarios SET margen_libre = :margenLibre WHERE id_usuario = :idUsuario";
	
	private static final String SQL_USUARIO_ACTUALIZA_CONSTRASENA = "UPDATE interna.usuarios \r\n"
			+ "	SET margen_libre = ( (SELECT  margen_libre FROM interna.usuarios WHERE id_usuario=:idUsuario) + :margenLibre) \r\n"
			+ "		WHERE id_usuario = :idUsuario";
	
	@Transactional
	public void actualizaMargenLibre( ApuestaClienteEntity apuestaClienteEntity , Double margenLibre) {
		
		String sql = SQL_USUARIO_ACTUALIZA_CONSTRASENA;
		
		Query query = entityManager.createNativeQuery(sql);		
		query.setParameter( "idUsuario", apuestaClienteEntity.getIdUsuario() );
		query.setParameter( "margenLibre", margenLibre );
				
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("Estatus no actualizado: ", e.getMessage());
		}
		
		//return resp;
	}

}
