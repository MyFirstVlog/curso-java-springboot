package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    void deleteUsuario(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCrendenciales(Usuario usuario);
}
