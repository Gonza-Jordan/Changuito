package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorHomeTest {

	private ControladorHome controladorHome;

	@BeforeEach
	public void init(){
		this.controladorHome = new ControladorHome(this.controladorHome);
	}

	@Test
	public void queAlSolicitarLaPantallaDelHomeSeMuestreLaVistaHome(){
		// preparacion

		// ejecucion
		ModelAndView mav = this.controladorHome.irAHome();
		String viewName = mav.getViewName();

		// verificacion
		assertThat(viewName, equalToIgnoringCase("home")); // Vista correcta
	}

	@Test
	public void queAlIngresarALaPantallaDelHomeSeMuestrenTodasLasCategorias(){
		// preparacion
		List<Categoria> categorias = Arrays.asList(Categoria.values());

		// ejecucion
		ModelAndView mav = this.controladorHome.irAHome();

		// verificacion
		List<Categoria> categoriasObtenidas = (List<Categoria>) mav.getModel().get("categorias");
		assertThat(categoriasObtenidas.size(), equalTo(categorias.size()));
	}

}