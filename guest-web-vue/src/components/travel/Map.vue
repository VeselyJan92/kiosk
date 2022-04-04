<template>
 <div id="map"></div>
</template>

<script setup>
import {onMounted} from "vue";

const props = defineProps({latitude: Number, longitude: Number})

onMounted(()=>{
  var center = SMap.Coords.fromWGS84(props.latitude, props.longitude);
  var m = new SMap(JAK.gel("map"), center, 13);
  m.addDefaultLayer(SMap.DEF_BASE).enable();
  m.addDefaultControls();
})
</script>



<style scoped>


</style>