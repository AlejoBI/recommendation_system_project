package com.example.apiweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.apiweb.model.UsuarioModel;

public class IUsuarioRepository extends MongoRepository<UsuarioModel, Integer>{
}
