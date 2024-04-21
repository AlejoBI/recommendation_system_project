package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.TutorModel;

public class ITutorRepository extends MongoRepository<TutorModel, Integer>{
}
