package com.nezztech.apuesta.security.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nezztech.apuesta.security.entity.RolEntity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface IRolRepository extends JpaRepository<RolEntity, Integer> {

}
