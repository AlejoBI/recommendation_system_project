package com.example.apiweb.model;

import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    private Integer usuario_id;
    private String nombre_usuario;
    private String carrera;
    private String semestre;
    private String curso;
}
