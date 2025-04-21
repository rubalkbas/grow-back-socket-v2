package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.DineroEntity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IDineroService {
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaIngreso( DineroEntity dineroEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO cargarIngreso( DineroEntity dineroEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaRetiroSolicitadoId( DineroEntity dineroEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaRetiroEfectuadoId( DineroEntity dineroEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaAllRetirosSolicitados( );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO cargarRetiro( DineroEntity dineroEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO apruebaRetiro( DineroEntity dineroEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaCredito( DineroEntity dineroEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaCreditoPagados( DineroEntity dineroEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO cargarCredito( DineroEntity dineroEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO pagoCredito( DineroEntity dineroEntity );
	
	

}
