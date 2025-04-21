package com.nezztech.apuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nezztech.apuesta.model.entity.Rol2Entity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface IRol2Repository extends JpaRepository<Rol2Entity, Integer> {

}
