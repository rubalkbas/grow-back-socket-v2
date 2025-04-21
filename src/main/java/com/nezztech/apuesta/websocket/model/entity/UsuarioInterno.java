/**
 * 
 */
package com.nezztech.apuesta.websocket.model.entity;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Entity
@Table(name="usuarios", schema="interna")
@Getter
@Setter
@ToString
public class UsuarioInterno implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4039673533547242096L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "alias")
	private String alias;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "pass")
	private String pass;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "rol")
	private String rol;
	
	@ManyToOne
	@JoinColumn(name="id_rol")
	private RolEntity idRol;
	
	@Column(name = "estatus")
	private int estatus;
	
	@Column(name = "fecha_creacion")
	private Date fecha;	
	
	@Column(name = "total_dinero")
	private Double totalDinero;
	
	@Column(name = "margen_libre")
	private Double margenLibre;
	
	@Column(name = "margen")
	private Double margen;
	
	@Column(name = "id_admin")
	private Integer idAdmin;
	
	public UsuarioInterno() {}
	
}
