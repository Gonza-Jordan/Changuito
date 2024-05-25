package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Subcategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
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
		Categoria categoria = Categoria.Almacen;

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(categoria);
		String viewName = mav.getViewName();
		Object categoriaResultante = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoriaResultante, equalTo(categoria));
	}

	@Test
	public void queAlHacerClickEnAlmacenDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElIconoAlmacen(){
		// preparacion
		Categoria categoria = Categoria.Almacen;
		String rutaDelIcono = "img/" + categoria + ".svg";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(categoria);
		String icono = (String) mav.getModel().get("icono");

		// verificacion
		assertThat(rutaDelIcono, equalTo(icono));
	}

	@Test
	public void queAlIngresarALaPantallaDeCategoriaAlmacenSeMuestrenTodasLasSubcategoriasDeLaMisma(){
		// preparacion
		Categoria categoria = Categoria.Almacen;
		List<Subcategoria> subcategoriasEsperadas = Arrays.asList(categoria.getSubcategorias());

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(categoria);

		// verificacion
		List<Subcategoria> subcategoriasObtenidas = (List<Subcategoria>) mav.getModel().get("subcategorias");
		assertThat(subcategoriasObtenidas, equalTo(subcategoriasEsperadas));
	}

	@Test
	public void queAlHacerClickEnPerfumeriaDeLaPantallaDelHomeSeMuestreLaVistaCategoriaConElNombrePerfumeria(){
		// preparacion
		String nombreDeLaCategoria = "Perfumeria";

		// ejecucion
		ModelAndView mav = this.controladorCategoria.irACategoria(Categoria.valueOf(nombreDeLaCategoria));
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
		ModelAndView mav = this.controladorCategoria.irACategoria(Categoria.valueOf(nombreDeLaCategoria));
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
		ModelAndView mav = this.controladorCategoria.irACategoria(Categoria.valueOf(nombreDeLaCategoria));
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
		ModelAndView mav = this.controladorCategoria.irACategoria(Categoria.valueOf(nombreDeLaCategoria));
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
		ModelAndView mav = this.controladorCategoria.irACategoria(Categoria.valueOf(nombreDeLaCategoria));
		String viewName = mav.getViewName();
		Object categoria = mav.getModel().get("categoria");

		// verificacion
		assertThat(viewName, equalToIgnoringCase("categoria")); // Vista correcta
		assertThat(categoria, equalTo(nombreDeLaCategoria));
	}
}