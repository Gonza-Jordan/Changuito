package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Subcategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorCategoriaTest {

	private ControladorCategoria controladorCategoria;

	@BeforeEach
	public void init(){
		this.controladorCategoria = new ControladorCategoria();
	}

	@Test
	public void queAlHacerClickEnAlmacenDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombreAlmacen(){
		// preparacion
		String nombreDeLaCategoria = "Almacen";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

	@Test
	public void queAlHacerClickEnAlmacenDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElIconoAlmacen(){
		// preparacion
		String nombreDeLaCategoria = "Almacen";
		String rutaDelIcono = "img/" + nombreDeLaCategoria + ".svg";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String icono = (String) mav.getModel().get("icono");

		// verificacion
		assertThat(rutaDelIcono, equalTo(icono));
	}

	@Test
	public void queAlIngresarALaPantallaDeCategoriaAlmacenSeMuestrenTodasLasSubcategoriasDeLaMisma(){
		// preparacion
		Categoria categoria = Categoria.Almacen;
		List<Subcategoria> subcategoriasEsperadas = Arrays.asList(categoria.getSubcategorias());
		String nombreDeLaCategoria = "Almacen";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		// verificacion
		List<Subcategoria> subcategoriasObtenidas = (List<Subcategoria>) mav.getModel().get("subcategorias");
		assertThat(subcategoriasObtenidas, equalTo(subcategoriasEsperadas));
	}

	@Test
	public void queAlHacerClickEnPerfumeriaDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombrePerfumeria(){
		// preparacion
		String nombreDeLaCategoria = "Perfumeria";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

	@Test
	public void queAlHacerClickEnBebidasDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombreBebidas(){
		// preparacion
		String nombreDeLaCategoria = "Bebidas";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

	@Test
	public void queAlHacerClickEnVerduleriaDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombreVerduleria(){
		// preparacion
		String nombreDeLaCategoria = "Verduleria";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

	@Test
	public void queAlHacerClickEnLimpiezaDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombreLimpieza(){
		// preparacion
		String nombreDeLaCategoria = "Limpieza";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

	@Test
	public void queAlHacerClickEnLacteosDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombreLacteos(){
		// preparacion
		String nombreDeLaCategoria = "Lacteos";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(nombreDeLaCategoria);
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}

}