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
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioPromocionTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPromocion repositorioPromocion;
    private Promocion combo = mock(Combo.class);
    private Promocion paquete = mock(Paquete.class);


    @BeforeEach
    public void init() {
        this.repositorioPromocion = new RepositorioPromocionImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaPromocionDeTipoCombo() {
        //Ejecucion
        this.repositorioPromocion.guardarPromocion(combo);

        //Verficacion
        Promocion promocionObtenida = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Promocion", Promocion.class)
                .getSingleResult();

        assertThat(promocionObtenida, equalTo(combo));
        assertThat(promocionObtenida.getClass(), equalTo(Combo.class));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaPromocionDeTipoPaquete() {
        //Ejecucion
        this.repositorioPromocion.guardarPromocion(paquete);

        //Verficacion
        Promocion promocionObtenida = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Promocion", Promocion.class)
                .getSingleResult();

        assertThat(promocionObtenida, equalTo(paquete));
        assertThat(promocionObtenida.getClass(), equalTo(Paquete.class));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerPromociones() {
        //Preparacion
        this.repositorioPromocion.guardarPromocion(combo);
        this.repositorioPromocion.guardarPromocion(paquete);

        //Ejecución
        List<Promocion> promocionesObtenidas = this.repositorioPromocion.obtenerPromociones();

        //Verificación
        assertThat(promocionesObtenidas, is(notNullValue()));
        assertThat(promocionesObtenidas.isEmpty(), is(false));
        assertThat(promocionesObtenidas.get(0), is(instanceOf(Combo.class)));
        assertThat(promocionesObtenidas.get(1), is(instanceOf(Paquete.class)));
    }
}
