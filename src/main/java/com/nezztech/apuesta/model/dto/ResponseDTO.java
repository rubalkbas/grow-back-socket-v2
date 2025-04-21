package com.nezztech.apuesta.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class ResponseDTO<T> implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**ESTATUS*/
	protected String estatus;
	/**MENSAJE*/
	protected String mensaje;
	/**LISTA*/
	protected List<?> lista;
	/**DTO*/
	protected T dto;
	/**COD ERROR*/
	protected String codError;
	/**TOTAL	*/
	protected String sumaTotal;

}
