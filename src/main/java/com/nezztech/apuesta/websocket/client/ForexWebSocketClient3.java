package com.nezztech.apuesta.websocket.client;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.websocket.model.entity.Apalancamiento;
import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.model.entity.HistoricoApuestaClienteEntity;
import com.nezztech.apuesta.websocket.repository.HistoryRepository;
import com.nezztech.apuesta.websocket.repository.IApalancamientoRepository;
import com.nezztech.apuesta.websocket.repository.IApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.IHistoricoApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.UsuarioInternoRepository;
import com.nezztech.apuesta.websocket.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ForexWebSocketClient3 extends WebSocketClient {
	
	private final IHistoricoApuestaClienteRepository iHistoricoApuestaClienteRepository;

	private final UsuarioInternoRepository usuarioInternoRepository;

	private ApuestaClienteEntity apuestaClienteEntity;

	private final IApuestaClienteRepository iApuestaClienteRepository;

	private final IApalancamientoRepository iApalancamientoRepository;

	private final UsuarioRepository usuarioRepository;
	
	private final HistoryRepository historyRepository;

	private boolean isReconnecting = false;

	private Double lastValue = null;
	private Double lastValue2 = null;
	private int conteo = 0;
	
	private final AtomicInteger messageCounter = new AtomicInteger(25);
	
	@Value("${porceperdida}")
	private String porceperdida;
	
	 private List<Apalancamiento> listaApalan;
	 
	 private final ReentrantLock lock = new ReentrantLock();

	@Autowired
	public ForexWebSocketClient3(URI uri, IHistoricoApuestaClienteRepository iHistoricoApuestaClienteRepository,
			UsuarioInternoRepository usuarioInternoRepository, IApuestaClienteRepository iApuestaClienteRepository,
			IApalancamientoRepository iApalancamientoRepository, UsuarioRepository usuarioRepository,
			HistoryRepository historyRepository) {
		super(uri);
		this.iHistoricoApuestaClienteRepository = iHistoricoApuestaClienteRepository;
		this.usuarioInternoRepository = usuarioInternoRepository;
		this.iApuestaClienteRepository = iApuestaClienteRepository;
		this.iApalancamientoRepository = iApalancamientoRepository;
		this.usuarioRepository = usuarioRepository;
		this.historyRepository = historyRepository;
		
		this.listaApalan = iApalancamientoRepository.findByIdUsuario(999999);
		
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		log.info("Connected to server: " + getConnection().getRemoteSocketAddress());
		isReconnecting = false; // Reset flag on successful connection
	}

	 private final AtomicInteger mensajeContador = new AtomicInteger(0);
	 
	@Override
	public void onMessage(String message) {

		
		 if (message == null || message.trim().isEmpty()) {
		        log.info("Mensaje vacío recibido, no se procesará.");
		        return; // Si el mensaje está vacío o es nulo, no hacer nada
		    }
		 
		 int count = mensajeContador.incrementAndGet();
	        
	        // Solo procesar cada 30 mensajes
//	        if (count % 5 != 0) {
//	           // log.info("Mensaje ignorado. Contador actual: {}", count);
//	            return;
//	        }
//		
			
			 if (!lock.tryLock()) {
			        log.info("Otra operación está en proceso, esperando...");
			        return; // Si ya hay una operación en curso, ignorar el mensaje
			    }
			
			
			 try {
			    //    log.info("Procesando mensaje: " + message);
			        crearHistoricoApuesta(message);  // Guarda en la base de datos
			    } catch (Exception e) {
			        log.error("Error al procesar el mensaje", e);
			    } finally {
			        lock.unlock(); // Libera el bloqueo después de completar la operación
			    }

	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		log.info("Connection closed: " + reason);
		
		
		
	}


	@Override
	public void onError(Exception ex) {
		log.info("ERROR : " + ex);
	}

	public void sendSubscriptionMessage(String symbol) {
		String subscribeMessage = "{\"action\": \"subscribe\", \"symbols\": \"" + symbol + "\"}";
		send(subscribeMessage);
	}

	@Transactional
	public void crearHistoricoApuesta(String message) {

		HistoricoApuestaClienteEntity historicoApuestaClienteEntity = new HistoricoApuestaClienteEntity();
		
		JSONObject jsonObject = new JSONObject(message);
		
		String jsonString = jsonObject.toString();	
		 
		
		
		if(!jsonString.contains("\"Authorized\"")) {
			
			
			try {
				
				String activo = jsonObject.getString("s");		
				
//				List<Integer> userIds = Arrays.asList(68,268);
//				String estatus = "ABIERTA"; // O el estatus que desees
				List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findByIdUsuarioAndEstatusCompra();
				
				
				//List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findByIdUsuarioAndEstatusCompra(68,"ABIERTA");
//				List<ApuestaClienteEntity> apuestasAbiertas = iApuestaClienteRepository.findByEstatusCompra("ABIERTA");
				
				List<ApuestaClienteEntity> apuestasFiltradas = new ArrayList<>();
				
				
				if(jsonString.contains("\"24hrTicker\"")) {
					
					String resultado = activo.replace("USDT", "").toLowerCase();
					
					apuestasFiltradas = apuestasAbiertas.stream()
						    .filter(apuesta -> resultado.equals(apuesta.getCompra()))
						    .collect(Collectors.toList());
					
					//activo = resultado;
					
				}else {
					
					apuestasFiltradas = apuestasAbiertas.stream()
						    .filter(apuesta -> activo.equals(apuesta.getCompra()))
						    .collect(Collectors.toList());
					
				}
				
				
				
				
				
				if(!apuestasFiltradas.isEmpty()) {
					
					for (ApuestaClienteEntity datos : apuestasFiltradas) {
						
//						log.info("entra a crear posicion ");
//						log.info("activo ...................."+ activo) ;
						
						
						ApuestaClienteEntity apuestaClienteEntity = datos;

			
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// OBTENCION DE DATOS DE LA APUESTA POR SE ACTUALIZA
//					Optional<ApuestaClienteEntity> apu = iApuestaClienteRepository
//							.findById(apuestaClienteEntity.getIdApuestaCliente());
//					ApuestaClienteEntity datos = apu.get();

					String tipoCompra = datos.getTipoCompra();
					Double valorCompraApuesta = datos.getValorUnidad();
					String bloqueCompra = datos.getBloqueCompra();
					Double apuesta = datos.getMontoApuesta();
					Double valorActivo = datos.getValorUnidad();
					String compra2 = datos.getCompra();
					Integer idUsuario = datos.getIdUsuario();
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// log.info(message);
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// OBTENCION DE VALOR DEL SOCKET PARA TENER EL VALOR COMPRA Y VENTA
					

					Double compra = 0.0;
					Double venta = 0.0;
					
					DecimalFormat df = new DecimalFormat("#.####");

					if ("CRIPTO".equals(apuestaClienteEntity.getBloqueCompra())
							|| "FONDOS".equals(apuestaClienteEntity.getBloqueCompra())) {

						compra = jsonObject.getDouble("c");
						venta = jsonObject.getDouble("w");

						

					} else if ("DIVISA".equals(apuestaClienteEntity.getBloqueCompra())
							|| "MATERIAS".equals(apuestaClienteEntity.getBloqueCompra())) {

						compra = jsonObject.getDouble("a");
						venta = jsonObject.getDouble("b");

					} else if ("ACCIONES".equals(apuestaClienteEntity.getBloqueCompra())) {

						compra = jsonObject.getDouble("ap");
						venta = jsonObject.getDouble("bp");

					}
					
					
					
					
					
					compra = Double.valueOf(df.format(compra));
					venta = Double.valueOf(df.format(venta));
					
//					log.info("compra " + compra);
//					log.info("venta " + venta);

					if (lastValue == null) {
						lastValue = compra;
					}
					
					if (lastValue2 == null) {
						lastValue2 = venta;
					}

					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// OBTIENE EL ULTIMO
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// OBTENCION DE VALORES DE APALANCAMIENTO PARA GANANCIA O PERDIDA
					//List<Apalancamiento> listaApalan = iApalancamientoRepository.findByIdUsuario(999999);

					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					Double margenLibre = 0.0;
					double epsilon = 0.00001;

					// validacion si es a compra o venta
					if ("COMPRA".equals(tipoCompra)) {

						historicoApuestaClienteEntity.setValorWebSocket(compra);

						if ("CRIPTO".equals(bloqueCompra) || "MATERIAS".equals(bloqueCompra) || "ACCIONES".equals(bloqueCompra)
								|| "FONDOS".equals(bloqueCompra)) {

							// APUESTA POR EL APALANCAMIENTO DE 5
							Double calculo1 = 0.0;
							Double calculo2 = 0.0;
							Double calculo3 = 0.0;
							Double calculo4 = 0.0;

							if (compra > valorCompraApuesta) {
								// GANA
								historicoApuestaClienteEntity.setGanPer("GANA");

								// validacion donde si llega el valor igual solo gana la primera vez
//								if (Math.abs(lastValue - compra) < epsilon) {
		//
//									return;
		//
//								}

								for (Apalancamiento apalancamiento : listaApalan) {

									if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
										calculo1 = apuesta * apalancamiento.getApalancamiento1Gana();
									}

								}
								
								
								// calculo1 / el precio del activo
								calculo2 = calculo1 / valorActivo;

								calculo3 = compra - valorCompraApuesta;
								calculo4 = calculo3 * calculo2;

								// MONTO GANADO O PERDIDO
								historicoApuestaClienteEntity.setMontoGanPer(calculo4);

								margenLibre = calculo4;
								// usuarioActualizar.setMargenLibre(margenLibre);
								

							} else {
								// PIERDE
								historicoApuestaClienteEntity.setGanPer("PIERDE");
								
								// validacion donde si llega el valor igual solo gana la primera vez
//								if (Math.abs(lastValue - compra) < epsilon) {
		//
//									return;
		//
//								}
								
								for (Apalancamiento apalancamiento : listaApalan) {

									if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
										calculo1 = apuesta * apalancamiento.getApalancamiento1pierde();
									}

								}
								
								
								// calculo1 / el precio del activo
								calculo2 = calculo1 / valorActivo;

								calculo3 = compra - valorCompraApuesta;
								calculo4 = calculo3 * calculo2;

								// MONTO GANADO O PERDIDO
								historicoApuestaClienteEntity.setMontoGanPer(calculo4);

								margenLibre = calculo4;
								// usuarioActualizar.setMargenLibre(margenLibre);
								
							}

							

						} else if ("DIVISA".equals(bloqueCompra)) {

							Double calculo1 = 0.0;
							Double calculo2 = 0.0;
							Double calculo3 = 0.0;
							Double calculo4 = 0.0;

							if (compra2.contains("USD")) {
								// SI INICIA O TERMINA CON USD

								// calculo1 / el precio del activo

								if (compra > valorCompraApuesta) {
									// GANA
									historicoApuestaClienteEntity.setGanPer("GANA");

									// validacion donde si llega el valor igual solo gana la primera vez
//									if (Math.abs(lastValue - compra) < epsilon) {
		//
//										return;
		//
//									}

									for (Apalancamiento apalancamiento : listaApalan) {

										if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
											calculo1 = apuesta * apalancamiento.getApalancamiento1Gana();
											calculo2 = calculo1 / valorActivo;
											calculo3 = compra - valorCompraApuesta;
											calculo4 = (calculo3 * calculo2) * apalancamiento.getApalancamiento2Gana();
										}

									}

								} else {
									// PIERDE
									historicoApuestaClienteEntity.setGanPer("PIERDE");
									
									
									for (Apalancamiento apalancamiento : listaApalan) {

										if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
											calculo1 = apuesta * apalancamiento.getApalancamiento1pierde();
											calculo2 = calculo1 / valorActivo;
											calculo3 = compra - valorCompraApuesta;
											calculo4 = (calculo3 * calculo2) * apalancamiento.getApalancamiento2pierde();
										}

									}

								}

//								DecimalFormat df = new DecimalFormat("#.####");

								// MONTO GANADO O PERDIDO
								historicoApuestaClienteEntity.setMontoGanPer(Double.valueOf(df.format(calculo4)));

								margenLibre = calculo4;
								// usuarioActualizar.setMargenLibre(Double.valueOf(df.format(margenLibre)));

							} else {

								// PENDIENTE

							}

						}

					} else if ("VENTA".equals(tipoCompra)) {

						historicoApuestaClienteEntity.setValorWebSocket(venta);

						if ("CRIPTO".equals(bloqueCompra) || "MATERIAS".equals(bloqueCompra) || "ACCIONES".equals(bloqueCompra)
								|| "FONDOS".equals(bloqueCompra)) {

							// APUESTA POR EL APALANCAMIENTO DE 5
							Double calculo1 = 0.0;
							Double calculo2 = 0.0;
							Double calculo3 = 0.0;
							Double calculo4 = 0.0;

							if (venta < valorCompraApuesta) {
								// GANA
								historicoApuestaClienteEntity.setGanPer("GANA");

								// validacion donde si llega el valor igual solo gana la primera vez
//								if (Math.abs(lastValue2 - venta) < epsilon) {
		//
//									return;
		//
//								}

								for (Apalancamiento apalancamiento : listaApalan) {

									if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
										calculo1 = apuesta * apalancamiento.getApalancamiento1Gana();
									}

								}

							} else {
								// PIERDE
								historicoApuestaClienteEntity.setGanPer("PIERDE");
								
								

								for (Apalancamiento apalancamiento : listaApalan) {

									if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
										calculo1 = apuesta * apalancamiento.getApalancamiento1pierde();
									}

								}

							}

							// calculo1 / el precio del activo
							calculo2 = calculo1 / valorActivo;

							calculo3 = venta - valorCompraApuesta;
							calculo4 = calculo3 * calculo2;

							// MONTO GANADO O PERDIDO
							historicoApuestaClienteEntity.setMontoGanPer(calculo4);

							margenLibre = calculo4;
							// usuarioActualizar.setMargenLibre(margenLibre);

						} else if ("DIVISA".equals(bloqueCompra)) {

							Double calculo1 = 0.0;
							Double calculo2 = 0.0;
							Double calculo3 = 0.0;
							Double calculo4 = 0.0;

							if (compra2.contains("USD")) {
								// SI INICIA O TERMINA CON USD

								if (venta < valorCompraApuesta) {
									// GANA
									historicoApuestaClienteEntity.setGanPer("GANA");

									// validacion donde si llega el valor igual solo gana la primera vez
//									if (Math.abs(lastValue2 - venta) < epsilon) {
		//
//										return;
		//
//									}

									for (Apalancamiento apalancamiento : listaApalan) {

										if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
											calculo1 = apuesta * apalancamiento.getApalancamiento1Gana();
											calculo2 = calculo1 / valorActivo;
											calculo3 = venta - valorCompraApuesta;
											calculo4 = (calculo3 * calculo2) * apalancamiento.getApalancamiento2Gana();
										}

									}

								} else {
									// PIERDE
									historicoApuestaClienteEntity.setGanPer("PIERDE");

									for (Apalancamiento apalancamiento : listaApalan) {
										
										
										if (bloqueCompra.equals(apalancamiento.getSimbolo())) {
											calculo1 = apuesta * apalancamiento.getApalancamiento1pierde();
											calculo2 = calculo1 / valorActivo;
											calculo3 = venta - valorCompraApuesta;
											calculo4 = (calculo3 * calculo2) * apalancamiento.getApalancamiento2pierde();
										}

									}

								}

//								DecimalFormat df = new DecimalFormat("#.####");

								// MONTO GANADO O PERDIDO
								historicoApuestaClienteEntity.setMontoGanPer(-Double.valueOf(df.format(calculo4)));

								margenLibre = calculo4;
								// usuarioActualizar.setMargenLibre(Double.valueOf(df.format(margenLibre)));

							} else {

								// PENDIENTE

							}

						}

					}
					
					//if(validaMargenLibre(apuestaClienteEntity)) {
						
						// GUARDA HISTORICO
						historicoApuestaClienteEntity.setIdApuestaCliente(Integer.valueOf(apuestaClienteEntity.getIdApuestaCliente()));
						historicoApuestaClienteEntity.setValorCompra(valorCompraApuesta);
						
						
						
						Optional<HistoricoApuestaClienteEntity> usuarioRecuperado = iHistoricoApuestaClienteRepository
								.findByIdApuestaCliente(historicoApuestaClienteEntity.getIdApuestaCliente());
						
						
						
										
						if(usuarioRecuperado.isPresent()) {
							
							HistoricoApuestaClienteEntity usuario = usuarioRecuperado.get();
							historicoApuestaClienteEntity.setIdHistApuestaCliente(usuario.getIdHistApuestaCliente());

							
						}
						iHistoricoApuestaClienteRepository.save(historicoApuestaClienteEntity);
						
		
					// se gaurdar el nuevo valor de socket
					lastValue = compra;
					lastValue2 = venta;
					
					}
					
				}			
				

			} catch (DataAccessException e) {

				log.info("-");
				log.info("ERROR : " + e.getMessage());

			}catch (Exception e) {
			    log.error("Error al procesar apuesta: " + e.getMessage(), e);
			    throw e;  // Lanza la excepción para que Spring haga rollback
			}

			
		}else {
			log.info("inicio conexion ");			
		}


		
	}
	
	




	public void setApuestaClienteEntity(ApuestaClienteEntity apuestaClienteEntity) {
		this.apuestaClienteEntity = apuestaClienteEntity;
	}

}
