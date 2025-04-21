package com.nezztech.apuesta.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "dinero", schema = "interna")
public class DineroEntity implements Serializable {

	/** serial */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dinero")
	private Integer idDinero;

	@Column(name = "dinero")
	private Double dinero;
	
	@Column(name = "accion")
	private String accion;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "estatus_retiro")
	private Integer estatusRetiro;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "id_usuario")
	private Integer idUsuario;

}
