function crearMapa(contenedor, latitud, longitud) {
    var map = L.map(contenedor).setView([latitud, longitud], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
}

/* ******************************************* FUNCIONES CHANGUITO ********************************************* */

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

function actualizarPrecio(index, cantidad, precioUnitario) {
    var inputCantidad = document.getElementById('cantidad-' + index);
    var cantidad = parseInt(inputCantidad.value);

    var precioTotal = cantidad * precioUnitario;
    var precioElemento = document.getElementById('precio-unitario-' + index);
    precioElemento.innerText = precioTotal.toFixed(2);

    // Recalcular el total
    recalcularTotal();
}

function recalcularTotal() {
    var preciosUnitarios = document.querySelectorAll('[id^="precio-unitario-"]');
    var total = 0;
    preciosUnitarios.forEach(function(element) {
        total += parseFloat(element.innerText);
    });

    // Actualizar el total final
    var totalFinalElement = document.getElementById('total-final');
    totalFinalElement.innerText = total.toFixed(2);
}

// showSlides();