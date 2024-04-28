package com.example.apiweb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tutor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorModel {
    private Integer tutor_id;
    private String tutor_name;
    private CursoModel cursos;
}
