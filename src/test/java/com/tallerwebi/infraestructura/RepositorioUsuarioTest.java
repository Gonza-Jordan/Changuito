package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioUsuarioImpl repositorioUsuario;

    @BeforeEach
    public void init() {
        this.repositorioUsuario = new RepositorioUsuarioImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarUsuario() {
        // Preparación
        Usuario usuario = new Usuario();
        usuario.setContrasena("abcd1234");
        usuario.setEmail("jlopez@gmail.com");
        usuario.setApellido("Lopez");
        usuario.setNombre("Jose");
        usuario.setDni(15012456);
        usuario.setDireccion("Florencio Varela 1903, San Justo, Provincia de Buenos Aires");

        // Guardar el usuario primero
        this.repositorioUsuario.guardar(usuario);

        // Modificar algún atributo del usuario
        usuario.setApellido("Garcia");

        // Ejecución
        this.repositorioUsuario.modificar(usuario);

        // Verificación
        Usuario usuarioModificado = this.repositorioUsuario.buscar("jlopez@gmail.com");
        assertThat("Garcia", equalTo(usuarioModificado.getApellido()));
    }

}