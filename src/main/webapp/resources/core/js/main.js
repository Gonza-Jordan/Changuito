function crearMapa(contenedor, latitud, longitud) {
    var map = L.map(contenedor).setView([latitud, longitud], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
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

// showSlides();