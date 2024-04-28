package com.example.apiweb.service;


import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    String crearUsuario(UsuarioModel usuario);
    List<UsuarioModel> listarUsuarios();
    Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId);
    String eliminarUsuarioPorId(int usuarioId);
    String actualizarUsuarioPorId(UsuarioModel usuario);
    String agregarCursoAUsuario(int usuarioId, CursoModel curso);
}
