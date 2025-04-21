package com.nezztech.apuesta.model.entity;

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
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Getter
@Setter
@Entity
@Table(name = "apalancamiento", schema = "interna")
public class Apalancamiento implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_apalancamiento")
	private Integer idApalancamiento;
	
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
	private Integer idUsuario;

}
