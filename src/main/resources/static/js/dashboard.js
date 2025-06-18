/**
 * Módulo principal del Dashboard Kuntur
 * 
 * Este script maneja todas las interacciones del panel de control,
 * incluyendo navegación, notificaciones, acciones rápidas y más.
 */

class Dashboard {
  constructor() {
    this.initSelectors();
    this.initEventListeners();
    this.loadInitialData();
    this.setupUIEffects();
  }

  // Selección de elementos del DOM
  initSelectors() {
    this.selectors = {
      navItems: '.nav-item',
      notificationBtn: '.notification',
      actionCards: '.action-card',
      favoriteButtons: '.btn-secondary',
      providerCards: '.provider-card',
      requestCards: '.request-card',
      serviceCards: '.service-card',
      bookingRows: '.bookings-table tbody tr',
      quickActionBtns: '.quick-actions button',
      statusBadges: '.status-badge'
    };
  }

  // Configuración de event listeners
  initEventListeners() {
    // Navegación
    document.querySelectorAll(this.selectors.navItems).forEach(item => {
      item.addEventListener('click', this.handleNavigation.bind(this));
    });

	
    // Notificaciones
    document.querySelector(this.selectors.notificationBtn)?.addEventListener('click', this.showNotifications);

    // Acciones rápidas
    document.querySelectorAll(this.selectors.actionCards).forEach(card => {
      card.addEventListener('click', this.handleQuickAction.bind(this));
    });

    // Favoritos
    document.querySelectorAll(this.selectors.favoriteButtons).forEach(button => {
      button.addEventListener('click', this.toggleFavorite.bind(this));
    });

    // Efectos hover para cards
    this.setupCardHoverEffects();
  }


  // Manejo de navegación
  handleNavigation(e) {
    e.preventDefault();
    const target = e.currentTarget;

    // Remover clase active de todos los items
    document.querySelectorAll(this.selectors.navItems).forEach(item => {
      item.classList.remove('active');
    });

    // Agregar clase active al item clickeado
    target.classList.add('active');

    // Cargar contenido de la sección
    const section = target.getAttribute('data-section');
    this.loadSectionContent(section);
  }

  // Cargar contenido de sección
  loadSectionContent(section) {
    console.log(`Cargando sección: ${section}`);
    // Aquí iría la lógica para cargar contenido dinámico
    // Por ejemplo, mediante fetch o mostrando/ocultando elementos
  }

  // Mostrar notificaciones
  showNotifications() {
    console.log('Mostrando notificaciones');
    // Implementación real para mostrar un dropdown de notificaciones
  }

  // Manejar acciones rápidas
  handleQuickAction(e) {
    const action = e.currentTarget.getAttribute('data-action');
    console.log(`Ejecutando acción: ${action}`);

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
      default:
        this.showToast(`Acción "${action}" ejecutada`);
    }
  }

  // Alternar favoritos
  toggleFavorite(e) {
    const button = e.currentTarget;
    button.classList.toggle('active');

    if (button.classList.contains('active')) {
      button.innerHTML = '<i class="fas fa-heart"></i>';
      this.showToast('Añadido a favoritos');
    } else {
      button.innerHTML = '<i class="far fa-heart"></i>';
      this.showToast('Eliminado de favoritos');
    }
  }

  // Mostrar notificación toast
  showToast(message, type = 'success') {
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

  // Cargar datos iniciales
  async loadInitialData() {
    try {
      console.log('Cargando datos iniciales...');
      // Ejemplo de carga de datos:
      // const response = await fetch('/api/dashboard-data');
      // const data = await response.json();
      // this.updateUI(data);
    } catch (error) {
      console.error('Error cargando datos iniciales:', error);
      this.showToast('Error al cargar datos', 'error');
    }
  }

  // Actualizar UI con datos
  updateUI(data) {
    // Implementación para actualizar la interfaz con los datos recibidos
    console.log('Actualizando UI con datos:', data);
  }
}

// Inicializar el dashboard cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {
  new Dashboard();
});