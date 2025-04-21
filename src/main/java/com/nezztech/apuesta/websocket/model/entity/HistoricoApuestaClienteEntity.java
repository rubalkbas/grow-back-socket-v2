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
 * ENTITY
 * 
 * @author 
 * 
 */
@Entity
@Table(name="historico_apuesta_cliente", schema="internanueva")
@Getter
@Setter
public class HistoricoApuestaClienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hist_apuest_cli")
	private Long idHistApuestaCliente;	
	
	@Column(name = "id_apuesta_cliente")
	private Long idApuestaCliente;
	
	@Column(name = "valor_compra")
	private Double valorCompra;
	
	@Column(name = "valor_web_socket")
	private Double valorWebSocket;
	
	@Column(name = "gan_per")
	private String ganPer;	

	@Column(name = "monto_gan_per")
	private Double montoGanPer;

}
