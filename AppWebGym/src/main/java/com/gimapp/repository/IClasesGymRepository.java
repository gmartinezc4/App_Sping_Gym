package com.gimapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimapp.model.ClasesGym;
import com.gimapp.model.DatosBancarios;

@Repository
public interface IClasesGymRepository extends JpaRepository<ClasesGym, Integer> {

}
