package com.nezztech.apuesta.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nezztech.apuesta.model.entity.ApuestaClienteEntity;

public interface IApuestaClienteRepository extends JpaRepository<ApuestaClienteEntity, Long> {	
	
	List<ApuestaClienteEntity> findByestatusCompraAndIdUsuarioOrderByFechaCreacionDesc( String estatusCompra, Integer idUsuario ); 
	
	List<ApuestaClienteEntity> findByestatusCompraAndIdUsuarioOrderByFechaCierreDesc( String estatusCompra, Integer idUsuario ); 
	
	@Query("SELECT SUM(p.gananciaPerdida) FROM ApuestaClienteEntity p WHERE p.idUsuario = :idUsuario")
	BigDecimal sumaGananciasPerdidasApuestas(@Param("idUsuario") Integer idUsuario);

}
