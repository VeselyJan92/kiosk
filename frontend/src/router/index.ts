import { createRouter, createWebHistory } from 'vue-router'
import KioskWebView from '@/views/KioskWebSiteView.vue'
import LoginView from '@/views/LoginView.vue'
import OnlineView from '@/views/MobileView.vue'
import TripPopup from '@/components/Mobile/MobileTripPopup.vue'
import TripEditPopup from '@/components/admin/PopupEditActivity.vue'
import EditTravelInfo from '@//components/admin/PopupEditTravelInfo.vue'
import EditCategory from '@//components/admin/PopupEditCategory.vue'
import TravelInfoPopup from '@//components/Mobile/MobileTravelInfoPopup.vue'
import Settings from '@//components/admin/PopupSettings.vue'
import {useHotelStore} from "@/stores/hotel";
import {useKioskStore} from "@/stores/kiosk";


async function checkData(to){
  const hotel = useHotelStore()
  if (!hotel.data){
    hotel.setHotelId(to.params.id)
    await hotel.reload()
  }
}


async function kioskRedirect(to){
  const kiosk = useKioskStore()

  if (kiosk.kiosk_mode){
    return {name: "hotel", params:{id: kiosk.hotel_id} }
  }
}



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'kiosk_login',
      component: LoginView,
      beforeEnter:[kioskRedirect]
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
          name:"edit-hotel-trip",
          path: 'edit-trip/:tripId',
          component: TripEditPopup,
          props: (route) => ({tripId: route.params.tripId})
        },

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
          props: (route) => ({data: useHotelStore().getTravelInfoById(route.params.infoId), route: true})
        },
        {
          name:"edit-hotel-category",
          path: 'edit-travel-category/:categoryId',
          component: EditCategory,
          props: (route) => ({id: route.params.categoryId})
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
