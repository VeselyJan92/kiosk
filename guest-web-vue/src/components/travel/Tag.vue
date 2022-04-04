<template>

  <span v-bind:style="{backgroundColor: data.color}">{{data.name}}</span>

</template>

<script setup lang="ts">
import {defineProps} from 'vue';

import type { PropType } from 'vue'


interface Tag  {
  name: string;
  color: string;
}

const props = defineProps({
  data: Object as PropType<Tag>,
})

</script>

<style scoped>

span {
  padding: 5px;
  border-radius: 5px;
  font-weight: 500;
  font-size: 14px;
  line-height: 21px;
}
</style>