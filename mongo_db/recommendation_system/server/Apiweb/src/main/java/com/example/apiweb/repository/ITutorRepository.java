package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.TutorModel;

public interface ITutorRepository extends MongoRepository<TutorModel, Integer>{
}
