<template>
<div class="travel-info-banner" @click.stop="display">

  <h4><span v-if="user.edit_mode" class="material-icons edit" @click.stop="edit">edit</span> {{ data.title }} </h4>

  <p>{{data.text.toString().replace(/(<([^>]+)>)/gi, "").slice(0, 150)}}</p>

</div>
</template>

<script setup>
import router from "@/router";
import {useHotelStore} from "@/stores/hotel";
import {useUserStore} from "@/stores/user";

const props = defineProps({data: Object})
const hotel = useHotelStore()

const user = useUserStore()

function display(){
  //router.push({name: 'edit-travel-info', params:{id:hotel.hotel_id, infoId: props.data._id}})
  router.push({name: 'travel-info', params:{id:hotel.hotel_id, infoId: props.data._id}})
}

function edit(){
  router.push({name: 'edit-travel-info', params:{id:hotel.hotel_id, infoId: props.data._id}})
}

</script>

<style scoped lang="scss">

.edit{
  float: right
}

.travel-info-banner {
  background: #ebebeb;
  margin: 5px;
  padding: 15px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;

  &:hover{
    background-color: #E7ECF3;
    cursor: pointer;
  }

  @include md{
    background-color: #ececec;
    border: 1px solid #E7ECF3;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  }

  img{
    border-radius: 15px;
  }

  h3{
    height: 50%;
  }

  p{
    margin: 0;
    overflow: clip;
  }
}



</style>