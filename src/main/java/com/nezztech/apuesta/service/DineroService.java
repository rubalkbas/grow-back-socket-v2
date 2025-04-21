package com.nezztech.apuesta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.DineroEntity;
import com.nezztech.apuesta.repository.IDineroRepository;
import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.repositoy.UsuarioInternoRepository;
import com.nezztech.apuesta.util.GenericasConstantes;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class DineroService implements IDineroService {
	
	/** Repository Service*/
	@Autowired
	private IDineroRepository iDineroRepository;
	
	@Autowired
	UsuarioInternoRepository usuarioRepository;
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Transactional
	@Override
	public ResponseDTO consultaIngreso( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndIdUsuario( "INGRESO" , dineroEntity.getIdUsuario() );
			
			if(lista.isEmpty()) {
				
				responseDTO.setSumaTotal("0");
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iDineroRepository.sumaIngresoIdUsuario(dineroEntity.getIdUsuario());
								
				responseDTO.setSumaTotal(String.valueOf(total));
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	@Transactional
	@Override
	public ResponseDTO cargarIngreso( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");
			
			dineroEntity.setAccion("TRANSFERENCIA");
			dineroEntity.setAccion("INGRESO");
			dineroEntity.setFechaCreacion(new Date());
			
			iDineroRepository.save(dineroEntity);
			
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(dineroEntity.getIdUsuario());
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			Double dineroTotal = usuarioEnvio.getTotalDinero() + dineroEntity.getDinero();
			
			Double dineroMargenLib = usuarioEnvio.getMargenLibre() + dineroEntity.getDinero();
			
			usuarioEnvio.setTotalDinero(dineroTotal);
			usuarioEnvio.setMargenLibre(dineroMargenLib);
			
			usuarioRepository.save(usuarioEnvio);
			
			log.info("FINALIZA ");
			
			responseDTO.setEstatus(GenericasConstantes.OK);
			responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Transactional
	@Override
	public ResponseDTO consultaRetiroSolicitadoId( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndIdUsuarioAndEstatusRetiro( "RETIRO" , dineroEntity.getIdUsuario(), 0);
			
			if(lista.isEmpty()) {
				
				responseDTO.setSumaTotal("0");
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iDineroRepository.sumaIRetiroSolcitadoIdUsuario(dineroEntity.getIdUsuario());
				
				responseDTO.setSumaTotal(String.valueOf(total));
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Transactional
	@Override
	public ResponseDTO consultaRetiroEfectuadoId( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndIdUsuarioAndEstatusRetiro( "RETIRO" , dineroEntity.getIdUsuario(), 1);
			
			if(lista.isEmpty()) {
				
				responseDTO.setSumaTotal("0");
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iDineroRepository.sumaIRetiroEfectuadoIdUsuario(dineroEntity.getIdUsuario());
				
				responseDTO.setSumaTotal(String.valueOf(total));
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
	@Transactional
	@Override
	public ResponseDTO consultaAllRetirosSolicitados(  ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndEstatusRetiro( "RETIRO" ,  0);
			
			if(lista.isEmpty()) {
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	@Transactional
	@Override
	public ResponseDTO cargarRetiro( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");
			
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(dineroEntity.getIdUsuario());
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			Double balance = usuarioEnvio.getTotalDinero();
			
			if(dineroEntity.getDinero() <= balance ) {				
				
				dineroEntity.setEstatusRetiro(0); // 0 es solicitado
				dineroEntity.setAccion("RETIRO");
				dineroEntity.setFechaCreacion(new Date());
				
				iDineroRepository.save(dineroEntity);
				
				log.info("FINALIZA ");
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);
				
			}else {
				
				responseDTO.setEstatus("PASA");
				responseDTO.setMensaje("No puedes realizar un retiro con un monto mayor a tu balance!");
				
			}
			
			

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
    @Transactional
	@Override
	public ResponseDTO apruebaRetiro( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");
			
			dineroEntity.setEstatusRetiro(1); // 1 es efectuado
			dineroEntity.setFechaCreacion(new Date());
					
			iDineroRepository.save(dineroEntity);
			
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(dineroEntity.getIdUsuario());
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			Double dineroTotal = usuarioEnvio.getTotalDinero() - dineroEntity.getDinero();
			Double margenLi = usuarioEnvio.getMargenLibre() - dineroEntity.getDinero();
			
			usuarioEnvio.setTotalDinero(dineroTotal);
			usuarioEnvio.setMargenLibre(margenLi);
			
			usuarioRepository.save(usuarioEnvio);
			
			log.info("FINALIZA ");
			
			responseDTO.setEstatus(GenericasConstantes.OK);
			responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
	
	
	/**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
    @Transactional
	@Override
	public ResponseDTO consultaCredito( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndIdUsuarioAndEstatusRetiro( "CREDITO" , dineroEntity.getIdUsuario() ,0);
			
			if(lista.isEmpty()) {
				
				responseDTO.setSumaTotal("0");
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iDineroRepository.sumaCreditoIdUsuario(dineroEntity.getIdUsuario());
				
				responseDTO.setSumaTotal(String.valueOf(total));
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
    
    
    /**
	 * 
	 * @author NEZZTECH
	 * 
	 * @param 
	 * @return 
	 * 
	 */
    @Transactional
	@Override
	public ResponseDTO consultaCreditoPagados( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<DineroEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iDineroRepository.findByAccionAndIdUsuarioAndEstatusRetiro( "CREDITO" , dineroEntity.getIdUsuario(),1 );
			
			if(lista.isEmpty()) {
				
				responseDTO.setSumaTotal("0");
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iDineroRepository.sumaCreditoIdUsuario(dineroEntity.getIdUsuario());
				
				responseDTO.setSumaTotal(String.valueOf(total));
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
    
    
	
    @Transactional
	@Override
	public ResponseDTO cargarCredito( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");
			
			dineroEntity.setEstatusRetiro(0);
			dineroEntity.setTipo("NO APLICA");
			dineroEntity.setAccion("CREDITO");
			dineroEntity.setFechaCreacion(new Date());
			
			iDineroRepository.save(dineroEntity);
			
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(dineroEntity.getIdUsuario());
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			Double dineroTotal = usuarioEnvio.getMargenLibre() + dineroEntity.getDinero();
			
			usuarioEnvio.setMargenLibre(dineroTotal);
			
			usuarioRepository.save(usuarioEnvio);
			
			log.info("FINALIZA ");
			
			responseDTO.setEstatus(GenericasConstantes.OK);
			responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}
    
    
    @Transactional
	@Override
	public ResponseDTO pagoCredito( DineroEntity dineroEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");
			
			dineroEntity.setEstatusRetiro(1); // 1 es pagado
			dineroEntity.setFechaCreacion(new Date());
						
			iDineroRepository.save(dineroEntity);
			
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(dineroEntity.getIdUsuario());
			UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
			
			Double dineroTotal = usuarioEnvio.getTotalDinero() + dineroEntity.getDinero();
			
			usuarioEnvio.setTotalDinero(dineroTotal);
			
			usuarioRepository.save(usuarioEnvio);
			
			log.info("FINALIZA ");
			
			responseDTO.setEstatus(GenericasConstantes.OK);
			responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);

		}catch(DataAccessException e ) {
			
			log.info("-");
			log.info("ERROR : " + e.getMessage());
			
			responseDTO.setEstatus(GenericasConstantes.ERROR);
			responseDTO.setCodError(e.getMessage());
			responseDTO.setMensaje(GenericasConstantes.ERROR_INTERNO_DEL_SISTEMA);
			
		}
		
		log.info("-");
		
		return responseDTO;		
		
	}

}
