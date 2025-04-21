package com.nezztech.apuesta.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = "roles_permisos", schema = "interna")
public class PermisosRolesEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -308700941117691724L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol_permiso")
	private Integer idRolPermiso;

	@ManyToOne
	@JoinColumn(name = "id_permiso")
	private PermisoEntity idPermiso;
	
	@ManyToOne
	@JoinColumn(name = "id_rol")
	private  RolEntity idRol;

}
