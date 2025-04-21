package com.nezztech.apuesta.service;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.Apalancamiento;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.model.entity.HistoricoApuestaClienteEntity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public interface IApuestaClienteService {
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaApuestasAbiertasClienteID( ApuestaClienteEntity apuestaClienteEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaApuestasCerradasClienteID( ApuestaClienteEntity apuestaClienteEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO crearApuestas( ApuestaClienteEntity apuestaClienteEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO cerrarApuesta( ApuestaClienteEntity apuestaClienteEntity );
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaApuestasHistoricoAbiertasClienteID( HistoricoApuestaClienteEntity historicoApuestaClienteEntity );


	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO actualizarApuesta( ApuestaClienteEntity apuestaClienteEntity );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO consultaApalancamientoClienteID( Apalancamiento apalancamiento );
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	public ResponseDTO actualizarApalancamiento( Apalancamiento apalancamiento );
	
	
}
