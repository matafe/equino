package com.matafe.equino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.matafe.equino.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

	List<Owner> findByCpf(@Param("cpf") String cpf);

	List<Owner> findByEmailIgnoreCase(@Param("email") String email);
//	@Query("select p from Pedido p join p.user u where u.username = :username")
//	List<Pedido> findAllByUsuario(@Param("username") String username);
//
//	@Query("select p from Pedido p join p.user u where u.username = :username and p.status = :status")
//	List<Pedido> findByStatusEUsuario(@Param("status") StatusPedido status, @Param("username") String username);

}
