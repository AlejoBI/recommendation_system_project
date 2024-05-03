package com.example.apiweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Document(collection = "tutor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorModel {
    @Id
    private Integer tutor_id;
    private String nombre_tutor;

    @JsonProperty("cursos")
    List<Map<String, Object>> cursos;
}
