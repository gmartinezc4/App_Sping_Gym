package com.gimapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimapp.model.BbddUsersClases;

@Repository
public interface IBbddUsersClases extends JpaRepository<BbddUsersClases, Integer>{

}
