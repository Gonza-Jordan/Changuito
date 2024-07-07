document.addEventListener("DOMContentLoaded", function() {
    const contrasenaInput = document.getElementById("contrasena");
    const contrasenaValue = contrasenaInput.value;

    if (contrasenaValue) {
        contrasenaInput.type = "password";
        contrasenaInput.value = "********";
    }

    contrasenaInput.addEventListener("focus", function() {
        contrasenaInput.type = "text";
        contrasenaInput.value = contrasenaValue;
    });

    contrasenaInput.addEventListener("blur", function() {
        contrasenaInput.type = "password";
        contrasenaInput.value = "********";
    });
});
