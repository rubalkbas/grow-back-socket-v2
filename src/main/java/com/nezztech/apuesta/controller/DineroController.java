package com.nezztech.apuesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.DineroEntity;
import com.nezztech.apuesta.service.IDineroService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@RestController
@RequestMapping("/dinero")
@Slf4j
public class DineroController {
	
	/**  */
	@Autowired
	private IDineroService iDineroService;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/** INGRESO DINERO CUENTA */
	/////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaIngreso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaIngreso( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.consultaIngreso(dineroEntity);	
		
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
	@PostMapping(path = "/cargarIngreso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> cargarIngreso( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.cargarIngreso(dineroEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/** RETIRO DINERO CUENTA */
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaRetiroSolicitado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaRetiroSolicitadoId( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.consultaRetiroSolicitadoId(dineroEntity);	
		
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
	@PostMapping(path = "/consultaRetiroEfectuado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaRetiroEfectuadoId( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.consultaRetiroEfectuadoId(dineroEntity);	
		
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
//	@GetMapping(path = "/consultaAllRetirosSolicitados")
//	public ResponseEntity<ResponseDTO> consultaAllRetirosSolicitados() {
//		
//		log.info("-");
//		log.info("INICIA  !!!");
//		
//		ResponseDTO responseDTO = iDineroService.consultaAllRetirosSolicitados( );	
//		
//		log.info("TERMINA !!!");
//		log.info("-");
//		
//		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//		
//	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/cargarRetiro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> cargarRetiro( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.cargarRetiro(dineroEntity);	
		
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
	@PostMapping(path = "/apruebaRetiro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> apruebaRetiro( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.apruebaRetiro(dineroEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
		
		
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/** CREDITO DINERO CUENTA */
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@PostMapping(path = "/consultaCredito", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaCredito( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.consultaCredito(dineroEntity);	
		
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
	@PostMapping(path = "/consultaCreditoPagados", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> consultaCreditoPagados( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.consultaCredito(dineroEntity);	
		
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
	@PostMapping(path = "/cargarCredito", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> cargarCredito( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.cargarCredito(dineroEntity);	
		
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
	@PostMapping(path = "/pagoCredito", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> pagoCredito( @RequestBody DineroEntity dineroEntity ) {
		
		log.info("-");
		log.info("INICIA  !!!");
		
		ResponseDTO responseDTO = iDineroService.pagoCredito(dineroEntity);	
		
		log.info("TERMINA !!!");
		log.info("-");
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}
	
	

}
