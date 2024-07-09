package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;



import java.util.ArrayList;
import java.util.List;

public class VistaWeb {
    protected Page page;

    public VistaWeb(Page page) {
        this.page = page;
    }

    protected String obtenerTextoDelElemento(String selectorCSS){
        return this.obtenerElemento(selectorCSS).textContent();
    }

    protected void darClickEnElElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).click();
    }

    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }

    public List<String> obtenerTextosDeElementos(String selectorCSS) {
        return page.locator(selectorCSS).allTextContents();
    }

    protected void darClickEnUnoDeMuchosElementos(String contenedorCSS, String textoBuscado) {
        Locator elementos = page.locator(contenedorCSS);
        int count = elementos.count();

        for (int i = 0; i < count; i++) {
            Locator elemento = elementos.nth(i);
            if (elemento.textContent().contains(textoBuscado)) {
                elemento.click();
                break;
            }
        }
    }

    protected void darClickEnElPrimerElemento(String contenedorCSS) {
        Locator primerElemento = page.locator(contenedorCSS).first();
        primerElemento.click();
    }

    public String obtenerTextoDelPrimerElemento(String selectorCSS){
        Locator primerElemento = page.locator(selectorCSS).first();
        return primerElemento.textContent();
    }


}
