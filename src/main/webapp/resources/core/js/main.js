let slideIndex = 0;
const slides = document.querySelectorAll('.carousel-item');
const totalSlides = slides.length;

// Deshabilita o habilita el botón "Anterior" según corresponda
document.querySelector('.prev').disabled = slideIndex <= 0;
if (totalSlides <= 4) {
    // Si la cantidad total de slides es menor o igual a 4, deshabilita el botón "Siguiente"
    document.querySelector('.next').disabled = true;
} else {
    // De lo contrario, habilita o deshabilita el botón "Siguiente" según corresponda
    document.querySelector('.next').disabled = slideIndex >= totalSlides - 4;
}

function moveSlide(n) {
    slideIndex += n * 4;
    showSlides();

    // Deshabilita o habilita el botón "Anterior" según corresponda
    document.querySelector('.prev').disabled = slideIndex <= 0;

    // Deshabilita o habilita el botón "Siguiente" según corresponda
    document.querySelector('.next').disabled = slideIndex >= totalSlides - 4;
}

function showSlides() {
    let startIndex = Math.max(0, slideIndex);
    let endIndex = Math.min(totalSlides, startIndex + 4);
    for (let i = 0; i < totalSlides; i++) {
        if (i >= startIndex && i < endIndex) {
            slides[i].style.display = 'inline-block';
        } else {
            slides[i].style.display = 'none';
        }
    }
}

function incrementarCantidad(index, precio) {
    var inputCantidad = document.getElementById('cantidad-' + index);
    var cantidad = parseInt(inputCantidad.value);
    cantidad++;
    inputCantidad.value = cantidad;
    actualizarPrecio(index, cantidad, precio);
}

function decrementarCantidad(index, precio) {
    var inputCantidad = document.getElementById('cantidad-' + index);
    var cantidad = parseInt(inputCantidad.value);
    if (cantidad > 1) {
        cantidad--;
        inputCantidad.value = cantidad;
        actualizarPrecio(index, cantidad, precio);
    }
}

function actualizarPrecio(index, precioUnitario) {
    var inputCantidad = document.getElementById('cantidad-' + index);
    var cantidad = parseInt(inputCantidad.value);

    var precioTotal = cantidad * precioUnitario;
    var precioElemento = document.getElementById('precio-' + index);
    precioElemento.innerText = precioTotal.toFixed(2);
}

showSlides();

