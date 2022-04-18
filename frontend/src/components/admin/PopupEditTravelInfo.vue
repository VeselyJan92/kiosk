<template>
  <Popup title="Upravit aktivitu" :route="true" id="popup-edit-info">

    <form @submit="submit">
      <div class="mb-3">
        <label>Titulek</label>
        <input class="form-control" placeholder="Jak zařídit skipas" v-model="data.title" id="popup-edit-info-title">
      </div>

      <div class="mb-3">
        <label>Text</label>
        <quill-editor theme="snow" style="background-color: white" v-model:content="data.text" content-type="html" id="popup-edit-info-text"></quill-editor>
      </div>

      <div class="popup-footer">
        <button id="popup-edit-info-delete" type="button" class="btn btn-danger" @click="deleteInfo">Smazat</button>
        <button id="popup-edit-info-submit" type="submit" class="btn btn-primary">Uložit</button>
      </div>

    </form>

  </Popup>
</template>

<script setup lang="ts">
import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import Popup from "@/components/Popup.vue";
import {defineProps, reactive} from "vue";
import {getGraphQLClient} from "@/composables/GraphQL";
import {gql} from "graphql-request";
import {useHotelStore} from "@/stores/hotel";
import router from "@/router";

const hotel = useHotelStore()
const props = defineProps({ id: String })

const _id = props.id === "new" ? null : props.id

const info = hotel.getTravelInfoById(_id)
const data = reactive({title: info?.title ?? "", text: info?.text ?? ""})

async function deleteInfo(){
  const mutation = gql`mutation( $_id: String!) { deleteTravelInfo(id: $_id) {  _id  }  }`

  await getGraphQLClient().request(mutation, {_id})

  await hotel.reload()
  router.back()
}

async function submit(e: Event){
  e.preventDefault()

  const mutation = gql`mutation( $input: UpsertTravelInfoInput! ) { upsertTravelInfo(input: $input) { _id } }`

  await getGraphQLClient().request(mutation, {input: {_id, ...data}})

  await hotel.reload()
  router.back()
}


</script>
