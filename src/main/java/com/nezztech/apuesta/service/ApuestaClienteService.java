package com.nezztech.apuesta.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.nezztech.apuesta.model.dto.ResponseDTO;
import com.nezztech.apuesta.model.entity.Apalancamiento;
import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.model.entity.HistoricoApuestaClienteEntity;
import com.nezztech.apuesta.notificacion.service.NotificacionCorreoService;
import com.nezztech.apuesta.repository.IApalancamientoRepository;
import com.nezztech.apuesta.repository.IApuestaClienteRepository;
import com.nezztech.apuesta.repository.IHistoricoApuestaClienteRepository;
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
public class ApuestaClienteService implements IApuestaClienteService {
	
	/** Repository */
	@Autowired
	private IApuestaClienteRepository iApuestaClienteRepository;
	
	/** Repository */
	@Autowired
	private IHistoricoApuestaClienteRepository iHistoricoApuestaClienteRepository;	
	
	/** Repository */
	@Autowired
	private IApalancamientoRepository iApalancamientoRepository;
	
	@Autowired
	UsuarioInternoRepository usuarioRepository;
	
	/** CORREO */
	@Autowired
	private NotificacionCorreoService notificacionCorreoService;
	
	
	
		
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
	public ResponseDTO consultaApuestasAbiertasClienteID( ApuestaClienteEntity apuestaClienteEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<ApuestaClienteEntity> lista = new ArrayList<>();
		
		try {			
						
			log.info("-");
						
			lista = iApuestaClienteRepository.findByestatusCompraAndIdUsuarioOrderByFechaCreacionDesc( "ABIERTA" , apuestaClienteEntity.getIdUsuario() );
			
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
	public ResponseDTO consultaApuestasCerradasClienteID( ApuestaClienteEntity apuestaClienteEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<ApuestaClienteEntity> lista = new ArrayList<>();
		
		try {
			
			log.info("-");
						
			lista = iApuestaClienteRepository.findByestatusCompraAndIdUsuarioOrderByFechaCierreDesc( "CERRADA" , apuestaClienteEntity.getIdUsuario() );
			
			if(lista.isEmpty()) {
				
				responseDTO.setEstatus(GenericasConstantes.EMPTY);
				responseDTO.setMensaje(GenericasConstantes.NO_HAY_DATOS);
				
			} else {
				
				responseDTO.setLista(lista);
				
				BigDecimal total = iApuestaClienteRepository.sumaGananciasPerdidasApuestas(apuestaClienteEntity.getIdUsuario());
				
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
	public ResponseDTO crearApuestas( ApuestaClienteEntity apuesta ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
	
				
		try {

			log.info("-");
			
			apuesta.setEstatusCompra("ABIERTA");
			apuesta.setFechaCreacion(new Date());
			apuesta.setGananciaPerdida(0.0);
			apuesta.setFechaCierre(null);
			
			
			//obtiee datos del usuario 
			Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(apuesta.getIdUsuario());
			UsuarioInterno usuario = usuarioRecuperado.get();
			
			Double balance = usuario.getTotalDinero();
			Double margenLibre = usuario.getMargenLibre();
			Double margen = usuario.getMargen();
			
			Double porcentaje = 0.15;
			
			Double treintaPorciento = balance * porcentaje;
			
			Double sobrante = balance-apuesta.getMontoApuesta();
			
			if(sobrante < treintaPorciento) { 
				
				responseDTO.setEstatus("FALTA");
				responseDTO.setMensaje(GenericasConstantes.CONSULTA_EXITOSA);
				
			}else {
				
				//guarda apuesta
				ApuestaClienteEntity apuestaAbierta = iApuestaClienteRepository.save(apuesta);			
				
				
				margenLibre = margenLibre - apuestaAbierta.getMontoApuesta();			
				margen = margen + apuestaAbierta.getMontoApuesta();

				log.info("margenLibre inicio: " + margenLibre);
				log.info("margen inicio: " + margen);
				
				usuario.setMargenLibre(margenLibre);
				usuario.setMargen(margen);
				
				//actualiza margen libre y margen
				UsuarioInterno user =usuarioRepository.save(usuario);
				
				log.info("margenLibre final: " + user.getMargenLibre());
				log.info("margen final: " + user.getMargen());
				
				//enviar correo
//				String cuerpo = "Se abrio una apuesta";
//				String info = "Aposto a la "+apuestaAbierta.getTipoCompra()+" en " +apuestaAbierta.getBloqueCompra() + " a : " + apuestaAbierta.getCompra() +
//						      " , unidades : " +apuestaAbierta.getUnidades() +". Monto de $ " + apuestaAbierta.getMontoApuesta(); 
				
				HistoricoApuestaClienteEntity historicoApuestaClienteEntity = new HistoricoApuestaClienteEntity();
				historicoApuestaClienteEntity.setIdApuestaCliente(apuestaAbierta.getIdApuestaCliente());
				historicoApuestaClienteEntity.setMontoGanPer(0.0);
				historicoApuestaClienteEntity.setGanPer("PIERDE");
				historicoApuestaClienteEntity.setValorCompra(apuestaAbierta.getValorUnidad());
				historicoApuestaClienteEntity.setValorWebSocket(apuestaAbierta.getValorUnidad());
				
				iHistoricoApuestaClienteRepository.save(historicoApuestaClienteEntity);
				
				//startConnection(apuestaAbierta);
				
				//notificacionCorreoService.envioCorreo(apuestaAbierta.getIdUsuario(), cuerpo, info);
				
				responseDTO.setDto(apuestaAbierta);
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.ALTA_EXITOSA);

				
			}
			

			log.info("FINALIZA ");

		}catch(DataAccessException  e){//| MessagingException | IOException e ) {
			
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
	public ResponseDTO cerrarApuesta( ApuestaClienteEntity apuestaClienteEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
				
		try {
			
			log.info("-");			
			
			apuestaClienteEntity.setEstatusCompra("CERRADA");
			apuestaClienteEntity.setFechaCierre(new Date());
			
			ApuestaClienteEntity cierre = iApuestaClienteRepository.save(apuestaClienteEntity);
			
			actualizaMontos(cierre.getGananciaPerdida(), cierre);
			
			//String cuerpo = "Se cerro una apuesta";
			//String info = "La ganancia o perdida fue de : " + gan.toString(); 
			
			//notificacionCorreoService.envioCorreo(cierre.getIdUsuario(), cuerpo, info);
			
			responseDTO.setEstatus(GenericasConstantes.OK);
			responseDTO.setMensaje("POSICION CERRADA CORRECTAMENTE");
		
			log.info("FINALIZA ");

		}catch(DataAccessException e ) { //| MessagingException | IOException e ) {
			
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
	private void actualizaMontos(Double gan,ApuestaClienteEntity apuestaClienteEntity) {
		
		//actualizar balance, margen libre y margen
		Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(apuestaClienteEntity.getIdUsuario());
		UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
		
		Double balance = usuarioEnvio.getTotalDinero();
		Double marLib = usuarioEnvio.getMargenLibre();
		Double mar = usuarioEnvio.getMargen();
		
		//ctualizamargen
		Double newMar = mar - apuestaClienteEntity.getMontoApuesta();
		
		//actualiza margen libre
		Double newmarLib = marLib + apuestaClienteEntity.getMontoApuesta() + gan;			
		
		//actualiza balance
		Double newbalance = balance + gan ;
			
		//se actualiza balance
		usuarioEnvio.setTotalDinero(newbalance);
		//se actuliza margen libre
		usuarioEnvio.setMargenLibre(newmarLib);			
		//se actuliza margen
		usuarioEnvio.setMargen(newMar);
		
		UsuarioInterno actualizado = usuarioRepository.save(usuarioEnvio);
				
	}
	
	
	@Autowired
    private RestTemplate restTemplate;
	
	public String startConnection(ApuestaClienteEntity apuestaClienteEntity) {
		
        //String url = "http://localhost:8093/websocket/start" ;
        String url = "https://app.growingcapitalmaker.org:8093/websocket/start" ;
        
        log.info("url : " +url);
        
        return restTemplate.postForObject(url,apuestaClienteEntity, String.class);
    }
	
	
	public String cerrarConexionWebsocket(Integer idApuestaCliente) {
		
        //String url = "http://localhost:8093/websocket/stopClient?clientId="+idApuestaCliente;
        String url = "https://app.growingcapitalmaker.org:8093/websocket/stopClient?clientId="+idApuestaCliente;
        
        log.info("url : " +url);
        
        return restTemplate.getForObject(url, String.class);
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
	public ResponseDTO consultaApuestasHistoricoAbiertasClienteID( HistoricoApuestaClienteEntity historicoApuestaClienteEntity ) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		List<HistoricoApuestaClienteEntity> lista = new ArrayList<>();
		
		try {			
						
			log.info("-");
						
			lista = iHistoricoApuestaClienteRepository.findByIdApuestaCliente(  historicoApuestaClienteEntity.getIdApuestaCliente() );
			
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
		public ResponseDTO actualizarApuesta( ApuestaClienteEntity apuestaClienteEntity ) {
			
			ResponseDTO responseDTO = new ResponseDTO();
					
			try {
				
				log.info("-");			
							
				iApuestaClienteRepository.save(apuestaClienteEntity);
	
				log.info("FINALIZA ");
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.ACTUALIZACION_EXITOSA);

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
		
		@Override
		public ResponseDTO consultaApalancamientoClienteID( Apalancamiento apalancamiento ) {
			
			ResponseDTO responseDTO = new ResponseDTO();
			
			List<Apalancamiento> lista = new ArrayList<>();
			
			try {
				
				log.info("-");
							
				lista = iApalancamientoRepository.findByIdUsuario(apalancamiento.getIdUsuario());
				
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
		public ResponseDTO actualizarApalancamiento( Apalancamiento apalancamiento ) {
			
			ResponseDTO responseDTO = new ResponseDTO();
					
			try {
				
				log.info("-");			
							
				iApalancamientoRepository.save(apalancamiento);
	
				log.info("FINALIZA ");
				
				responseDTO.setEstatus(GenericasConstantes.OK);
				responseDTO.setMensaje(GenericasConstantes.ACTUALIZACION_EXITOSA);

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
