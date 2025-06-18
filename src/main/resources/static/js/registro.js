document.addEventListener('DOMContentLoaded', function() {
    // Configuración de proveedores OAuth
    const OAUTH_CONFIG = {
        facebook: {
            appId: 'TU_APP_ID_FACEBOOK',
            scope: 'public_profile,email',
            redirectUri: window.location.origin + '/auth/facebook/callback'
        },
        google: {
            clientId: 'TU_CLIENT_ID_GOOGLE',
            scope: 'profile email',
            redirectUri: window.location.origin + '/auth/google/callback'
        },
        linkedin: {
            clientId: 'TU_CLIENT_ID_LINKEDIN',
            scope: 'r_liteprofile r_emailaddress',
            redirectUri: window.location.origin + '/auth/linkedin/callback'
        }
    };

    // Elementos del DOM
    const loginForm = document.getElementById('loginForm');
    const alertContainer = document.querySelector('.alert-container');
    const socialButtons = document.querySelectorAll('.social-btn');
    
    // Mostrar alerta
    function showAlert(message, type) {
        // Crear elemento de alerta
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        
        // Icono según el tipo
        const iconClass = type === 'error' ? 'fa-exclamation-circle' : 'fa-check-circle';
        alertDiv.innerHTML = `<i class="fas ${iconClass}"></i> ${message}`;
        
        // Limpiar alertas anteriores
        alertContainer.innerHTML = '';
        
        // Agregar la nueva alerta
        alertContainer.appendChild(alertDiv);
        
        // Eliminar después de 5 segundos
        setTimeout(() => {
            alertDiv.style.opacity = '0';
            setTimeout(() => alertDiv.remove(), 300);
        }, 5000);
    }
    
    // Validar formulario
    function validateForm(formData) {
        const { username, password } = formData;
        
        if (!username.trim()) {
            showAlert('Por favor ingresa tu usuario', 'error');
            return false;
        }
        
        if (!password.trim()) {
            showAlert('Por favor ingresa tu contraseña', 'error');
            return false;
        }
        
        return true;
    }
    
    // Manejar envío del formulario
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = {
                username: this.username.value,
                password: this.password.value,
                remember: this.remember?.checked || false
            };
            
            if (validateForm(formData)) {
                // Aquí iría la lógica real de autenticación
                authenticateWithBackend(formData)
                    .then(response => {
                        if (response.success) {
                            showAlert('Inicio de sesión exitoso! Redirigiendo...', 'success');
                            setTimeout(() => {
                                window.location.href = response.redirectUrl || '/dashboard';
                            }, 1500);
                        } else {
                            showAlert(response.message || 'Error en el inicio de sesión', 'error');
                        }
                    })
                    .catch(error => {
                        showAlert('Error al conectar con el servidor', 'error');
                        console.error(error);
                    });
            }
        });
    }
    
    // Función para autenticar con el backend
    function authenticateWithBackend(credentials) {
        return fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        }).then(response => response.json());
    }
    
    // Iniciar sesión con Facebook
    function loginWithFacebook() {
        showAlert('Iniciando sesión con Facebook...', 'success');
        
        // Cargar SDK de Facebook si no está cargado
        if (typeof FB === 'undefined') {
            loadFacebookSDK().then(() => {
                initFacebookLogin();
            });
        } else {
            initFacebookLogin();
        }
    }
    
    function loadFacebookSDK() {
        return new Promise((resolve) => {
            const script = document.createElement('script');
            script.src = 'https://connect.facebook.net/en_US/sdk.js';
            script.async = true;
            script.defer = true;
            script.onload = resolve;
            document.body.appendChild(script);
        });
    }
    
    function initFacebookLogin() {
        FB.init({
            appId: OAUTH_CONFIG.facebook.appId,
            cookie: true,
            xfbml: true,
            version: 'v12.0'
        });
        
        FB.login(function(response) {
            if (response.authResponse) {
                // Obtener información del usuario
                FB.api('/me', {fields: 'name,email'}, function(userData) {
                    socialLogin({
                        provider: 'facebook',
                        accessToken: response.authResponse.accessToken,
                        email: userData.email,
                        name: userData.name
                    });
                });
            } else {
                showAlert('Inicio de sesión con Facebook cancelado', 'error');
            }
        }, {scope: OAUTH_CONFIG.facebook.scope});
    }
    
    // Iniciar sesión con Google
    function loginWithGoogle() {
        showAlert('Iniciando sesión con Google...', 'success');
        
        // Cargar SDK de Google si no está cargado
        if (typeof google === 'undefined') {
            const script = document.createElement('script');
            script.src = 'https://accounts.google.com/gsi/client';
            script.async = true;
            script.defer = true;
            script.onload = () => initGoogleLogin();
            document.head.appendChild(script);
        } else {
            initGoogleLogin();
        }
    }
    
    function initGoogleLogin() {
        const client = google.accounts.oauth2.initTokenClient({
            client_id: OAUTH_CONFIG.google.clientId,
            scope: OAUTH_CONFIG.google.scope,
            callback: (tokenResponse) => {
                if (tokenResponse && tokenResponse.access_token) {
                    // Obtener información del usuario
                    fetch('https://www.googleapis.com/oauth2/v3/userinfo', {
                        headers: {
                            'Authorization': `Bearer ${tokenResponse.access_token}`
                        }
                    })
                    .then(response => response.json())
                    .then(userData => {
                        socialLogin({
                            provider: 'google',
                            accessToken: tokenResponse.access_token,
                            email: userData.email,
                            name: userData.name
                        });
                    })
                    .catch(error => {
                        showAlert('Error al obtener información de Google', 'error');
                        console.error(error);
                    });
                }
            }
        });
        
        client.requestAccessToken();
    }
    
    // Iniciar sesión con LinkedIn
    function loginWithLinkedIn() {
        showAlert('Iniciando sesión con LinkedIn...', 'success');
        
        const linkedinAuthUrl = `https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=${OAUTH_CONFIG.linkedin.clientId}&redirect_uri=${encodeURIComponent(OAUTH_CONFIG.linkedin.redirectUri)}&scope=${OAUTH_CONFIG.linkedin.scope}`;
        
        // Abrir ventana emergente para autenticación
        const width = 600;
        const height = 600;
        const left = (window.innerWidth - width) / 2;
        const top = (window.innerHeight - height) / 2;
        
        window.open(linkedinAuthUrl, 'LinkedInLogin', `width=${width},height=${height},top=${top},left=${left}`);
    }
    
    // Escuchar mensajes de la ventana de LinkedIn
    window.addEventListener('message', function(event) {
        if (event.origin !== window.location.origin) return;
        
        if (event.data.linkedinAuth) {
            if (event.data.success) {
                socialLogin({
                    provider: 'linkedin',
                    accessToken: event.data.accessToken,
                    email: event.data.email,
                    name: event.data.name
                });
            } else {
                showAlert(event.data.message || 'Error en autenticación con LinkedIn', 'error');
            }
        }
    });
    
    // Función para autenticación social con el backend
    function socialLogin(socialData) {
        fetch('/api/auth/social-login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(socialData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showAlert('Autenticación exitosa! Redirigiendo...', 'success');
                setTimeout(() => {
                    window.location.href = data.redirectUrl || '/dashboard';
                }, 1500);
            } else {
                showAlert(data.message || 'Error en la autenticación social', 'error');
            }
        })
        .catch(error => {
            showAlert('Error al conectar con el servidor', 'error');
            console.error(error);
        });
    }
    
    // Asignar eventos a los botones sociales
    socialButtons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-3px)';
        });
        
        button.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
        
        // Asignar función según el botón
        if (button.classList.contains('fb-btn')) {
            button.addEventListener('click', loginWithFacebook);
        } else if (button.classList.contains('google-btn')) {
            button.addEventListener('click', loginWithGoogle);
        } else if (button.classList.contains('linkedin-btn')) {
            button.addEventListener('click', loginWithLinkedIn);
        }
    });
    
    // Efecto de carga inicial
    setTimeout(() => {
        document.querySelector('.login-container')?.style?.opacity = '1';
    }, 100);
    
    // Verificar parámetros de URL para mensajes
    const urlParams = new URLSearchParams(window.location.search);
    const errorMessage = urlParams.get('error');
    const successMessage = urlParams.get('success');
    
    if (errorMessage) {
        showAlert(decodeURIComponent(errorMessage), 'error');
    }
    
    if (successMessage) {
        showAlert(decodeURIComponent(successMessage), 'success');
    }
});