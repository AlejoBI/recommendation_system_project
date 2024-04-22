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
public class TutorDTO {
    private Integer tutor_id;
    private String tutor_name;

    @JsonProperty("cursos")
    List<Map<String, Integer>> cursos;
}
