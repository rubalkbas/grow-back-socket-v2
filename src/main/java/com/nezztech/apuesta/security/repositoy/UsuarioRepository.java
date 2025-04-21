package com.nezztech.apuesta.security.repositoy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.relation.RoleUnresolved;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.model.dto.CambiarContrasenaDTO;
import com.nezztech.apuesta.model.dto.RolUsuarioDTO;
import com.nezztech.apuesta.model.dto.UsuarioDTO;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.util.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsuarioRepository implements IUsuarioRepository {
	
	private static final String SQL_USUARIO_SELECT = "SELECT id_usuario, nombre_completo, correo, pass, puesto, fecha, estatus FROM interna.usuarios";
	
	private static final String SQL_USUARIO_SELECT_PK = "SELECT id_usuario, nombre_completo, correo, pass, puesto, fecha, estatus FROM interna.usuarios where id_usuario = :pUsuarioId";
	
	private static final String SQL_USUARIO_INSERT = "INSERT INTO interna.usuarios (nombre_completo, correo, pass, puesto, fecha, estatus)\r\n"
			+ "VALUES(:nombreCompleto, :correo, :pass, :puesto, :fecha, :estatus) RETURNING id_usuario";
	
	private static final String SQL_USUARIO_ACTUALIZA_ESTATUS = "UPDATE interna.usuarios\r\n"
			+ "SET pass = :pass, correo = :correo, estatus = :estatus, nombre = :nombre where id_usuario = :idUsuario";
	
	private static final String SQL_USUARIO_ACTUALIZA_CONSTRASENA = "UPDATE interna.usuarios\r\n"
			+ "SET fecha = :fecha,verificacion = :verificacion, pass = :contrasena where id_usuario = :idUsuario";
	
	private static final String SQL_USUARIO_ACTUALIZA = "UPDATE interna.usuarios\r\n"
			+ "SET nombre_completo=:nombreCompleto,\r\n"
			+ "	correo=:correo, \r\n"
			+ "	pass=:pass,\r\n"
			+ "	puesto=:puesto,\r\n"
			+ "	fecha=:fecha,\r\n"
			+ "	estatus=:estatus where id_usuario = :idUsuario";
	
	private static final String SQL_USUARIO_ASIGNA_ROL = "UPDATE interna.usuarios\r\n"
			+ "SET id_rol = :idRol where id_usuario = :idUsuario";

	
	private static final String SQL_USUARIO_SELECT_ROL = "SELECT id_usuario, nombre_completo, correo, pass, puesto, fecha, estatus FROM interna.usuarios where id_rol = :idRol";

	private static final String SQL_USUARIO_ELIMINAR_ROL = "UPDATE interna.usuarios\r\n"
	        + "SET id_rol = null WHERE id_usuario = :idUsuario";
	
	@Autowired
	public EntityManager entityManager;
	
	private final com.nezztech.apuesta.util.JasyptEncryptionUtil jasyptEncryption;

    public UsuarioRepository(com.nezztech.apuesta.util.JasyptEncryptionUtil jasyptEncryption) {
        this.jasyptEncryption = jasyptEncryption;
    }
	
	@Transactional
	public int asignarRol(RolUsuarioDTO rolUsuario) {
			
		String sql = SQL_USUARIO_ASIGNA_ROL;
		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idUsuario", rolUsuario.getIdUsuario());
		query.setParameter("idRol", rolUsuario.getIdRol());
				
		log.info("Sql: {}", sql);
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("Usuario no actualizado: ", e.getMessage());
		}
		
		return resp;
	}
	
	
	@Transactional
	public int eliminarRolByUser(Long idUsuario) {
		String sql = SQL_USUARIO_ELIMINAR_ROL;
		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idUsuario", idUsuario);
				
		log.info("Sql: {}", sql);
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("Usuario no actualizado: ", e.getMessage());
		}
		
		return resp;
	}
	
	@Transactional
	public int updateUsuario(UsuarioInterno usuarioInterno) {
			
		String sql = SQL_USUARIO_ACTUALIZA_ESTATUS;
		
		Query query = entityManager.createNativeQuery(sql);
		
		
		 String encodedPassword = jasyptEncryption.encriptar(usuarioInterno.getPass());
		
		query.setParameter("idUsuario", usuarioInterno.getIdUsuario() );
		
		query.setParameter("pass", encodedPassword );		
		query.setParameter("correo", usuarioInterno.getCorreo() );
		query.setParameter("estatus", usuarioInterno.getEstatus() );
		query.setParameter("nombre", usuarioInterno.getNombre());

		log.info("Sql: {}", sql);
		
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("informacion no actualizada: ", e.getMessage());
		}
		
		return resp;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public UsuarioDTO findByPk(Long pUsuarioId) {
		
		UsuarioDTO regresa = null;
		
		String sql = SQL_USUARIO_SELECT_PK;
		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("pUsuarioId", pUsuarioId);
		
		log.info("Sql: {}", sql);
		
		try {
			Object[] obj =  (Object[]) query.getSingleResult();	
			if (obj != null) {
				regresa = creaDto(obj);
			}
		} catch(javax.persistence.NoResultException e ) {
			log.info("Regitro no encontrado: ", e.getMessage());
		}

		return regresa;
		
	}
	
	
	private UsuarioDTO creaDto(Object[] obj) {
		
		UsuarioDTO regresa = new UsuarioDTO();
		
		regresa.setIdUsuario(Util.getLongRs(obj[0]));
		
		Long l = Long.valueOf(obj[0].toString());
		
		regresa.setIdUsuario(l);
		regresa.setNombreCompleto(obj[1].toString());
		regresa.setCorreo(obj[2].toString());
		regresa.setPass(obj[3].toString());
		regresa.setPuesto(obj[4].toString());
		regresa.setFecha((Date) obj[5]);
		
		return regresa;
		
	}
	

	@Transactional
	public int updatePass(CambiarContrasenaDTO cambiarContrasenaDTO) {
		String sql = SQL_USUARIO_ACTUALIZA_CONSTRASENA;
		
		Query query = entityManager.createNativeQuery(sql);
		
		query.setParameter("idUsuario", cambiarContrasenaDTO.getIdUsuario());
		
		query.setParameter("contrasena", cambiarContrasenaDTO.getPassNueva());
		query.setParameter("verificacion", true);
				
		query.setParameter("fecha", new Date());

		log.info("Sql: {}", sql);
		
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("Estatus no actualizado: ", e.getMessage());
		}
		
		return resp;
	}
	
}
