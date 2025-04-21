package com.nezztech.apuesta.websocket.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nezztech.apuesta.websocket.model.entity.HistoricoApuestaClienteEntity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface IHistoricoApuestaClienteRepository extends JpaRepository< HistoricoApuestaClienteEntity , Integer > {
	
	@Query("SELECT SUM(p.montoGanPer) FROM HistoricoApuestaClienteEntity p WHERE  p.idApuestaCliente = :idApuestaCliente")
   	BigDecimal sumaGananciaPerdida(@Param("idApuestaCliente") Integer idApuestaCliente);
	
	 Optional<HistoricoApuestaClienteEntity> findByIdApuestaCliente(Long idApuestaCliente);

}
