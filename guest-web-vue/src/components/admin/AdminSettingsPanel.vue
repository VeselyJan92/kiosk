<template>

  <PopupEditActivity :trip-id="null" v-if="insertTripPopup" @close="insertTripPopup = false"></PopupEditActivity>

  <PopupEditCategory v-if="editCategoryPopup" @close="editCategoryPopup = false"></PopupEditCategory>

  <div class="admin-settings-panel">

    <div @click.stop="newTravelInfo">
      <span class="material-icons md-24">add</span>
      <span>Článek</span>
    </div>

    <div @click="insertTripPopup = true">
      <span class="material-icons md-24">add</span>
      <span>Výlet</span>
    </div>

    <div @click="editCategoryPopup = true">
      <span class="material-icons md-24">add</span>
      <span>Skupina výletů</span>
    </div>

    <div>
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

import PopupEditActivity from "@/components/admin/PopupEditActivity.vue";
import {ref} from "vue";
import PopupEditCategory from "@/components/admin/PopupEditCategory.vue";
import {useUserStore} from "@/stores/user";
import {useHotelStore} from "@/stores/hotel";
import { useRouter } from 'vue-router'


const hotel = useHotelStore()
const router = useRouter()

function newTravelInfo(){
  router.push({name: "edit-travel-info", params: {id: hotel.hotel_id, infoId: "new"}})
}

const insertTripPopup = ref(false)
const editCategoryPopup = ref(false)


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