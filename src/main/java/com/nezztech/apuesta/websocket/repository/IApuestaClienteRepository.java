package com.nezztech.apuesta.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;

public interface IApuestaClienteRepository extends JpaRepository<ApuestaClienteEntity, Integer> {		
	
	List<ApuestaClienteEntity> findByestatusCompra( String estatusCompra ); 
	
	List<ApuestaClienteEntity> findByestatusCompraAndIdUsuarioOrderByFechaCreacionDesc( String estatusCompra, Integer idUsuario );
	
//	List<ApuestaClienteEntity> findByEstatusCompra( String estatusCompra); 
	
//	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.estatusCompra = 'ABIERTA'")
//	List<ApuestaClienteEntity> findApuestasAbiertasCriptoFondos();
	
	//List<ApuestaClienteEntity> findByIdUsuarioAndEstatusCompra( Integer idUsuario , String estatusCompra); 
	
//	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.idUsuario IN :idsUsuario AND a.estatusCompra = :estatusCompra ")
//	List<ApuestaClienteEntity> findByIdUsuarioAndEstatusCompra(@Param("idsUsuario") List<Integer> idsUsuario, @Param("estatusCompra") String estatusCompra);

	
//	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.idUsuario = 68 AND a.estatusCompra = 'ABIERTA' ")
//	List<ApuestaClienteEntity> findApuestasAbiertasCriptoFondos();
	
//	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.idUsuario IN :idsUsuario AND a.estatusCompra = 'ABIERTA'")
//	List<ApuestaClienteEntity> findApuestasAbiertasCriptoFondos(@Param("idsUsuario") List<Integer> idsUsuario);
//

	
	
	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.estatusCompra = 'ABIERTA' ")
	List<ApuestaClienteEntity> findByIdUsuarioAndEstatusCompra( );
	
	@Query("SELECT a FROM ApuestaClienteEntity a WHERE a.estatusCompra = 'ABIERTA'")
	List<ApuestaClienteEntity> findApuestasAbiertasCriptoFondos();
	
}
