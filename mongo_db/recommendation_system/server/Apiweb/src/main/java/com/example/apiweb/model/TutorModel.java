package com.example.apiweb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tutores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tutor_id;
    private String tutor_name;
    private CursoModel cursos;
}
