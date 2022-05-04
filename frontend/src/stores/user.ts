import { defineStore } from 'pinia'



export const useUserStore  = defineStore("user_store", {
  persist: {
    storage: window.sessionStorage,
  },
  state: () => ({
    authenticated_hotel_id: null,
    token: null,
    kiosk: false
  }),
  getters:{
    edit_mode(): boolean{
      return this.token !=null
    },
    kiosk_mode(): boolean{
      return this.kiosk
    }

  },
  actions: {
    logout(){
      this.token = null
      this.authenticated_hotel_id = null
    },

    async login(email: String, password: String, kioskMode: boolean){

      const url = import.meta.env.VITE_SERVER_URL + "/login"

      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, password})
      });

      console.log("Kiosk MODE AUTH:" + kioskMode)
      console.log("response:" + response.status)


      if(response.status == 200){
        const payload = await response.json()

        console.log(payload)

        if (!kioskMode){
          this.authenticated_hotel_id = payload.id
          this.token = payload.token
        }

        return payload.id
      }

      return null

    },

    logout(){
      this.authenticated_user_id = null
      this.token = null
    }

  }

})

