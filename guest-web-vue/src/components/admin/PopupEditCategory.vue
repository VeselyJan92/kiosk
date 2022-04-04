<template>

  <Popup title="Upravit kategorii"  @close="emit('close')">


    <form @submit="submit">
      <div class="mb-3">
        <label for="title">Titulek</label>
        <input class="form-control" id="title" placeholder="Sleva pro děti" v-model="name">
      </div>
    </form>

    <div class="popup-footer">
      <button v-if="props.category._id" type="button" class="btn btn-danger" @click="remove">Smazat</button>
      <button type="button" @click="update" class="btn btn-primary">{{ props.category._id ? "Uložit" : "Přidat"}}</button>
    </div>


  </Popup>


</template>

<script setup lang="ts">

import '@vueup/vue-quill/dist/vue-quill.snow.css'





import Popup from "@/components/Popup.vue";
import {useKioskStore} from "@/stores/kiosk";
import {onBeforeMount, ref} from "vue";
import {gql} from "graphql-request";
import {graphQLClient} from "@/composables/GraphQL";

const store = useKioskStore()

const props = defineProps({ category: Object })

const emit = defineEmits(['close'])

console.log(props.category)

const name = ref(props.category.name)


async function update(){

  console.log(name.value)

  console.log(store.data.trip_categories)

  let categories = store.data.trip_categories.map(item => ({_id: item._id, name: item.name, trip_ids: item.trip_ids}))

  if (props.category == null){
    categories.push({_id: null, name: name.value, trip_ids: []})
  }else {

    const update = categories.find( item => item._id == props.category._id)

    if (update)
      update.name = name.value
  }

  console.log(categories)

  const mutation = gql`mutation($categories: [TripCategoryInput!]!){modifyTripCategories(categories: $categories){ _id } }`

  await graphQLClient.request(mutation, {categories})

  await store.init()

  emit("close")
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