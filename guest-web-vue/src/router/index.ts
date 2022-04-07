import { createRouter, createWebHistory } from 'vue-router'
import KioskView from '@/views/KioskView.vue'
import KioskWebView from '@/views/KioskWebSiteView.vue'
import LoginView from '@/views/LoginView.vue'
import OnlineView from '@/views/MobileView.vue'
import TripPopup from '@/components/Mobile/MobileTripBanner.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'kiosk_login',
      component: LoginView
    },
    {
      path: '/kiosk/:id',
      name: 'kiosk-panel',
      component: KioskView
    },
    {
      path: '/kiosk/:id/web',
      name: 'kiosk-web',
      component: KioskWebView,
    },

    {
      path: '/hotel/:id',
      name: 'hotel',
      component: OnlineView,
      children: [
        {
          path: 'trip/:contactId',
          component: TripPopup,
          props: true
        }
      ]
    },
  ]
})

export default router
