document.addEventListener('DOMContentLoaded', function() {
    actualizarTotal();

    var cantidadSelects = document.querySelectorAll('.cantidad-select');
    cantidadSelects.forEach(function(select) {
        select.addEventListener('change', function() {
            actualizarPrecioTotal(this.closest('tr'));
        });
    });

    // Copiar el valor del total al abrir el modal
    document.querySelector('[data-bs-target="#modalPago"]').addEventListener('click', function() {
        document.getElementById('totalPagar').innerText = document.getElementById('total-final').innerText;
    });

    document.getElementById('formPago').addEventListener('submit', function(event) {
        event.preventDefault();
        const numeroTarjeta = document.getElementById('numeroTarjeta');
        const nombreTitular = document.getElementById('nombreTitular');
        const fechaVencimiento = document.getElementById('fechaVencimiento');
        const codigoSeguridad = document.getElementById('codigoSeguridad');
        const domicilio = document.getElementById('domicilio');
        const codigoPostal = document.getElementById('codigoPostal');
        const tipoTarjeta = document.querySelector('input[name="tipoTarjeta"]:checked').value;

        // Validaciones del formulario
        if (tipoTarjeta !== 'saldo') {
            if (!numeroTarjeta.value.match(/^\d{16}$/)) {
                alert("El número de tarjeta debe tener 16 dígitos.");
                return false;
            }
            if (!nombreTitular.value.match(/^[a-zA-Z\s]+$/)) {
                alert("El nombre del titular debe contener solo letras y espacios.");
                return false;
            }
            if (!codigoSeguridad.value.match(/^\d{3}$/)) {
                alert("El código de seguridad debe tener 3 dígitos.");
                return false;
            }

            // Validar fecha de vencimiento
            const fechaVencimientoVal = new Date(fechaVencimiento.value);
            const fechaActual = new Date();
            fechaActual.setMonth(fechaActual.getMonth() + 1); // Ajustar a primer día del próximo mes
            fechaActual.setDate(1);

            if (fechaVencimientoVal < fechaActual) {
                alert("La tarjeta está vencida. Por favor, ingrese otra tarjeta.");
                return false;
            }
        }
        if (!domicilio.value.match(/^[a-zA-Z0-9\s]+$/)) {
            alert("El domicilio no debe contener caracteres especiales.");
            return false;
        }
        if (!codigoPostal.value.match(/^\d{4}$/)) {
            alert("El código postal debe tener 4 dígitos.");
            return false;
        }

        // Mostrar mensaje de éxito en el modal
        document.getElementById('mensajeExito').style.display = 'block';
        setTimeout(function() {
            window.location.href = '/spring/home'; // Redirigir a la página principal
            fetch('/spring/vaciarCarrito', { method: 'POST' });
        }, 3000);
    });

    function actualizarPrecioTotal(row) {
        var precioUnitario = parseFloat(row.querySelector('.precio-unitario').innerText);
        var cantidad = parseInt(row.querySelector('.cantidad-select').value);
        var precioTotal = precioUnitario * cantidad;
        row.querySelector('.precio-total').innerText = precioTotal.toFixed(2);
        actualizarTotal();
    }

    function actualizarTotal() {
        var total = 0;
        var precios = document.querySelectorAll('.precio-total');
        precios.forEach(function(precio) {
            total += parseFloat(precio.innerText);
        });
        document.getElementById('total-final').innerText = total.toFixed(2);
    }

    function eliminarFila(button) {
        var row = button.closest('tr');
        row.parentNode.removeChild(row);
        actualizarTotal();
        actualizarCantidadProductos();
    }

    function actualizarCantidadProductos() {
        var cantidadProductos = document.querySelectorAll('.carrito-table tbody tr').length;
        document.getElementById('cantidad-productos').innerText = 'Cantidad de productos en el carrito: ' + cantidadProductos;
    }

        // Mostrar u ocultar campos según la opción de pago seleccionada
        const tipoTarjetaInputs = document.querySelectorAll('input[name="tipoTarjeta"]');
        tipoTarjetaInputs.forEach(function(input) {
            input.addEventListener('change', function() {
                const tarjetaFields = document.querySelectorAll('#numeroTarjeta, #nombreTitular, #fechaVencimiento, #codigoSeguridad');
                if (this.value === 'saldo') {
                    tarjetaFields.forEach(function(field) {
                        field.closest('.mb-3').style.display = 'none';
                    });
                } else {
                    tarjetaFields.forEach(function(field) {
                        field.closest('.mb-3').style.display = 'block';
                    });
                }
            });
        });
    });