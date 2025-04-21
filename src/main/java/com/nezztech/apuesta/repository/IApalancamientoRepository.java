package com.nezztech.apuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nezztech.apuesta.model.entity.Apalancamiento;

public interface IApalancamientoRepository extends JpaRepository<Apalancamiento, Long> {
	
	List<Apalancamiento> findByIdUsuario(Integer idUsuario ); 

}
