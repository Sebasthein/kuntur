document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const profileForm = document.getElementById('profileForm');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmationModal = document.getElementById('confirmationModal');
    const confirmSave = document.getElementById('confirmSave');
    const confirmCancel = document.getElementById('confirmCancel');
    const toastNotification = document.getElementById('toastNotification');
    const fileInput = document.getElementById('foto');
    const removePhotoBtn = document.getElementById('removePhotoBtn');
    const photoPreview = document.querySelector('.current-photo');
    
    // Estado inicial
    let originalPhotoSrc = photoPreview.src;
    let formEdited = false;
    
    // Event Listeners
    cancelBtn.addEventListener('click', () => {
        if (isFormEdited()) {
            confirmationModal.style.display = 'flex';
        } else {
            window.location.href = '/dashboard';
        }
    });
    
    confirmCancel.addEventListener('click', () => {
        confirmationModal.style.display = 'none';
    });
    
    confirmSave.addEventListener('click', () => {
        confirmationModal.style.display = 'none';
        window.location.href = '/dashboard';
    });
    
    profileForm.addEventListener('submit', function(e) {
        e.preventDefault();
        submitForm();
    });
    
    fileInput.addEventListener('change', function(e) {
        handleImageUpload(e);
    });
    
    removePhotoBtn.addEventListener('click', function() {
        removeSelectedImage();
    });
    
    // Escuchar cambios en los inputs
    const inputs = profileForm.querySelectorAll('input, textarea');
    inputs.forEach(input => {
        input.addEventListener('input', () => {
            formEdited = true;
        });
    });
    
    // Funciones
    function isFormEdited() {
        // Verificar si algún campo ha sido modificado
        const inputs = profileForm.querySelectorAll('input, textarea');
        for (let input of inputs) {
            if (input.defaultValue !== input.value && !input.readOnly) {
                return true;
            }
        }
        
        // Verificar si se ha seleccionado una nueva imagen
        if (fileInput.files.length > 0) {
            return true;
        }
        
        return false;
    }
    
    async function submitForm() {
        try {
            // Mostrar estado de carga
            const saveBtn = profileForm.querySelector('.btn-save');
            saveBtn.disabled = true;
            saveBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';
            
            // Crear FormData directamente del formulario
            const formData = new FormData(profileForm);
            
            // Enviar datos al servidor
            const response = await fetch('/profile/edit', {
                method: 'POST',
                body: formData,
                headers: {
                    'Accept': 'text/html' // Porque tu endpoint devuelve una redirección
                }
            });
            
            if (!response.ok) {
                throw new Error('Error al actualizar perfil');
            }
            
            // Como es una redirección, seguimos la ubicación
            const redirectUrl = response.url;
            showToast('Perfil actualizado correctamente', 'success');
            
            // Redirigir después de mostrar el toast
            setTimeout(() => {
                window.location.href = redirectUrl || '/dashboard';
            }, 1500);
            
        } catch (error) {
            console.error('Error:', error);
            showToast('Error al actualizar el perfil: ' + error.message, 'error');
            
            // Restaurar botón
            const saveBtn = profileForm.querySelector('.btn-save');
            saveBtn.disabled = false;
            saveBtn.textContent = 'Guardar cambios';
        }
    }
    
    function handleImageUpload(event) {
        const file = event.target.files[0];
        if (!file) return;
        
        // Validar tipo de archivo
        if (!file.type.match('image.*')) {
            showToast('Por favor, selecciona una imagen válida (JPEG, PNG)', 'error');
            event.target.value = '';
            return;
        }
        
        // Validar tamaño de archivo (2MB máximo)
        if (file.size > 2 * 1024 * 1024) {
            showToast('La imagen no debe superar los 2MB', 'error');
            event.target.value = '';
            return;
        }
        
        // Mostrar previsualización
        const reader = new FileReader();
        reader.onload = function(e) {
            photoPreview.src = e.target.result;
            formEdited = true;
        };
        reader.readAsDataURL(file);
    }
    
    function removeSelectedImage() {
        fileInput.value = '';
        photoPreview.src = originalPhotoSrc;
        formEdited = true;
        showToast('Imagen eliminada', 'success');
    }
    
    function showToast(message, type = 'success') {
        toastNotification.textContent = message;
        toastNotification.className = 'toast ' + type;
        toastNotification.classList.add('show');
        
        setTimeout(() => {
            toastNotification.classList.remove('show');
        }, 3000);
    }
    
    // Cerrar modal al hacer clic fuera del contenido
    confirmationModal.addEventListener('click', (e) => {
        if (e.target === confirmationModal) {
            confirmationModal.style.display = 'none';
        }
    });
});