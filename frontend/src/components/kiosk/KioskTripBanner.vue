<template>

  <KioskTripPopup v-if="show" @close="show = false" :data="data"></KioskTripPopup>

  <div class="trip-banner"  @click="show = true">

    <div class="image">
      <img :src="data.main_img_url">

      <div class="tags">
        <Tag v-for="item in data.tags" :data="{name: item, color: '#eef3e7'}"></Tag>
      </div>
    </div>


    <div>

      <h3>{{data.title}}</h3>

      <p>{{ data.text.toString().replace(/(<([^>]+)>)/gi, "").slice(0, 150) }}</p>




    </div>
  </div>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'
import {ref} from "vue";
import Tag from "@/components/travel/Tag.vue";
import KioskTripPopup from "@/components/kiosk/KioskTripPopup.vue";

const show = ref(false)


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
  display: flex;
  flex-direction: column;
  gap: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: 1px solid #E7ECF3;
  border-radius: 20px;
  padding: 15px;

  h3{
    margin: 0;
    font-size: 22px;
  }

  p{
   margin-top: 3px;
  }



  img{
    width: 100%;
    aspect-ratio: 5/3;
    object-fit: cover;
    border-radius: 10px;
  }

  .tags {
    display: flex;
    flex-direction: row;
    gap: 10px;

  }

}

.image{
  position: relative;

  .tags{
    position: absolute;
    bottom: 10px;
    left: 10px
  }

}


</style>