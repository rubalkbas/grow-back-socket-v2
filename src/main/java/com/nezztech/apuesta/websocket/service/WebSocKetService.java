package com.nezztech.apuesta.websocket.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.websocket.client.ForexWebSocketClient;
import com.nezztech.apuesta.websocket.client.ForexWebSocketClient2;
import com.nezztech.apuesta.websocket.client.ForexWebSocketClient3;
import com.nezztech.apuesta.websocket.client.ForexWebSocketClient4;
import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.repository.HistoryRepository;
import com.nezztech.apuesta.websocket.repository.IApalancamientoRepository;
import com.nezztech.apuesta.websocket.repository.IApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.IHistoricoApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.UsuarioInternoRepository;
import com.nezztech.apuesta.websocket.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class WebSocKetService implements IWebSocKetService {

	 @Autowired
	    private IHistoricoApuestaClienteRepository iHistoricoApuestaClienteRepository;
	 
	 @Autowired
	    private UsuarioInternoRepository usuarioInternoRepository;

	    @Autowired
	    private IApuestaClienteRepository iApuestaClienteRepository;

	    @Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    @Autowired
	    private IApalancamientoRepository iApalancamientoRepository;
	    
	    @Autowired
	    private HistoryRepository historyRepository;

	    @Value("${websocket.url}")
	    private String url;

	    @Value("${websocket.url2}")
	    private String url2;

	    private ForexWebSocketClient accionesClient;
	    private ForexWebSocketClient4 materiasClient;
	    private ForexWebSocketClient3 divisasClient;
	    
	    private ScheduledExecutorService executorService;
	    private ScheduledExecutorService executorService3;
	    private ScheduledExecutorService executorService4;
	    
	    
	    private final Queue<String> accionesQueueDinamica = new LinkedList<>();
	    private final Queue<String> divisasQueueDinamica = new LinkedList<>();
	    private final Queue<String> materiasQueueDinamica = new LinkedList<>();
	    
	    private ConcurrentHashMap<String, ForexWebSocketClient2> clients;


	    @PostConstruct
	    public void init() {
	    	
	    	inicaListas() ;
	    	
	    	clients = new ConcurrentHashMap<>();
	    	
	    	 executorService = Executors.newScheduledThreadPool(50);; // 2 hilos para ejecutar en paralelo
	    	 executorService.execute(this::startAccionesWebSocket);
	    	
	    	executorService3 = Executors.newScheduledThreadPool(50); // 2 hilos para ejecutar en paralelo
	    	 executorService3.execute(this::startDivisasWebSocket);
	    	
	    	 executorService4 = Executors.newScheduledThreadPool(50); // 2 hilos para ejecutar en paralelo
	    	 executorService4.execute(this::startMateriasWebSocket);
	    	
	    	iniciarWebSocketsParaApuestasAbiertas();

	 		
	    }
	    
	    
	    public void inicaListas() {

	    	List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findApuestasAbiertasCriptoFondos();

	    	// Usar conjuntos (Set) para evitar duplicados
	        Set<String> accionesSet = new HashSet<>();
	        Set<String> divisasSet = new HashSet<>();
	        Set<String> materiasSet = new HashSet<>();

	        for (ApuestaClienteEntity apuesta : apuestasAbiertas) {
	            if (esAccion(apuesta.getBloqueCompra())) {
	                accionesSet.add(apuesta.getCompra());
	            } else if (esDivisa(apuesta.getBloqueCompra())) {
	                divisasSet.add(apuesta.getCompra());
	            } else if (esMateria(apuesta.getBloqueCompra())) {
	                materiasSet.add(apuesta.getCompra());
	            }
	        }

	        // Limpiar las colas para evitar datos antiguos
	        this.accionesQueueDinamica.clear();
	        this.divisasQueueDinamica.clear();
	        this.materiasQueueDinamica.clear();

	        // Agregar los valores únicos a las colas
	        this.accionesQueueDinamica.addAll(accionesSet);
	        this.divisasQueueDinamica.addAll(divisasSet);
	        this.materiasQueueDinamica.addAll(materiasSet);

	        log.info("Lista de Acciones: {}", this.accionesQueueDinamica);
	        log.info("Lista de Divisas: {}", this.divisasQueueDinamica);
	        log.info("Lista de Materias: {}", this.materiasQueueDinamica);
	    }

	private boolean esAccion(String bloqueCompra) {
	    return "ACCIONES".equalsIgnoreCase(bloqueCompra);
	}
	
	private boolean esDivisa(String bloqueCompra) {
	    return "DIVISA".equalsIgnoreCase(bloqueCompra);
	}
	
	private boolean esMateria(String bloqueCompra) {
	    return "MATERIAS".equalsIgnoreCase(bloqueCompra);
	}
	
	private boolean esCriptoOFondos(ApuestaClienteEntity apuesta) {
	    return "CRIPTO".equalsIgnoreCase(apuesta.getBloqueCompra()) || "FONDOS".equalsIgnoreCase(apuesta.getBloqueCompra());
	}
	
	
	private void startMateriasWebSocket() {
        try {
            URI uri = new URI(url);
            if (materiasClient == null || !materiasClient.isOpen()) {
            	materiasClient = new ForexWebSocketClient4(uri, iHistoricoApuestaClienteRepository, usuarioInternoRepository, iApuestaClienteRepository, iApalancamientoRepository, usuarioRepository, historyRepository);
            	materiasClient.connect();
            	
//            	int retryCount = 0;
//                while (!materiasClient.isOpen() && retryCount < 30) {
//                    Thread.sleep(100);
//                    retryCount++;
//                }
            	
            	CompletableFuture.runAsync(() -> {
                    int retryCount = 0;
                    while (!materiasClient.isOpen() && retryCount < 30) {
                        try {
                            Thread.sleep(100);
                            retryCount++;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.error("Error en reconexión", e);
                            return;
                        }
                    }
                });
            	
                scheduleSymbolSending4(materiasClient, materiasQueueDinamica);
               // divisasClient.sendSubscriptionMessage(SYMBOLS2); // Suscribe símbolos de Forex
            }
        } catch (URISyntaxException e) {
            log.error("Error iniciando el tercer WebSocket", e);
        }
    }
	
	
	private void scheduleSymbolSending4(ForexWebSocketClient4 client, Queue<String> symbolQueue) {
        executorService4.scheduleAtFixedRate(() -> {
            try {
            	if (!symbolQueue.isEmpty()) {
                    String symbols = String.join(",", symbolQueue); // Unir los símbolos en un solo mensaje
                    client.sendSubscriptionMessage(symbols);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                System.err.println("Hilo interrumpido: " + e.getMessage());
            }
        	
            
        	
        }, 0, 1, TimeUnit.SECONDS);
    }
	
	
	
	private void startDivisasWebSocket() {
        try {
            URI uri = new URI(url);
            if (divisasClient == null || !divisasClient.isOpen()) {
            	divisasClient = new ForexWebSocketClient3(uri, iHistoricoApuestaClienteRepository, usuarioInternoRepository, iApuestaClienteRepository, iApalancamientoRepository, usuarioRepository, historyRepository);
            	divisasClient.connect();
            	
//            	int retryCount = 0;
//                while (!divisasClient.isOpen() && retryCount < 30) {
//                    Thread.sleep(100);
//                    retryCount++;
//                }
            	
            	CompletableFuture.runAsync(() -> {
                    int retryCount = 0;
                    while (!divisasClient.isOpen() && retryCount < 30) {
                        try {
                            Thread.sleep(100);
                            retryCount++;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.error("Error en reconexión", e);
                            return;
                        }
                    }
                });
            	
                scheduleSymbolSending3(divisasClient, divisasQueueDinamica);
               // divisasClient.sendSubscriptionMessage(SYMBOLS2); // Suscribe símbolos de Forex
            }
        } catch (URISyntaxException e) {
            log.error("Error iniciando el segundo WebSocket", e);
        }
    }
	
	
	private void scheduleSymbolSending3(ForexWebSocketClient3 client, Queue<String> symbolQueue) {
        executorService3.scheduleAtFixedRate(() -> {
            try {
            	if (!symbolQueue.isEmpty()) {
                    String symbols = String.join(",", symbolQueue); // Unir los símbolos en un solo mensaje
                    client.sendSubscriptionMessage(symbols);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                System.err.println("Hilo interrumpido: " + e.getMessage());
            }
        	
            
        	
        }, 0, 1, TimeUnit.SECONDS);
    }
	
	
	
	private void startAccionesWebSocket() {
        try {
            URI uri = new URI(url2);
            if (accionesClient == null || !accionesClient.isOpen()) {
                accionesClient = new ForexWebSocketClient(uri, iHistoricoApuestaClienteRepository, usuarioInternoRepository, iApuestaClienteRepository, iApalancamientoRepository, usuarioRepository, historyRepository);
                accionesClient.connect();
//                int retryCount = 0;
//                while (!accionesClient.isOpen() && retryCount < 30) {
//                    Thread.sleep(100);
//                    retryCount++;
//                }
                CompletableFuture.runAsync(() -> {
                    int retryCount = 0;
                    while (!accionesClient.isOpen() && retryCount < 30) {
                        try {
                            Thread.sleep(100);
                            retryCount++;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.error("Error en reconexión", e);
                            return;
                        }
                    }
                });
                scheduleSymbolSending(accionesClient, accionesQueueDinamica);
               // accionesClient.sendSubscriptionMessage(SYMBOLS);
            }
        } catch (URISyntaxException e) {
            log.error("Error iniciando WebSocket de acciones", e);
        }
    }
	
	
	private void scheduleSymbolSending(ForexWebSocketClient client, Queue<String> symbolQueue) {
        executorService.scheduleAtFixedRate(() -> {
            try {
            	if (!symbolQueue.isEmpty()) {
                    String symbols = String.join(",", symbolQueue); // Unir los símbolos en un solo mensaje
                    client.sendSubscriptionMessage(symbols);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                System.err.println("Hilo interrumpido: " + e.getMessage());
            }
        	
            
        	
        }, 0, 1, TimeUnit.SECONDS);
    }
	
	
	
	private void iniciarWebSocketsParaApuestasAbiertas() {
    	
    	//List<Integer> userIds = Arrays.asList(68,268);
    	
    	List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findApuestasAbiertasCriptoFondos();
       // List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findApuestasAbiertasCriptoFondos();

        for (ApuestaClienteEntity apuesta : apuestasAbiertas) {
            if (esCriptoOFondos(apuesta)) {
                CompletableFuture.runAsync(() -> {
                    try {
                        abrirWebSocketParaApuesta(apuesta);
                    } catch (URISyntaxException | InterruptedException e) {
                        log.error("Error en WebSocket para apuesta ID: {}", apuesta.getIdApuestaCliente(), e);
                    }
                });
            } 
        }
    }
	
	
	
	private void abrirWebSocketParaApuesta(ApuestaClienteEntity apuesta) throws URISyntaxException, InterruptedException {
        String binanceUrl = "wss://stream.binance.com:9443/ws/" + apuesta.getCompra() + "usdt@ticker";
        URI uri = new URI(binanceUrl);
        
        ForexWebSocketClient2 client = new ForexWebSocketClient2(uri, 
            iHistoricoApuestaClienteRepository, usuarioInternoRepository, 
            iApuestaClienteRepository, iApalancamientoRepository, 
            usuarioRepository, historyRepository);

        client.setApuestaClienteEntity(apuesta);
        client.connect();

        while (!client.isOpen()) {
        	try {
        	    Thread.sleep(100);
        	} catch (InterruptedException e) {
        	    Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
        	    //log.error("El hilo fue interrumpido mientras dormía");
        	}
        }

        clients.put(String.valueOf(apuesta.getIdApuestaCliente()), client);
        //log.info("✅ WebSocket abierto para apuesta ID: " + apuesta.getIdApuestaCliente());
    }
    
	
	
	
	@Scheduled(fixedRate = 60000)
    public void restartAllConnections() {
        log.info("INICIA RECONEXION AUTOMATICA PAGA");

        stopAllConnections(); // Cierra todas las conexiones activas correctamente

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DayOfWeek diaActual = fechaHoraActual.getDayOfWeek();
        LocalTime horaActual = fechaHoraActual.toLocalTime();

        LocalTime horaRestriccionInicio = LocalTime.of(17, 0); // 5:00 PM
        LocalTime horaRestriccionFin = LocalTime.of(8, 0); // 8:00 AM

        if ((diaActual == DayOfWeek.FRIDAY && horaActual.isAfter(horaRestriccionInicio))
                || diaActual == DayOfWeek.SATURDAY || diaActual == DayOfWeek.SUNDAY
                || (diaActual == DayOfWeek.MONDAY && horaActual.isBefore(horaRestriccionFin))) {
            
            log.info("PROCESO EN PAUSA: Desde Viernes 5:00 PM hasta Lunes 8:00 AM.");
            return; 
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("ERROR PARA REINICIO: ", e);
            Thread.currentThread().interrupt();
        }

        inicaListas();

        executorService = Executors.newScheduledThreadPool(50); // 2 hilos para ejecutar en paralelo
   	    executorService.execute(this::startAccionesWebSocket);
   	
	    executorService3 = Executors.newScheduledThreadPool(50); // 2 hilos para ejecutar en paralelo
	   	executorService3.execute(this::startDivisasWebSocket);
	   	
	   	 executorService4 = Executors.newScheduledThreadPool(50); // 2 hilos para ejecutar en paralelo
	   	 executorService4.execute(this::startMateriasWebSocket);

        log.info("TERMINA RECONEXION AUTOMATICA PAGA");
    }
	
	

    public void stopAllConnections() {
        log.info("⛔ Cerrando conexiones WebSocket...");
       

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        
        if (executorService3 != null && !executorService3.isShutdown()) {
            executorService3.shutdownNow();
        }
        
        if (executorService4 != null && !executorService4.isShutdown()) {
            executorService4.shutdownNow();
        }
        
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("🔴 Executor no terminó correctamente. Forzando cierre...");
            }
            if (!executorService3.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("🔴 Executor no terminó correctamente. Forzando cierre...");
            }
            if (!executorService4.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("🔴 Executor no terminó correctamente. Forzando cierre...");
            }
        } catch (InterruptedException e) {
            log.error("🔴 Error al detener el ExecutorService", e);
            Thread.currentThread().interrupt();
        }

       
    }
    
    
    
    @Scheduled(fixedRate = 30000)
    public void restartAllConnections2() {
        log.info("INICIA RECONEXION AUTOMATICA GRATIS");

        stopAllConnections2(); // Cierra todas las conexiones activas correctamente

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DayOfWeek diaActual = fechaHoraActual.getDayOfWeek();
        LocalTime horaActual = fechaHoraActual.toLocalTime();

        LocalTime horaRestriccionInicio = LocalTime.of(17, 0); // 5:00 PM
        LocalTime horaRestriccionFin = LocalTime.of(8, 0); // 8:00 AM

        if ((diaActual == DayOfWeek.FRIDAY && horaActual.isAfter(horaRestriccionInicio))
                || diaActual == DayOfWeek.SATURDAY || diaActual == DayOfWeek.SUNDAY
                || (diaActual == DayOfWeek.MONDAY && horaActual.isBefore(horaRestriccionFin))) {
            
            log.info("PROCESO EN PAUSA: Desde Viernes 5:00 PM hasta Lunes 8:00 AM.");
            return; 
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("ERROR PARA REINICIO: ", e);
            Thread.currentThread().interrupt();
        }


        clients = new ConcurrentHashMap<>();

        iniciarWebSocketsParaApuestasAbiertas();

        log.info("TERMINA RECONEXION AUTOMATICA GRATIS ");
    }
    
    
    public void stopAllConnections2() {
        log.info("⛔ Cerrando conexiones WebSocket...");

        clients.values().forEach(client -> {
            try {
                client.close();
            } catch (Exception e) {
                log.error("Error al cerrar WebSocket", e);
            }
        });

        // Limpiar el mapa de clientes
        clients.clear();


    }
    
    
//    @Autowired
//    public void stopAllConnectionMaestro() {
//
//    	stopAllConnections();
//    	restartAllConnections2();
//    	
//       
//    }
//    
	

	    
}
