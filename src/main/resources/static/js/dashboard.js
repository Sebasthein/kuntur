document.addEventListener('DOMContentLoaded', function() {
    // Navegación entre secciones
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remover clase active de todos los items
            navItems.forEach(navItem => {
                navItem.classList.remove('active');
            });
            
            // Agregar clase active al item clickeado
            this.classList.add('active');
            
            // Aquí iría la lógica para cargar el contenido de la sección
            const section = this.getAttribute('data-section');
            console.log('Cargando sección:', section);
        });
    });
    
    // Notificaciones
    const notificationBtn = document.querySelector('.notification');
    notificationBtn.addEventListener('click', function() {
        // Aquí iría la lógica para mostrar notificaciones
        console.log('Mostrar notificaciones');
    });
    
    // Acciones rápidas
    const actionCards = document.querySelectorAll('.action-card');
    actionCards.forEach(card => {
        card.addEventListener('click', function() {
            const action = this.getAttribute('data-action');
            console.log('Acción:', action);
            
            // Ejemplo: Redirigir según la acción
            switch(action) {
                case 'search-providers':
                    // window.location.href = '/proveedores';
                    break;
                case 'new-booking':
                    // window.location.href = '/reservas/nueva';
                    break;
                case 'add-service':
                    // window.location.href = '/servicios/nuevo';
                    break;
                case 'profile':
                    // window.location.href = '/perfil';
                    break;
            }
        });
    });
    
    // Favoritos
    const favoriteButtons = document.querySelectorAll('.btn-secondary');
    favoriteButtons.forEach(button => {
        button.addEventListener('click', function() {
            this.classList.toggle('active');
            if (this.classList.contains('active')) {
                this.innerHTML = '<i class="fas fa-heart"></i>';
                showToast('Añadido a favoritos');
            } else {
                this.innerHTML = '<i class="far fa-heart"></i>';
                showToast('Eliminado de favoritos');
            }
        });
    });
    
    // Mostrar toast
    function showToast(message) {
        const toast = document.createElement('div');
        toast.className = 'toast';
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
    
    // Cargar datos iniciales
    function loadInitialData() {
        // Aquí irían las peticiones al servidor para cargar datos iniciales
        console.log('Cargando datos iniciales...');
    }
    
    // Inicializar
    loadInitialData();
});