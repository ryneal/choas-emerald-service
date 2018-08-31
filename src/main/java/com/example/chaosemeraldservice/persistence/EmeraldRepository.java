package com.example.chaosemeraldservice.persistence;

import com.example.chaosemeraldservice.model.Emerald;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmeraldRepository extends CrudRepository<Emerald, Long> { }
