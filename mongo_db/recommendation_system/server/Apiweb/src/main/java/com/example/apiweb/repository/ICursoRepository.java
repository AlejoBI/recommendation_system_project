package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.CursoModel;

import java.util.List;

public interface ICursoRepository extends MongoRepository<CursoModel, Integer>{
    @Aggregation("{ '$match' : { 'ratings' : { '$gt' : ?0 } } }")
    List<CursoModel> listarCursosRatingsMayoresAN(Double ratings);
}
