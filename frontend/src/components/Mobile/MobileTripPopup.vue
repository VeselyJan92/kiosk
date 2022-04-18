<template>
    <Popup :title="data.title" @close="emit('close')" :route="true" id="trip-popup">

      <div class="location">
        <span class="material-icons md-24">place</span>

        <span>Pec pod Sněžkou</span>
      </div>


      <div class="tags">
        <Tag v-for="item in data.tags" :data="{name: item, color: '#c2c9d2'}"></Tag>
      </div>

      <img :src="data.main_img_url" class="intro-img">

      <div class="content-text description auto-text" v-html="data.text"></div>

      <div class="images">
        <img v-for="img in data.img_urls" :src="img">
      </div>

    </Popup>
</template>

<script setup>
import Tag from "../travel/Tag.vue";
import Popup from "@/components/Popup.vue";

const props = defineProps({data: Object, route: Boolean })

const emit = defineEmits(["close"])

</script>

<style scoped lang="scss">

.close{
  float: right;
  cursor: pointer;
}

h1{
  margin-bottom: 0px;
}

.tags{
  margin-top: 5px;
  display: flex;
  gap: 10px;
}

.location{
  display: flex;
  align-items: center;
  gap: 10px

}

.intro-img{
  width: 100%;
  border-radius: 20px;
  margin-top: 10px;
  margin-bottom: 20px;
}

.images{


  display: grid;

  grid-template-columns: 1fr 1fr;

  img{
    width: 100%;
    border-radius: 20px;
  }
}



</style>