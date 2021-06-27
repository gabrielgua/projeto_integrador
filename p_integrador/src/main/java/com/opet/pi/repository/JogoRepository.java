package com.opet.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opet.pi.model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{

}
