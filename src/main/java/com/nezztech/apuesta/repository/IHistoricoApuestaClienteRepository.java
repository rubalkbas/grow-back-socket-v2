package com.nezztech.apuesta.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.model.entity.HistoricoApuestaClienteEntity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface IHistoricoApuestaClienteRepository extends JpaRepository< HistoricoApuestaClienteEntity , Long > {
	
	List<HistoricoApuestaClienteEntity> findByIdApuestaCliente(  Integer idApuestaCliente ); 

	@Query("SELECT SUM(p.montoGanPer) FROM HistoricoApuestaClienteEntity p WHERE  p.idApuestaCliente = :idApuestaCliente")
   	BigDecimal sumaGananciaPerdida(@Param("idApuestaCliente") Integer idApuestaCliente);
}
