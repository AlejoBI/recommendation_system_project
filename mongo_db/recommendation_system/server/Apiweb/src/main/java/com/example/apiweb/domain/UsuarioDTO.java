package com.example.apiweb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer usuario_id;
    private String nombre_usuario;
    private String carrera;
    private String semestre;

    @JsonProperty("cursos")
    List<Map<String, Integer>> cursos;
}
