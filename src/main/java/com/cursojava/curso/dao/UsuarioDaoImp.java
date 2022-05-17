package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    EntityManager enitityManager;

    @Override //Remplazando - Patron diseno
    @Transactional
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        List<Usuario> resultado = enitityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public void deleteUsuario(Long id) {
        //Primera opcion
        Usuario usuario = enitityManager.find(Usuario.class, id);
        enitityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        enitityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCrendenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> resultado = enitityManager
                .createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(resultado.isEmpty()){
            return null;
        }
        String passwordHashed = resultado.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed, usuario.getPassword())){
            return resultado.get(0);
        };

        return null;
      //  System.out.println(resultado.get(0).getEmail());
       // System.out.println(resultado.get(0).getNombre());return isLogged;
    }


}
