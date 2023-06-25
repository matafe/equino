package com.matafe.equino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matafe.equino.model.Animal;
import com.matafe.equino.model.Owner;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

	@Query("select a from Animal a inner join fetch a.owner o")
	List<Animal> fetchAll();
	
	List<Animal> findByOwner(Owner owner);

	@Query("select a from Animal a inner join fetch a.owner o where o = :owner")
	List<Animal> fetchByOwner(@Param("owner") Owner owner);

}
