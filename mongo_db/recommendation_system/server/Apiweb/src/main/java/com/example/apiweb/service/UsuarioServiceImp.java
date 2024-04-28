package com.example.apiweb.service;

import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.UsuarioModel;
import com.example.apiweb.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class UsuarioServiceImp implements IUsuarioService{
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Override
    public String crearUsuario(UsuarioModel usuario) {
        this.usuarioRepository.save(usuario);
        return "El usuario " + usuario.getNombre_usuario() + " fue creado exitosamente";
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId) {
        return this.usuarioRepository.findById(usuarioId);
    }

    @Override
    public String eliminarUsuarioPorId(int usuarioId) {
        Optional<UsuarioModel> usuarioRef = this.usuarioRepository.findById(usuarioId);
        this.usuarioRepository.deleteById(usuarioId);
        return "El usuario " + usuarioRef.get().getNombre_usuario() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarUsuarioPorId(UsuarioModel usuario) {
        this.usuarioRepository.save(usuario);
        return "El usuario con id " + usuario.getUsuario_id() + " fue actualizado con exito.";
    }

    @Override
    public String agregarCursoAUsuario(int usuarioId, CursoModel curso) {
        Optional<UsuarioModel> usuarioOptional = this.usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();

            Map<String, Integer> cursoMap = new HashMap<>();
            cursoMap.put("curso_id", curso.getCurso_id());

            List<Map<String, Integer>> cursos = usuario.getCursos();
            if (cursos == null) {
                cursos = new ArrayList<>();
            }
            cursos.add(cursoMap);

            usuario.setCursos(cursos);
            this.usuarioRepository.save(usuario);

            return "Curso agregado con éxito al usuario con id " + usuarioId;
        } else {
            throw new RecursoNoEncontradoException("Error! No se encontró el usuario con el id " + usuarioId);
        }
    }
}
