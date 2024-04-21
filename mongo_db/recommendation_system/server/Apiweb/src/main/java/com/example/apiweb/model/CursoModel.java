package com.example.apiweb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cursos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer curso_id;
    private String nombre_curso;
    private String modalidad;
    private Boolean ratings;
}
