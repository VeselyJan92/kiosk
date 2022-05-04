import { defineStore } from 'pinia'


export const useKioskStore  = defineStore("kiosk_store", {
  persist: {
    storage: window.localStorage,
  },
  state: () => ({
    hotel_id: null
  }),
  getters:{
    kiosk_mode(): boolean{
      return this.hotel_id
    }
  },
  actions: {
    setKioskMode(hotel_id: String){
      console.log("kiosk mode")
      this.hotel_id = hotel_id
    },
  }
})

