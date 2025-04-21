package com.nezztech.apuesta.websocket.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.model.entity.HistoricoApuestaClienteEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HistoryRepository {

	@Autowired
	public EntityManager entityManager;	

	private static final String SQL_USUARIO_ACTUALIZA_CONSTRASENA = "UPDATE internanueva.historico_apuesta_cliente \r\n"
			+ "			SET valor_compra = :valorCompra, valor_web_socket = :valorWebSocket, gan_per = :ganPer, monto_gan_per = :montoGanPer\r\n"
			+ "				WHERE id_hist_apuest_cli = :idHistApuestaCliente";
	
	@Transactional
	public void actualizaHistorico( HistoricoApuestaClienteEntity hist ) {
		
		String sql = SQL_USUARIO_ACTUALIZA_CONSTRASENA;
		
		Query query = entityManager.createNativeQuery(sql);		
		query.setParameter( "valorCompra", hist.getValorCompra() );
		query.setParameter( "valorWebSocket",  hist.getValorWebSocket());
		query.setParameter( "ganPer",  hist.getGanPer() );
		query.setParameter( "montoGanPer", hist.getMontoGanPer() );
		
		query.setParameter( "idHistApuestaCliente", hist.getIdHistApuestaCliente() );
				
		int resp = 0;
		
		try {
			resp = query.executeUpdate();
		} catch(javax.persistence.NoResultException e ) {
			log.info("Registro no actualizado: ", e.getMessage());
		}
		
		//return resp;
	}
}
