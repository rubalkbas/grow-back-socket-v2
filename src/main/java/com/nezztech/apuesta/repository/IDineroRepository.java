package com.nezztech.apuesta.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nezztech.apuesta.model.entity.DineroEntity;

public interface IDineroRepository extends JpaRepository<DineroEntity, Long> {
	
	List<DineroEntity> findByAccionAndIdUsuario( String accion, Integer idDinero);
	
	List<DineroEntity> findByAccionAndIdUsuarioAndEstatusRetiro( String accion, Integer idDinero, Integer estatusRetiro ); 
	
	List<DineroEntity> findByAccionAndEstatusRetiro( String accion, Integer estatusRetiro ); 
	
    @Query("SELECT SUM(p.dinero) FROM DineroEntity p WHERE p.accion = 'INGRESO' and p.idUsuario = :idUsuario")
	BigDecimal sumaIngresoIdUsuario(@Param("idUsuario") Integer idUsuario); 
       
    @Query("SELECT SUM(p.dinero) FROM DineroEntity p WHERE p.accion = 'CREDITO' and p.idUsuario = :idUsuario")
	BigDecimal sumaCreditoIdUsuario(@Param("idUsuario") Integer idUsuario);
    
    @Query("SELECT SUM(p.dinero) FROM DineroEntity p WHERE p.accion = 'RETIRO' and estatusRetiro = 1 and p.idUsuario = :idUsuario")
   	BigDecimal sumaIRetiroEfectuadoIdUsuario(@Param("idUsuario") Integer idUsuario);
    
    @Query("SELECT SUM(p.dinero) FROM DineroEntity p WHERE p.accion = 'RETIRO' and estatusRetiro = 0 and p.idUsuario = :idUsuario")
   	BigDecimal sumaIRetiroSolcitadoIdUsuario(@Param("idUsuario") Integer idUsuario);

}
