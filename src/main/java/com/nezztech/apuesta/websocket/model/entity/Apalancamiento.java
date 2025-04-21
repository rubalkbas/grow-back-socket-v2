package com.nezztech.apuesta.websocket.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * ENTITY
 * 
 * @author 
 * 
 */
@Entity
@Table(name="apalancamiento", schema="internanueva")
@Getter
@Setter
public class Apalancamiento implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_apalancamiento")
	private Long idApalancamiento;
	
	@Column(name = "simbolo")
	private String simbolo;
	
	@Column(name = "apalancamiento1gana")
	private Double apalancamiento1Gana;
	
	@Column(name = "apalancamiento2gana")
	private Double apalancamiento2Gana;	
	
	@Column(name = "apalancamiento1pierde")
	private Double apalancamiento1pierde;
	
	@Column(name = "apalancamiento2pierde")
	private Double apalancamiento2pierde;	
	
	@Column(name = "id_usuario")
	private Long idUsuario;

}
