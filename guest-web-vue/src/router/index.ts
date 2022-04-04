import { createRouter, createWebHistory } from 'vue-router'
import KioskView from '@/views/KioskView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/hotel/kiosk/login',
      name: 'kiosk_login',
      component: LoginView
    },
    {
      path: '/hotel/kiosk/:id',
      name: 'kiosk',
      component: KioskView
    },
  ]
})

export default router
