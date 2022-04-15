<template>

  <Popup title="Nastavení"  @close="emit('close')">


    <form @submit="submit">
      <div class="row mb-3">
        <div class="col">
          <label>Název zařízení</label>
          <input  class="form-control" v-model="data.name" placeholder="Sleva pro děti" style="height: 42px">
        </div>

        <div class="col">
          <label>Oficiální stránky</label>
          <input  class="form-control" v-model="data.website"  placeholder="Sleva pro děti" style="height: 42px">
        </div>
      </div>

      <div class="row mb-3">
        <div class="col">
          <label>Kontaktní email</label>
          <input  class="form-control" v-model="data.email"  placeholder="Sleva pro děti" style="height: 42px">
        </div>

        <div class="col">
          <label>Kontaktní telefon</label>
          <input  class="form-control" v-model="data.phone"  placeholder="Sleva pro děti" style="height: 42px">
        </div>
      </div>

      <div class="mb-3">
        <label>Informace o ubytování</label>
        <div class="editor">
          <quill-editor  theme="snow" style="background-color: white" v-model:content="data.info"  content-type="html"></quill-editor>
        </div>
      </div>


      <div class="popup-footer">
        <button type="submit" @submit="submit" class="btn btn-primary">Uložit</button>
      </div>

    </form>


  </Popup>


</template>

<script setup lang="ts">


import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import Popup from "@/components/Popup.vue";
import {useHotelStore} from "@/stores/hotel";
import {reactive} from "vue";

const hotel = useHotelStore()

const data = reactive({
  email: hotel.data.contact_email,
  phone: hotel.data.contact_phone,
  name: hotel.data.hotel_name,
  website: hotel.data.official_website,
  info: hotel.data.accommodation_text,
})

function submit(e: Event){
  e.preventDefault()

  
}


</script>

<style lang="scss">





</style>

<style>
.ql-editor{
  max-height:200px;
}
</style>