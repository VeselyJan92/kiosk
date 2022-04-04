<template>
  <PopupEditActivity v-if="edit" @close="edit = false"  :trip-id="data._id"></PopupEditActivity>

  <TripPopup v-if="show" @close="show.value = false" :data="data"></TripPopup>

  <div class="trip-banner"  @click="show.value = true">

    <img :src="data.main_img_url">

    <div>

      <div class="details">
          <div>
            <Tag class="tag" :data="{name: 'U Veselých', color: '#caeaaa'}"></Tag>
          </div>

        <span class="date">24. 01. 2022</span>

        <span class="material-icons close" @click.stop="edit = true" v-if="user.edit_mode">edit</span>

      </div>

      <h3>{{data.title}}</h3>

      <p>{{ data.text.toString().replace(/(<([^>]+)>)/gi, "").slice(0, 150) }}</p>


      <div class="tags">
        <Tag v-for="item in data.tags" :data="{name: item, color: '#eef3e7'}"></Tag>
      </div>



    </div>
  </div>
</template>

<script setup lang="ts">
import Tag from "./Tag.vue";
import type { PropType } from 'vue'
import TripPopup from "@/components/travel/TripPopup.vue";
import PopupEditActivity from "@/components/admin/PopupEditActivity.vue";
import {ref} from "vue";
import {useKioskStore} from "@/stores/kiosk";
import {useUserStore} from "@/stores/user";

const edit = ref(false)
const show = ref(false)

const user = useUserStore()

interface Trip  {
  author: string;
  date: string;
  title: string;
  text: string;
  main_img: string;
  imgs: [String];
  tags: [String];
}

const props = defineProps({
  data: Object as PropType<Trip>,
})


</script>

<style scoped lang="scss">

.trip-banner {

  cursor: pointer;
  display: flex;
  flex-direction: column;

  min-width: 70vw;

  gap: 10px;

  @include md{
    min-width: unset;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border: 1px solid #E7ECF3;
    border-radius: 20px;
    padding: 15px;
  }
  @include lg{
    flex-direction: row;
  }

  &:hover{
    background-color: #E7ECF3;
  }

  h3{
    margin: 0;
   font-size: 22px;

  }

  p{
   margin-top: 3px;
  }


  img {

    aspect-ratio: 4/3;
    object-fit: cover;
    border-radius: 10px;
    width: 100%;

    @include lg{
      width: 40%;
    }


  }

  .tags {

    flex-direction: row;
    gap: 10px;
    display: none;

    @include lg{
      display: flex;
    }
  }

  .details{
    display: none;
    margin-bottom: 10px;
    gap: 20px;

    > div {
      flex-grow: 1;
    }

    @include lg{
      display: flex;
    }


    .material-icons{
      background-color: #c2c9d2;
      padding: 3px;
      border-radius: 2px;
    }
  }

}


</style>