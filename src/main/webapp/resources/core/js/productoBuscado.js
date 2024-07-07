        document.addEventListener("DOMContentLoaded", function () {
            const checkboxes = document.querySelectorAll('input[type="checkbox"]');
            const selectOrdenar = document.getElementById('ordenar');

            checkboxes.forEach(function (checkbox) {
                checkbox.addEventListener('change', function () {
                    updateUrlParams();
                });
            });

            selectOrdenar.addEventListener('change', function () {
                updateUrlParams();
            });

            const urlParams = new URLSearchParams(window.location.search);
            checkboxes.forEach(function (checkbox) {
                if (urlParams.has(checkbox.name)) {
                    checkbox.checked = urlParams.get(checkbox.name).split(',').includes(checkbox.value);
                }
            });

            if (urlParams.has(selectOrdenar.name)) {
                selectOrdenar.value = urlParams.get(selectOrdenar.name);
            }
        });

        function updateUrlParams() {
            const url = new URL(window.location.href);
            const params = new URLSearchParams(url.search);

            const checkboxes = document.querySelectorAll('input[type="checkbox"]');
            checkboxes.forEach(function (checkbox) {
                if (checkbox.checked) {
                    if (!params.has(checkbox.name)) {
                        params.append(checkbox.name, checkbox.value);
                    } else {
                        const existingValues = params.get(checkbox.name).split(',');
                        if (!existingValues.includes(checkbox.value)) {
                            existingValues.push(checkbox.value);
                            params.set(checkbox.name, existingValues.join(','));
                        }
                    }
                } else {
                    if (params.has(checkbox.name)) {
                        const existingValues = params.get(checkbox.name).split(',');
                        const index = existingValues.indexOf(checkbox.value);
                        if (index !== -1) {
                            existingValues.splice(index, 1);
                            if (existingValues.length > 0) {
                                params.set(checkbox.name, existingValues.join(','));
                            } else {
                                params.delete(checkbox.name);
                            }
                        }
                    }
                }
            });

            const selectOrdenar = document.getElementById('ordenar');
            if (selectOrdenar.value) {
                params.set(selectOrdenar.name, selectOrdenar.value);
            } else {
                params.delete(selectOrdenar.name);
            }

            window.location.href = `${url.pathname}?${params.toString()}`;
        }