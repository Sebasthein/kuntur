document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const profileForm = document.getElementById('profileForm');
    const cancelBtn = document.getElementById('cancelBtn');
    const editAvatarBtn = document.getElementById('editAvatarBtn');
    const avatarUpload = document.getElementById('avatarUpload');
    const profileAvatar = document.getElementById('profileAvatar');
    
    // Datos de ejemplo (simulando datos del usuario)
    const userData = {
        firstName: 'Juan',
        lastName: 'Pérez',
        email: 'juan.perez@example.com',
        phone: '+51 987 654 321',
        bio: 'Desarrollador web apasionado por crear experiencias digitales excepcionales.',
        avatar: 'img/default-avatar.jpg'
    };
    
    // Cargar datos del usuario en el formulario
    function loadUserData() {
        document.getElementById('firstName').value = userData.firstName;
        document.getElementById('lastName').value = userData.lastName;
        document.getElementById('email').value = userData.email;
        document.getElementById('phone').value = userData.phone;
        document.getElementById('bio').value = userData.bio;
        profileAvatar.src = userData.avatar;
    }
    
    // Manejar el cambio de avatar
    editAvatarBtn.addEventListener('click', function() {
        avatarUpload.click();
    });
    
    avatarUpload.addEventListener('change', function(e) {
        if (e.target.files && e.target.files[0]) {
            const reader = new FileReader();
            
            reader.onload = function(event) {
                profileAvatar.src = event.target.result;
                
                // Aquí podrías agregar la lógica para subir la imagen al servidor
                console.log('Nueva imagen seleccionada:', e.target.files[0].name);
            };
            
            reader.readAsDataURL(e.target.files[0]);
        }
    });
    
    // Manejar el envío del formulario
    profileForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Obtener valores del formulario
        const updatedData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            bio: document.getElementById('bio').value,
            avatar: profileAvatar.src
        };
        
        // Aquí iría la lógica para guardar los datos (AJAX, fetch, etc.)
        console.log('Datos actualizados:', updatedData);
        
        // Simular guardado exitoso
        showSuccessMessage('Perfil actualizado correctamente');
    });
    
    // Manejar el botón cancelar
    cancelBtn.addEventListener('click', function() {
        if (confirm('¿Estás seguro de que deseas descartar los cambios?')) {
            loadUserData(); // Recargar datos originales
        }
    });
    
    // Mostrar mensaje de éxito
    function showSuccessMessage(message) {
        const alert = document.createElement('div');
        alert.className = 'success-message';
        alert.innerHTML = `
            <i class="fas fa-check-circle"></i>
            <span>${message}</span>
        `;
        
        document.body.appendChild(alert);
        
        setTimeout(() => {
            alert.classList.add('show');
        }, 10);
        
        setTimeout(() => {
            alert.classList.remove('show');
            setTimeout(() => {
                document.body.removeChild(alert);
            }, 300);
        }, 3000);
    }
    
    // Inicializar la vista
    loadUserData();
});