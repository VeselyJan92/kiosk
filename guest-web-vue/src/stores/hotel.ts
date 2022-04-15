import { defineStore } from 'pinia'
import {getGraphQLClient, graphQLClient} from "@/composables/GraphQL";
import {gql} from "graphql-request";
import type {HotelDTO, TravelInfoDTO} from "graphql-types";



export type RootType = {
  data: HotelDTO | null
  hotel_id: string | null
  state: {selectedCategory: number}
}

export const useHotelStore  = defineStore("kiosk_store", {
  state: (): RootType => ({
    data: null,
    hotel_id: "",
    state: {selectedCategory:0}
  }),
  getters:{
    ready(): Boolean{
      return this.data != null
    },

    getCategoryById(state: RootType) {
      return (id: String) => state.data.trip_categories.find(item => item._id == id)
    },

    getTravelInfoById(state: RootType) {
      return (id: String): TravelInfoDTO => state.data.travel_info.find(item => item._id == id)
    },

    getTripById(state){
      return (id: string) => {
          return state.data.trip_categories
              .map(category => category.trips)
              .flat()
              .find(trip => trip._id == id)
      }
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

    async reload(){
      const request = gql`query($id: String!) {
       searchHotels(id: $id) {
        contact_email
        contact_phone
        accommodation_text
        official_website
        hotel_name
        
       
        travel_info{
          _id title text
        }
        
        trip_categories {
          _id name trip_ids 
          trips {
           _id hotel_id title tags text main_img_url imgs img_urls
          }
        } 
       }
      }
      `
      const response = await (getGraphQLClient().request(request,{id : this.hotel_id})).catch(e => console.log(e))

      this.data = response.searchHotels

      console.log("Loaded")
    }

  }

})

