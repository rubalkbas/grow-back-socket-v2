package com.nezztech.apuesta.websocket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nezztech.apuesta.websocket.client.ForexWebSocketClient;
import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.service.IWebSocKetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/websocket")
@Slf4j
public class WebSocKetController {
	
	
	/**  */
	@Autowired
	private IWebSocKetService iWebSocKetService;
	


//	    @PostMapping("/start")
//	    public String startConnection( @RequestBody ApuestaClienteEntity apuestaClienteEntity) {
//	    	iWebSocKetService.startWebSocketConnection( apuestaClienteEntity);
//	        return "WebSocket connection started and subscribed to " + apuestaClienteEntity.getCompra() + " with clientId " + apuestaClienteEntity.getIdApuestaCliente();
//	    }
	    
	    @PostMapping(path = "/start2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> crearApuestas( @RequestBody ApuestaClienteEntity apuestaClienteEntity ) {
			
			log.info("-");
			log.info("INICIA  !!!");
			
			//iWebSocKetService.stopAllConnectionMaestro( );	
			
			log.info("TERMINA !!!");
			log.info("-");
			
			return new ResponseEntity<>("INICIO EXITOSO", HttpStatus.OK);
			
		}

//	    @GetMapping("/stop")
//	    public String stopConnections() {
//	    	iWebSocKetService.stopAllConnections();
//	        return "All WebSocket connections stopped";
//	    }
//
//	    @GetMapping("/stopClient")
//	    public String stopConnection(@RequestParam String clientId) {
//	    	iWebSocKetService.stopConnection(clientId);
//	        return "WebSocket connection with clientId " + clientId + " stopped";
//	    }
//	    
//		@GetMapping(path = "/stopClient2")
//		public ResponseEntity<String> consultaAllRetirosSolicitados(@RequestParam String clientId) {
//			
//			log.info("-");
//			log.info("INICIA  !!!");
//			
//			iWebSocKetService.stopConnection(clientId);
//			
//			log.info("TERMINA !!!");
//			log.info("-");
//			
//			return new ResponseEntity<>("WebSocket connection with clientId " + clientId + " stopped", HttpStatus.OK);
//			
//		}

//	    @GetMapping("/getClient")
//	    public String getClient(@RequestParam String clientId) {
//	        ForexWebSocketClient client = iWebSocKetService.getClient(clientId);
//	        if (client != null) {
//	            return "WebSocket client found: " + client.getConnection().getRemoteSocketAddress();
//	        } else {
//	            return "WebSocket client not found with clientId: " + clientId;
//	        }
//	    }


}
