import { defineStore } from 'pinia'



export const useUserStore  = defineStore("user_store", {
  persist: {
    storage: window.sessionStorage,
  },
  state: () => ({
    authenticated_hotel_id: null,
    token: null,
  }),
  getters:{
    edit_mode(): boolean{
      return this.token !=null
    },

  },
  actions: {
    logout(){
      this.token = null
      this.authenticated_hotel_id = null
    },

    async login(email: String, password: String){

      const url = import.meta.env.VITE_SERVER_URL + "/login"

      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, password})
      });


      if(response.status == 200){
        const payload = await response.json()

        this.authenticated_hotel_id = payload.id
        this.token = payload.token

        console.log(this.token)

        return this.authenticated_hotel_id
      }

      return null

    },

    logout(){
      this.authenticated_user_id = null
      this.token = null
    }

  }

})

