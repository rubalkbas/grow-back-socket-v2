package com.nezztech.apuesta.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nezztech.apuesta.websocket.model.entity.Apalancamiento;


public interface IApalancamientoRepository extends JpaRepository<Apalancamiento, Long> {
	
	List<Apalancamiento> findByIdUsuario(Long idUsuario ); 

}
