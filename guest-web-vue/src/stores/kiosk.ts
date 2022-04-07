import { defineStore } from 'pinia'
import {getGraphQLClient, graphQLClient} from "@/composables/GraphQL";
import {gql} from "graphql-request";



export const useKioskStore  = defineStore("kiosk_store", {
  state: () => ({
    data: null,
    hotel_id: "",

    state: {selectedCategory:0}
  }),
  getters:{
    ready(): Boolean{
      return this.data != null
    },

    getCategoryById(state) {
      return (id: String) => state.data.categories.find(item => item.id == id)
    },

    getTripById(state){
      return (id: String) => this.state.data.categories.flat().find(trip => trip._id == id)
    }
  },
  actions: {
    setHotelId(id: string){
      this.hotel_id = id
    },


    selectCategory(index: number){
      this.state.selectedCategory = index
    },

    getSelectedCategoryTrips(){

      return this.data.trip_categories[this.state.selectedCategory]?.trips ?? []
    },

    async reload_kiosk(){
      const request = gql`query($id: String!) {
       searchHotels(id: $id) {
        blog_posts{
         _id title text
        }
   
        trip_categories {
          _id name trip_ids 
          trips { _id title tags text main_img_url imgs img_urls }
        } 
       }
      }
      `
      const response = await (getGraphQLClient().request(request,{id : this.hotel_id})).catch(e => console.log(e))

      this.data = response.searchHotels
    }

  }

})

