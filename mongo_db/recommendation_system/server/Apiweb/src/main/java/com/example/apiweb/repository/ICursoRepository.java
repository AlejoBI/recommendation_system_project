package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.CursoModel;

public interface ICursoRepository extends MongoRepository<CursoModel, Integer>{
}
