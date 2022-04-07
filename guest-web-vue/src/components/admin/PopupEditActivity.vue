<template>

  <Popup title="Upravit aktivitu" v-if="data" @close="emit('close')">


    <form @submit="submit">
      <div class="mb-3">
        <label for="title">Titulek</label>
        <input class="form-control" id="title" placeholder="Sleva pro děti" v-model="data.title">
      </div>

      <div class="mb-3">
        <label for="title">Obsah</label>
        <quill-editor theme="snow" style="background-color: white" v-model:content="data.text"
                      content-type="html"></quill-editor>
      </div>


      <div class="mb-3 ">

        <div class="row">
          <div class="col">
            <label for="x">Tagy</label>

            <input  class="form-control" v-model="data.tags" placeholder="Sleva pro děti" style="height: 42px">
          </div>

          <div class="col">
            <label for="x">Kategorie</label>

            <multiselect style="height: 40px"
                :multiple="true"
                :close-on-select="true"
                 track-by="_id"
                label="name"
                v-model="selected"
                :options="selectableCategories">
            </multiselect>

          </div>
        </div>
      </div>

      <div class="mb-3">
        <label for="inputGroupFile02">Upload</label>
        <div class="input-group ">
          <input type="file" class="form-control" id="inputGroupFile02" @change="upload">
        </div>
      </div>


      <div class="images">
        <div v-for="(img, index) in data.img_urls">
          <img :src="img">
          <span class="material-icons close" @click="data.img_urls.splice(index, 1)">close</span>
        </div>
      </div>


      <div class="popup-footer">
        <button type="button" class="btn btn-danger" @click="deleteTrip">Smazat</button>
        <button type="submit" class="btn btn-primary">Uložit</button>
      </div>

    </form>


  </Popup>


</template>

<script setup lang="ts">

import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import Multiselect from 'vue-multiselect'


type Data = {
  id: String
  text: String
  title: String
  imgs: [string]
  tags: string
}


import Popup from "@/components/Popup.vue";
import {onBeforeMount, ref} from "vue";
import {encode} from "base64-arraybuffer";
import {getGraphQLClient, graphQLClient} from "@/composables/GraphQL";
import {gql} from "graphql-request";
import {useKioskStore} from "@/stores/kiosk";

const store = useKioskStore()
const props = defineProps({ tripId: String })
const emit = defineEmits(['close'])


const selectableCategories =  store.data.trip_categories.map(category => ({name: category.name, _id: category._id}))


const selected =  ref([])

const data = ref(null)


onBeforeMount(async ()=>{

  if (props.tripId){
    const mutation = gql`
    query($ids: [String!]){
      searchTrips(ids: $ids) {
        _id title text img_urls tags

        categories {
          _id name
        }
      }
    }`

    const trip = (await getGraphQLClient().request(mutation, {ids: [props.tripId] })).searchTrips[0]

    trip.tags = trip.tags.join(", ")
    data.value = trip
    selected.value = trip.categories
  }else {
    data.value = {hotel_id: null, _id: null, title:"", text:"", img_urls:[], tags:"" }
  }


})

function upload(e: Event) {
  data.value.img_urls.push(URL.createObjectURL(e.target.files[0]))
}

async function deleteTrip(){
  const mutation = gql`
      mutation( $_id: String!) {
        deleteTrip(id: $_id) {
          _id
        }
      }`

  await getGraphQLClient().request(mutation, {_id: data.value._id})

  await store.reload_kiosk()

  emit("close")
}


async function submit(e){
  e.preventDefault()

  const imgs = []

  for (const item of data.value.img_urls){
    if (item.startsWith("blob")) {
      imgs.push(await fetch(item).then((b) => b.blob()).then((r) => r.arrayBuffer()).then(r => "blob:" + encode(r)))
    } else if (item.startsWith("http")) {
      imgs.push("id:"+item.split("/").pop())
    }
  }

  const trip = data.value

  const tags = trip.tags.split(",").map((tag) => tag.trim())
  const categories = selected.value.map(item => item._id)

  const mutation = gql`mutation( $input: UpsertTripInput! ) { upsertTrip(input: $input) { _id } }`

  await getGraphQLClient().request(mutation, {input: {id: props.tripId, imgs, tags, categories, title: trip.title, text: trip.text}})

  await store.reload_kiosk()

  emit("close")

  return true
}


</script>

<style lang="scss">

@import "vue-multiselect/dist/vue-multiselect.css";

.images {

  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;

  > div {
    position: relative;

    img {
      width: 100%;
      aspect-ratio: 4 / 3;
      object-fit: cover;
    }

    .material-icons {
      position: absolute;
      top: 0;
      right: 0;
      color: black;
      background-color: #E7ECF3;
    }
  }

}


</style>