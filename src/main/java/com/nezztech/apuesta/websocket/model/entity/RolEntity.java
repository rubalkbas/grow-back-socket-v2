package com.nezztech.apuesta.websocket.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "roles", schema = "interna")
public class RolEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol")
	private Integer idRol;

	@Column(name = "nombre_rol")
	private String nombreRol;
	
	@Column(name = "estatus")
	private int estatus;
	
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_act")
	private LocalDateTime fechaAct;
}
