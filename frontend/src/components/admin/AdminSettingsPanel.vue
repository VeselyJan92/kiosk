<template>

  <div class="admin-settings-panel">

    <div @click.stop="newInfo">
      <span class="material-icons md-24" id="add-new-trip-info">add</span>
      <span>Článek</span>
    </div>

    <div @click="newTrip" id="add-new-trip">
      <span class="material-icons md-24">add</span>
      <span>Výlet</span>
    </div>

    <div @click="newCategory" id="add-new-trip-category">
      <span class="material-icons md-24">add</span>
      <span>Skupina výletů</span>
    </div>

    <div @click.stop="settings">
      <span class="material-icons md-24">settings</span>
      <span>Nastavení</span>
    </div>

    <div style="flex-grow: 1"></div>


    <div>
      <span class="material-icons md-24">logout</span>
      <span @click.stop="logout">Logout</span>
    </div>

  </div>

</template>

<script setup >

import {useUserStore} from "@/stores/user";
import {useHotelStore} from "@/stores/hotel";
import { useRouter } from 'vue-router'





const hotel = useHotelStore()
const router = useRouter()

const newInfo = () => router.push({name: "edit-travel-info", params: {id: hotel.hotel_id, infoId: "new" }})
const newTrip = () => router.push({name: 'edit-hotel-trip', params: {id: hotel.hotel_id, tripId: "new" }})
const newCategory = () => router.push({name: 'edit-hotel-category', params: {id: hotel.hotel_id, categoryId: "new"}})
const settings = () => router.push({name: 'settings'})


const user = useUserStore()

function logout(){
  user.logout()
}




</script>

<style scoped lang="scss">

.admin-settings-panel{
  width: 100%;
  margin-top: 20px;
  display: flex;
  flex-direction: row;
  border-radius: 10px;
  gap: 20px;

  background-color: #ebebeb;




  >div{
    padding: 10px;
    display: flex;
    align-items: center;
    gap: 5px;
    justify-content: center;

    &:hover{
      background-color: #dddddd;
      cursor: pointer;
    }
  }
}







</style>