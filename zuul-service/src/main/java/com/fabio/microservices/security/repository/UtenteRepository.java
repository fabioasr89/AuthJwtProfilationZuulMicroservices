package com.fabio.microservices.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabio.microservices.security.model.AuthUser;
@Repository
public interface UtenteRepository extends JpaRepository<AuthUser,String>{
	
	
}
