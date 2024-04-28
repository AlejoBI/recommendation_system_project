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
public class CursoDTO {
    private Integer curso_id;
    private String nombre_curso;
    private String modalidad;

    @JsonProperty("ratings")
    List<Map<String, Double>> ratings;
}
