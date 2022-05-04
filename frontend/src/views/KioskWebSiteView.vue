<template>

  <MobileHeader></MobileHeader>

  <iframe sandbox="allow-same-origin allow-scripts" :src="hotel.data.official_website">

  </iframe>

</template>


<script setup>
import {useHotelStore} from "@/stores/hotel";
import MobileHeader from "@/components/Mobile/MobileHeader.vue";

const hotel = useHotelStore()
</script>

<style scoped lang="scss">

iframe{
  width: 100%;
  height: 100vh;
}

</style>