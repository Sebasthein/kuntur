// Efectos de interacción
document.addEventListener('DOMContentLoaded', function() {
    // Efecto hover en los inputs
    const inputs = document.querySelectorAll('.input-group input');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.querySelector('.input-icon').style.color = 'var(--gold-light)';
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.querySelector('.input-icon').style.color = 'var(--gold-primary)';
        });
    });
    
    // Efecto de carga del formulario
    const loginContainer = document.querySelector('.login-container');
    setTimeout(() => {
        loginContainer.style.opacity = '1';
        loginContainer.style.transform = 'translateY(0)';
    }, 100);
    
    // Mostrar/ocultar contraseña (podrías añadir un icono de ojo)
    // Aquí iría la lógica si decides añadir esa funcionalidad
});

// Funciones para login con redes sociales (simuladas)
function loginWithFacebook() {
    alert('Redirigiendo a Facebook Login...');
    // Aquí iría la implementación real con Firebase o tu backend
}

function loginWithGoogle() {
    alert('Redirigiendo a Google Login...');
    // Aquí iría la implementación real con Firebase o tu backend
}

function loginWithLinkedIn() {
    alert('Redirigiendo a LinkedIn Login...');
    // Aquí iría la implementación real con LinkedIn API
}

// Validación del formulario antes de enviar
const loginForm = document.querySelector('.login-form');
if (loginForm) {
    loginForm.addEventListener('submit', function(e) {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        
        if (!username || !password) {
            e.preventDefault();
            alert('Por favor completa todos los campos');
        }
        
        // Aquí puedes añadir más validaciones si necesitas
    });
}

// Efecto de hover dorado en los botones sociales
const socialBtns = document.querySelectorAll('.social-btn');
socialBtns.forEach(btn => {
    btn.addEventListener('mouseenter', function() {
        this.style.boxShadow = `0 0 10px ${getComputedStyle(this).backgroundColor}`;
    });
    
    btn.addEventListener('mouseleave', function() {
        this.style.boxShadow = 'none';
    });
});