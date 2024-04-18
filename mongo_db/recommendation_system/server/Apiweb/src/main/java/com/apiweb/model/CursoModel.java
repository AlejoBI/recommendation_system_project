package com.apiweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Document ("curso") // relacionando la clase con una colección de la BD en MongoDB

public class CursoModel {
    @Id
    private int curso_id;
    private String nombre_curso;
    private String modalidad;
}
