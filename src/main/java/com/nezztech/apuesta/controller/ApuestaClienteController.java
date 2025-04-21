package com.nezztech.apuesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.Apalancamiento;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.model.entity.DineroEntity;
import com.nezztech.apuesta.model.entity.HistoricoApuestaClienteEntity;
import com.nezztech.apuesta.service.IApuestaClienteService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@RestController
@RequestMapping("/apuestaCliente")
@Slf4j
public class ApuestaClienteController {
	
	/**  */
	@Autowired
	private IApuestaClienteService iApuestaClienteService;
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaApuestasAbiertasClienteID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaApuestasAbiertasClienteID( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.consultaApuestasAbiertasClienteID(apuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaApuestasCerradasClienteID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaApuestasCerradasClienteID( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.consultaApuestasCerradasClienteID(apuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/crearApuestas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> crearApuestas( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.crearApuestas(apuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/cerrarApuesta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> cerrarApuesta( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.cerrarApuesta(apuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaApuestasHistoricoAbiertasClienteID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaApuestasHistoricoAbiertasClienteID( @RequestBody HistoricoApuestaClienteEntity historicoApuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.consultaApuestasHistoricoAbiertasClienteID(historicoApuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/actualizarApuesta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> actualizarApuesta( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.actualizarApuesta(apuestaClienteEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaApalancamientoClienteID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaApalancamientoClienteID( @RequestBody Apalancamiento apalancamiento ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.consultaApalancamientoClienteID(apalancamiento);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/actualizarApalancamiento", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> actualizarApalancamiento( @RequestBody Apalancamiento apalancamiento ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iApuestaClienteService.actualizarApalancamiento(apalancamiento);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}

}
