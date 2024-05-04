package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.TutorModel;

import java.util.List;

public interface ITutorRepository extends MongoRepository<TutorModel, Integer>{
    @Aggregation({
            "{ '$lookup': { 'from': 'curso', 'localField': 'cursos.curso_id', 'foreignField': '_id', 'as': 'cursosData' } }",
            "{ '$match': { 'cursosData.ratings': { '$elemMatch': { '$gt': ?0 } } } }",
            "{ '$unwind': '$cursosData' }",
            "{ '$group': { '_id': '$_id', 'nombre_tutor': { '$first': '$nombre_tutor' }, 'cursos': { '$push': '$cursosData' } } }"})
    List<TutorModel> listarTutoresCursosMayoresAN(Double ratings);
}
