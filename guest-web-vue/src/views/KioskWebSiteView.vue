<template>

  <KioskHeader></KioskHeader>

  <iframe src="http://192.168.0.2:46187/">

  </iframe>

</template>


<script setup>
import KioskTextInfoBox from "../components/kiosk/KioskTextInfoBox.vue";
import CategoryButton from "../components/travel/CategoryButton.vue";
import Title from "../components/Title.vue";
import {useHotelStore} from "@/stores/hotel";
import {onBeforeMount} from "vue";
import {useRoute} from "vue-router";
import KioskHeader from "@/components/kiosk/KioskHeader.vue";
import KioskTripBanner from "@/components/kiosk/KioskTripBanner.vue";
import KioskBlogPostBanner from "@/components/kiosk/KioskBlogPostBanner.vue";

const kiosk = useHotelStore()
const route = useRoute()

onBeforeMount(async () => {
  kiosk.setHotelId(route.params.id)
  await kiosk.reload_kiosk()
});

</script>

<style scoped lang="scss">

iframe{
  width: 100%;
  height: 100vh;
}

</style>