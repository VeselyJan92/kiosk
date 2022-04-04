<template>

  <PopupEditActivity :trip-id="null" v-if="insertTripPopup" @close="insertTripPopup = false"></PopupEditActivity>

  <div class="admin-settings-panel">

    <div >
      <span class="material-icons md-24">add</span>
      <span>Článek</span>
    </div>

    <div @click="insertTripPopup = true">
      <span class="material-icons md-24">add</span>
      <span>Výlet</span>
    </div>

    <div>
      <span class="material-icons md-24">add</span>
      <span>Skupina výletů</span>
    </div>

    <div>
      <span class="material-icons md-24">settings</span>
      <span>Nastavení</span>
    </div>







  </div>

</template>

<script setup >

import PopupEditActivity from "@/components/admin/PopupEditActivity.vue";
import {ref} from "vue";


const insertTripPopup = ref(false)




</script>

<style scoped lang="scss">

.admin-settings-panel{
  margin-top: 20px;
  display: flex;
  flex-direction: row;
  gap: 20px;

  background-color: #F9F9F9;




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