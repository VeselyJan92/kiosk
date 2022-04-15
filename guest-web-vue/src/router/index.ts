import { createRouter, createWebHistory } from 'vue-router'
import KioskView from '@/views/KioskView.vue'
import KioskWebView from '@/views/KioskWebSiteView.vue'
import LoginView from '@/views/LoginView.vue'
import OnlineView from '@/views/MobileView.vue'
import TripPopup from '@/components/Mobile/MobileTripPopup.vue'
import EditTravelInfo from '@//components/admin/PopupEditTravelInfo.vue'
import TravelInfoPopup from '@//components/kiosk/KioskTravelInfoPopup.vue'
import Settings from '@//components/admin/PopupSettings.vue'
import {useHotelStore} from "@/stores/hotel";


async function checkData(to){
  const hotel = useHotelStore()

  if (!hotel.data){
    hotel.setHotelId(to.params.id)
    await hotel.reload()
  }

}


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
      component: KioskView,
      beforeEnter: [checkData],
    },
    {
      path: '/kiosk/:id/web',
      name: 'kiosk-web',
      component: KioskWebView,
      beforeEnter: [checkData],
    },

    {
      path: '/hotel/:id',
      name: 'hotel',
      component: OnlineView,
      beforeEnter: [checkData],

      children: [
        {
          name:"hotel-trip",
          path: 'trip/:tripId',
          component: TripPopup,
          props: (route) => ({data: useHotelStore().getTripById(route.params.tripId), route: true})
        },
        {
          name:"edit-travel-info",
          path: 'edit-travel-info/:infoId',
          component: EditTravelInfo,
          props: (route) => ({id: route.params.infoId})
        },
        {
          name:"travel-info",
          path: 'travel-info/:infoId',
          component: TravelInfoPopup,
          props: (route) => ({id: route.params.infoId})
        },
        {
          name:"settings",
          path: 'settings',
          component: Settings
        },
      ]
    },
  ]
})


/*router.beforeEach(async (to, from, next) => {

  const data = useKioskStore()

  if (!data.data){
    data.setHotelId(to.params.id)
    data.reload_kiosk()
  }

  if (await login()){
    next();
  }
});*/

export default router
