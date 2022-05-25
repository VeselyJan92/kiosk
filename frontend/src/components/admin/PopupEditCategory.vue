<template>
  <Popup title="Upravit kategorii" id="popup-edit-category" :route="true">

    <form @submit="submit">
      <div class="mb-3">
        <label>Název kategorie</label>
        <input class="form-control" id="popup-edit-category-name" placeholder="Pro rodiny" v-model="name">
      </div>
    </form>

    <div class="popup-footer">
      <button id="popup-delete-category"  v-if="props.id" @click.stop="remove" type="button" class="btn btn-danger">Smazat</button>
      <button id="popup-edit-category-submit" type="submit" @click.stop="submit" class="btn btn-primary">{{ props.id ? "Uložit" : "Přidat"}}</button>
    </div>

  </Popup>
</template>

<script setup lang="ts">

import '@vueup/vue-quill/dist/vue-quill.snow.css'





import Popup from "@/components/Popup.vue";
import {useHotelStore} from "@/stores/hotel";
import {onBeforeMount, ref} from "vue";
import {gql} from "graphql-request";
import {getGraphQLClient, graphQLClient} from "@/composables/GraphQL";
import type {TripCategoryDTO} from "../../../../graphql-model/build/productionLibrary";
import {useRouter} from "vue-router";

const props = defineProps<{id: string | null }>()
const hotel = useHotelStore()

const router = useRouter()

const name = ref(hotel.getCategoryById(props.id)?.name ?? "")

function mapCategories(): [TripCategoryDTO]{
  return hotel.data.trip_categories.map(item => ({_id: item._id, name: item.name, trip_ids: item.trip_ids}))
}

async function modifyTripCategories(categories: Object[]){
  const mutation = gql`mutation($categories: [TripCategoryInput!]!){modifyTripCategories(categories: $categories){ _id } }`
  await getGraphQLClient().request(mutation, {categories})
  await hotel.reload()

  console.log("back")
  router.back()


}

async function submit(e: Event){
  console.log("submit")

  e.preventDefault()

  const categories = mapCategories()


  if (props.id == "new"){
    categories.push({_id: null, name: name.value, trip_ids: []})
  }else {

    const update = categories.find( item => item._id == props.id)

    if (update)
      update.name = name.value
  }

  modifyTripCategories(categories)
}


async function remove(){
  console.log("remove")
  const categories = mapCategories().filter(category => category._id != props.id)
  modifyTripCategories(categories)
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