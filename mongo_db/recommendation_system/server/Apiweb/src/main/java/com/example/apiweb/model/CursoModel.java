package com.example.apiweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@Document(collection = "curso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoModel {
    private Integer curso_id;
    private String nombre_curso;
    private String modalidad;
    private List<Double> ratings;
}
