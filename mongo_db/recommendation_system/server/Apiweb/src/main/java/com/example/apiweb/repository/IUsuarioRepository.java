package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.UsuarioModel;

public interface IUsuarioRepository extends MongoRepository<UsuarioModel, Integer>{
}
