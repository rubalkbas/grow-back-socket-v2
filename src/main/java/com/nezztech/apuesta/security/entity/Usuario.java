package com.nezztech.apuesta.security.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "usuarios", schema = "interna")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
		
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "estatus")
	private int estatus;

	@Column(name = "rol")
	private String rol;
	
	@Column(name = "tipo")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name="id_rol")
	private RolEntity idRol;
	
	@Column(name = "total_dinero")
	private Double totalDinero;
	
	@Column(name = "margen_libre")
	private Double margenLibre;
	
	@Column(name = "margen")
	private Double margen;
	
	@Column(name = "id_admin")
	private Integer idAdmin;
}
