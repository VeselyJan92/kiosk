<template>
  <div>

    <div class="category" v-bind:class="{selected: selected}">
      <span>{{ props.category.name }}</span>
      <span class="material-icons edit" v-if="user.edit_mode" @click.stop="edit">edit</span>
    </div>

  </div>
</template>

<script setup>

import {useHotelStore} from "@/stores/hotel";
import {useUserStore} from "@/stores/user";
import {useRouter} from "vue-router";
import router from "@/router";

const props = defineProps({category: Object, selected:Boolean})

const store = useHotelStore()

const user = useUserStore()

const edit = () => router.push({name: 'edit-hotel-category', params: {id: store.hotel_id, categoryId: props.category._id }});

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
    background-color: #E7ECF3;
    cursor: pointer;
  }

  background: #ececec;
  border: 1px solid #A5A5A5;
  box-sizing: border-box;
}


.selected{
  border: 2px solid black;
}


</style>