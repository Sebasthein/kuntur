document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('serviceForm');
    const descriptionField = document.getElementById('descripcion');
    const charCount = document.getElementById('charCount');

    // Contador de caracteres
    descriptionField.addEventListener('input', function () {
        charCount.textContent = this.value.length;
    });

    // Envío del formulario
    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const saveBtn = document.querySelector('.btn-save');
        saveBtn.disabled = true;
        saveBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creando...';

        const formData = new FormData(form);
        const serviceData = {
            titulo: formData.get("titulo"),
            descripcion: formData.get("descripcion"),
            precio: parseFloat(formData.get("precio")),
            fechaCreacion: new Date().toISOString(),
            categoria: { id: 1 }, // Aquí puedes cargar dinámicamente las categorías si lo deseas
            proveedor: { id: 1 }  // Reemplaza por el ID real del usuario actual
        };

        try {
            const response = await fetch('/api/servicios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(serviceData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Error al crear servicio: ${errorText}`);
            }

            const result = await response.json();
            showToast('Servicio creado exitosamente', 'success');
            
            setTimeout(() => {
                window.location.href = '/dashboard';
            }, 1500);

        } catch (error) {
            console.error(error);
            showToast('Hubo un problema al crear el servicio', 'error');
            saveBtn.disabled = false;
            saveBtn.textContent = 'Crear Servicio';
        }
    });

    // Función para mostrar mensajes toast
    function showToast(message, type = 'success') {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.textContent = message;
        document.body.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => toast.remove(), 300);
            }, 3000);
        }, 100);
    }
});