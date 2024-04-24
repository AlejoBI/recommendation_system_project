package com.example.apiweb.service;

import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.UsuarioModel;
import com.example.apiweb.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
