package com.gimapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimapp.model.BbddUsersTarifas;

@Repository
public interface IBbddUsersTarifas extends JpaRepository<BbddUsersTarifas, Integer>{

}
