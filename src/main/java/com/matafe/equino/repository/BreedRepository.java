package com.matafe.equino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.matafe.equino.model.Breed;

public interface BreedRepository extends JpaRepository<Breed, Long> {

	List<Breed> findByName(@Param("name") String name);
	
	List<Breed> findByNameIgnoreCase(String name);
	

}
