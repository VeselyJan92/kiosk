<template>

  <Popup title="Nastavení"  :route="true">

    <form @submit="submit">
      <div class="row mb-3">
        <div class="col">
          <label>Název zařízení</label>
          <input  class="form-control" v-model="data.hotel_name" placeholder="Sleva pro děti" style="height: 42px">
        </div>

        <div class="col">
          <label>Oficiální stránky</label>
          <input  class="form-control" v-model="data.official_website"  placeholder="Sleva pro děti" style="height: 42px">
        </div>
      </div>

      <div class="row mb-3">
        <div class="col">
          <label>Kontaktní email</label>
          <input  class="form-control" v-model="data.contact_email"  placeholder="Sleva pro děti" style="height: 42px">
        </div>

        <div class="col">
          <label>Kontaktní telefon</label>
          <input  class="form-control" v-model="data.contact_phone"  placeholder="Sleva pro děti" style="height: 42px">
        </div>
      </div>

      <div class="mb-3">
        <label>Informace o ubytování</label>
        <div class="editor">
          <quill-editor  theme="snow" style="background-color: white" v-model:content="data.accommodation_text"  content-type="html"></quill-editor>
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
import {getGraphQLClient} from "@/composables/GraphQL";
import {gql} from "graphql-request";
import {useRouter} from "vue-router";

const hotel = useHotelStore()

const router = useRouter()

const {accommodation_text, contact_email, contact_phone, hotel_name, official_website} = hotel.data

const data = reactive({contact_email, contact_phone, hotel_name, official_website, accommodation_text,})

async function submit(e: Event) {
  e.preventDefault()



  const query = gql`
    mutation ($data: HotelUpdateInput!) {
      modifyHotel(data: $data) {
        _id
      }
    }
  `
  await getGraphQLClient().request(query, {data: { _id: hotel.hotel_id, ...data}})

  await hotel.reload()

  router.back()
}


</script>

<style lang="scss">





</style>

<style>
.ql-editor{
  max-height:200px;
}
</style>