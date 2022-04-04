<template>

  <PopupEditCategory v-if="edit" @close="edit=false" :category="props.category"></PopupEditCategory>

  <div class="category" v-bind:class="{selected: selected}">
    <span>{{ props.category.name }}</span>
    <span class="material-icons"  v-if="store.state.edit" @click="edit=true">edit</span>
  </div>


</template>

<script setup>

import {useKioskStore} from "@/stores/kiosk";
import PopupEditCategory from "@/components/admin/PopupEditCategory.vue";
import {ref} from "vue";

const props = defineProps({category: Object, selected:Boolean})


const store = useKioskStore()


const edit = ref(false)


</script>

<style scoped lang="scss">
.category {
  padding-left: 10px;
  padding-right: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  white-space:nowrap;

  height: 50px;

  text-wrap: none;


  border-radius: 15px;

  span:nth-child(1){
    flex: 1;
    text-align: center;
  }

  @include lg {
    padding: 6px 15px;
    min-width: 200px;
  }

  &:hover{
    background: #D8D8D8;
    cursor: pointer;

  }

  //background: #D8D8D8;
  border: 1px solid #A5A5A5;
  box-sizing: border-box;
}


.selected{
  background: #D8D8D8;
}


</style>