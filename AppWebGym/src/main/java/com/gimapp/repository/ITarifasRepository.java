package com.gimapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimapp.model.Tarifas;

@Repository
public interface ITarifasRepository extends JpaRepository<Tarifas, Integer>{

}
