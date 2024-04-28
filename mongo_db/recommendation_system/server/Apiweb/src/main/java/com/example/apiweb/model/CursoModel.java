package com.example.apiweb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "curso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoModel {
    @Id
    private Integer curso_id;
    private String nombre_curso;
    private String modalidad;
    private List<Double> ratings;
}
