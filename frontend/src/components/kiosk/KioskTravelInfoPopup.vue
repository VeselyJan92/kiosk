<template>
    <Popup :title="data.title">

      <div class="content-text description auto-text" v-html="data.text"></div>

    </Popup>
</template>

<script setup lang="ts">
import Popup from "@/components/Popup.vue";

import type {TravelInfoDTO} from "graphql-types"

const props = defineProps<{data: TravelInfoDTO}>()

</script>

<style scoped lang="scss">


h1{
  margin-bottom: 0px;
}





</style>