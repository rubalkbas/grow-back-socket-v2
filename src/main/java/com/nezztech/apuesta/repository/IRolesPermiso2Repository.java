package com.nezztech.apuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.model.entity.PermisosRoles2Entity;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface IRolesPermiso2Repository extends JpaRepository<PermisosRoles2Entity, Integer> {

	@Transactional
	@Query("SELECT p FROM PermisosRolesEntity p WHERE p.idRol.idRol = :idRol")
	List<PermisosRoles2Entity> findByRol(@Param("idRol") Integer idTicket);

	@Modifying
	@Transactional
	@Query("DELETE FROM PermisosRolesEntity p WHERE p.idRol.idRol = :idRol")
	void deleteByRolId(@Param("idRol") Integer idRol);

}
