package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControladorHomeTest {

	private ControladorHome controladorHome;

	@BeforeEach
	public void init(){
		this.controladorHome = new ControladorHome();
	}

	@Test
	public void queAlSolicitarLaPantallaDelHomeSeMuestreLaVistaHome(){
		// preparacion

		// ejecucion
		ModelAndView mav = this.controladorHome.irAHome();
		String viewName = mav.getViewName();

		// verificacion
		assertThat(viewName, equalToIgnoringCase("home"));
	}

	@Test
	public void queAlIngresarALaPantallaDelHomeSeMuestrenTodasLasCategorias(){
		// preparacion
		List<Categoria> categorias = Arrays.asList(Categoria.values());

		// ejecucion
		ModelAndView mav = this.controladorHome.irAHome();
		List<Categoria> categoriasObtenidas = (List<Categoria>) mav.getModel().get("categorias");


		// verificacion
		assertThat(mav.getViewName(), equalToIgnoringCase("home"));
		assertThat(categoriasObtenidas.size(), equalTo(categorias.size()));
	}

}