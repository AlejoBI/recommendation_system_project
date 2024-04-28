package com.example.apiweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Document(collection = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    @Id
    private Integer usuario_id;
    private String nombre_usuario;
    private String carrera;
    private String semestre;

    @JsonProperty("cursos")
    List<Map<String, Integer>> cursos;
}
