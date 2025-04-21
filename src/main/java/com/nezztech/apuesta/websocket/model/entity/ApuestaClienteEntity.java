package com.nezztech.apuesta.websocket.model.entity;

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
@Table(name = "apuesta_cliente", schema = "interna")
public class ApuestaClienteEntity implements Serializable {

	/** serial */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_apuesta_cliente")
	private Integer idApuestaCliente;	
	
	@Column(name = "tipo_compra")
	private String tipoCompra;
	
	@Column(name = "compra")
	private String compra;
	
	@Column(name = "valor_unidad")
	private Double valorUnidad;
	
	@Column(name = "unidades")
	private Double unidades;
	
	@Column(name = "monto_apuesta")
	private Double montoApuesta;
	
	@Column(name = "variacion")
	private Double variacion;
	
	@Column(name = "ganancia_perdida")
	private Double gananciaPerdida;
	
	@Column(name = "bloque_compra")
	private String bloqueCompra;
	
	@Column(name = "estatus_compra")
	private String estatusCompra;	
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Column(name = "id_usuario")
	private Integer idUsuario;

}
